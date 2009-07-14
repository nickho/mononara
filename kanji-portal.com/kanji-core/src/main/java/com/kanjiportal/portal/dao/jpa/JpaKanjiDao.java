package com.kanjiportal.portal.dao.jpa;

import com.kanjiportal.portal.dao.KanjiDao;
import com.kanjiportal.portal.model.*;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 24, 2009
 * Time: 10:52:33 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless
@AutoCreate
@Name("kanjiDao")
public class JpaKanjiDao implements KanjiDao {

    @PersistenceContext
    private EntityManager em;

    @Logger
    private Log log;

    public List<Kanji> findByPattern(String pattern, int page, int pageSize) {
        return em.createQuery("select k from Kanji k join fetch k.meanings km join fetch km.meaning m where k.kanji like :pattern or lower(m.meaning) like :pattern or lower(k.description) like :pattern")
                .setParameter("pattern", pattern)
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
    }

    public List<Kanji> findByPatternForcingFullFetch(String pattern, int page, int pageSize) {
        return em.createQuery(new StringBuilder().append(
                "select k from Kanji k left join fetch k.tags t left join fetch t.tag")
                .append(" left join fetch k.meanings")
                .append(" where k.kanji like :pattern")
                .append(" or lower(k.meaning) like :pattern")
                .append(" or lower(k.description) like :pattern").toString())
                .setParameter("pattern", pattern)
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
    }

    public List<Kanji> findBySinceDateForcingFullFetch(Date since, int page, int pageSize) {
        return em.createQuery(new StringBuilder().append(
                "select k from Kanji k left join fetch k.tags t left join fetch t.tag")
                .append(" left join fetch k.meanings")
                .append(" where k.updateDate >= :since")
                .append(" or t.updateDate >= :since").toString())
                .setParameter("since", since, TemporalType.TIMESTAMP)
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
    }

    public List<Kanji> findByTagForcingFullFetch(String tag, int page, int pageSize) {
        return em.createQuery(new StringBuilder().append(
                "select k from Kanji k left join fetch k.tags t left join fetch t.tag")
                .append(" left join fetch k.meanings")
                .append(" where t.tag.code = :tag").toString())
                .setParameter("tag", tag)
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
    }

    public List<Kanji> findByRefForcingFullFetch(String reference, String value, int page, int pageSize) {
        return em.createQuery(new StringBuilder().append(
                "select k from Kanji k join k.references r left join fetch k.tags t left join fetch t.tag")
                .append(" left join fetch k.meanings")
                .append(" where r.reference.value = :value")
                .append(" and r.reference.referenceType.code = :reference").toString())
                .setParameter("reference", reference)
                .setParameter("value", value)
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
    }

    public long countByRef(String reference, String value) {
        return (Long) em.createQuery(new StringBuilder().append(
                "select count(k) from Kanji k join k.references r")
                .append(" where r.reference.value = :value")
                .append(" and r.reference.referenceType.code = :reference").toString())
                .setParameter("reference", reference)
                .setParameter("value", value)
                .getSingleResult();
    }

    public Kanji findByKanji(String kanji) {
        return (Kanji) em.createQuery("select k from Kanji k where k.kanji = :kanji")
                .setParameter("kanji", kanji)
                .getSingleResult();
    }

    public void addTagToKanji(Kanji kanji, Tag tag) {
        KanjiTag kanjiTag = new KanjiTag(kanji, tag);
        em.persist(kanjiTag);
        kanji.addTag(kanjiTag);
        em.merge(kanji);
    }

    public void removeTagFromKanji(Kanji kanji, Tag tag) {
        List<KanjiTag> listKanjiTag = em.createQuery("from KanjiTag kt where kt.kanji.id = :kanjiId and kt.tag.id = :tagId")
                .setParameter("kanjiId", kanji.getId())
                .setParameter("tagId", tag.getId())
                .getResultList();
        if (listKanjiTag.size() > 1) {
            log.warn("No unicity while trying to remove tag(#0) from Kanji(#1). None removed", kanji.getId(), tag.getId());
        } else if (listKanjiTag.size() == 0) {
            log.warn("No tag(#0) found for Kanji(#1). None removed", kanji.getId(), tag.getId());
        } else {
            em.remove(listKanjiTag.get(0));
            kanji.removeTag(listKanjiTag.get(0));
            em.merge(kanji);
        }
    }

    public void addReference(Kanji kanji, Reference reference, long referenceTypeId) {
        KanjiReference kanjiReference = new KanjiReference();
        kanjiReference.setKanji(kanji);
        kanjiReference.setReference(reference);
        //kanjiReference.setReferenceType(em.find(ReferenceType.class, referenceTypeId));
        em.persist(kanjiReference);
        kanji.addReference(kanjiReference);
        em.merge(kanji);
    }

    public void removeReferenceFromKanji(Kanji kanji, Reference reference) {
        List<KanjiReference> listKanjiReferences = em.createQuery("from KanjiReference kr where kr.kanji.id = :kanjiId and kr.reference.id = :refId")
                .setParameter("kanjiId", kanji.getId())
                .setParameter("refId", reference.getId())
                .getResultList();
        if (listKanjiReferences.size() > 1) {
            log.warn("No unicity while trying to remove reference(#0) from Kanji(#1). None removed", kanji.getId(), reference.getId());
        } else if (listKanjiReferences.size() == 0) {
            log.warn("No reference(#0) found for Kanji(#1). None removed", kanji.getId(), reference.getId());
        } else {
            em.remove(listKanjiReferences.get(0));
            kanji.removeReference(listKanjiReferences.get(0));
            em.merge(kanji);
        }
    }

    public void addSpellingToKanji(Kanji kanji, Spelling spelling) {
        KanjiSpelling kanjiSpelling = new KanjiSpelling();
        kanjiSpelling.setKanji(kanji);
        kanjiSpelling.setSpelling(spelling);
        em.persist(kanjiSpelling);
        kanji.addSpelling(kanjiSpelling);
        em.merge(kanji);
    }

    public void removeSpellingFromKanji(Kanji kanji, Spelling spelling) {
        List<KanjiSpelling> listKanjiSpellings = em.createQuery("from KanjiSpelling ks where ks.kanji.id = :kanjiId and ks.spelling.id = :spellingId")
                .setParameter("kanjiId", kanji.getId())
                .setParameter("spellingId", spelling.getId())
                .getResultList();
        if (listKanjiSpellings.size() > 1) {
            log.warn("No unicity while trying to remove spelling(#0) from Kanji(#1). None removed", kanji.getId(), spelling.getId());
        } else if (listKanjiSpellings.size() == 0) {
            log.warn("No spelling(#0) found for Kanji(#1). None removed", kanji.getId(), spelling.getId());
        } else {
            em.remove(listKanjiSpellings.get(0));
            kanji.removeSpelling(listKanjiSpellings.get(0));
            em.merge(kanji);
        }
    }
}
