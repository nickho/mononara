/**
 * This file is part of kanji-portal.com.
 *
 * kanji-portal.com is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * kanji-portal.com is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with kanji-portal.com.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (c) 2008-2009 Nicolas Radde <nicolas@radde.org>
 */
package com.kanjiportal.portal.dao.jpa;

import com.kanjiportal.portal.dao.KanjiDao;
import com.kanjiportal.portal.dao.SearchTooGenericException;
import com.kanjiportal.portal.model.*;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 24, 2009
 * Time: 10:52:33 PM
 * To change this template use File | Settings | File Templates.
 */
@AutoCreate
@Name("kanjiDao")
public class JpaKanjiDao implements KanjiDao {

    @In
    private EntityManager entityManager;

    @Logger
    private Log log;

    public List<Kanji> findByPattern(String pattern, int page, int pageSize) throws SearchTooGenericException {
        FullTextEntityManager ftem = (FullTextEntityManager) entityManager;
        Map<String, Float> boostPerField = new HashMap<String, Float>();
        boostPerField.put("meanings.meaning.meaning", 2f);
        boostPerField.put("description", 1f);
        String[] productFields = {"description", "meanings.meaning.meaning"};

        QueryParser parser = new MultiFieldQueryParser(productFields, new FrenchAnalyzer(), boostPerField);

        parser.setAllowLeadingWildcard(true);
        org.apache.lucene.search.Query luceneQuery = null;

        try {
            luceneQuery = parser.parse(pattern);
            log.debug("lucene search : #0", luceneQuery.toString());
        } catch (ParseException e) {
            log.warn("lucene query error : #0", e.getMessage());
            return null;
        }
        List<Kanji> res = null;

        try {
            res = ftem.createFullTextQuery(luceneQuery, Kanji.class)
                    .setMaxResults(pageSize)
                    .setFirstResult(page * pageSize)
                    .getResultList();
        } catch (BooleanQuery.TooManyClauses e) {
            throw new SearchTooGenericException();
        }

        return res;
    }

    public List<Kanji> findByPatternWithoutLucene(String pattern, int page, int pageSize) {
        return entityManager.createQuery("select k from Kanji k join fetch k.meanings km join fetch km.meaning m where k.kanji like :pattern or lower(m.meaning) like :pattern or lower(k.description) like :pattern")
                .setParameter("pattern", pattern)
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
    }

    @Deprecated
    public List<Kanji> findByPatternForcingFullFetch(String pattern, int page, int pageSize) {
        return entityManager.createQuery(new StringBuilder().append(
                "select k from Kanji k left join fetch k.tags t left join fetch t.tag")
                .append(" left join fetch k.meanings")
                .append(" where k.kanji like :pattern")
                .append(" or lower(k.description) like :pattern").toString())
                .setParameter("pattern", pattern)
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
    }

    public List<Kanji> findBySinceDate(Date since, int page, int pageSize) {
        return entityManager.createQuery(new StringBuilder().append(
                "select k from Kanji k left join k.tags t")
                .append(" where k.updateDate >= :since")
                .append(" or t.updateDate >= :since").toString())
                .setParameter("since", since, TemporalType.TIMESTAMP)
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
    }

    @Deprecated
    public List<Kanji> findBySinceDateForcingFullFetch(Date since, int page, int pageSize) {
        return entityManager.createQuery(new StringBuilder().append(
                "select k from Kanji k left join fetch k.tags t left join fetch t.tag")
                .append(" left join fetch k.meanings")
                .append(" where k.updateDate >= :since")
                .append(" or t.updateDate >= :since").toString())
                .setParameter("since", since, TemporalType.TIMESTAMP)
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
    }

    public List<Kanji> findByTag(String tag, int page, int pageSize) {
        return entityManager.createQuery(new StringBuilder().append(
                "select k from Kanji k left join k.tags t")
                .append(" where t.tag.code = :tag").toString())
                .setParameter("tag", tag)
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
    }

    @Deprecated
    public List<Kanji> findByTagForcingFullFetch(String tag, int page, int pageSize) {
        return entityManager.createQuery(new StringBuilder().append(
                "select k from Kanji k left join fetch k.tags t left join fetch t.tag")
                .append(" left join fetch k.meanings")
                .append(" where t.tag.code = :tag").toString())
                .setParameter("tag", tag)
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
    }

    public List<Kanji> findByRef(String reference, String value, int page, int pageSize) {
        return entityManager.createQuery(new StringBuilder().append(
                "select k from Kanji k join k.references r")
                .append(" where r.reference.value = :value")
                .append(" and r.reference.referenceType.code = :reference").toString())
                .setParameter("reference", reference)
                .setParameter("value", value)
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
    }

    @Deprecated
    public List<Kanji> findByRefForcingFullFetch(String reference, String value, int page, int pageSize) {
        return entityManager.createQuery(new StringBuilder().append(
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
        return (Long) entityManager.createQuery(new StringBuilder().append(
                "select count(k) from Kanji k join k.references r")
                .append(" where r.reference.value = :value")
                .append(" and r.reference.referenceType.code = :reference").toString())
                .setParameter("reference", reference)
                .setParameter("value", value)
                .getSingleResult();
    }

    public Kanji findByKanji(String kanji) {
        return (Kanji) entityManager.createQuery("select k from Kanji k where k.kanji = :kanji")
                .setParameter("kanji", kanji)
                .getSingleResult();
    }

    public void addTagToKanji(Kanji kanji, Tag tag) {
        KanjiTag kanjiTag = new KanjiTag(kanji, tag);
        entityManager.persist(kanjiTag);
        kanji.addTag(kanjiTag);
        entityManager.merge(kanji);
    }

    public void removeTagFromKanji(Kanji kanji, Tag tag) {
        List<KanjiTag> listKanjiTag = entityManager.createQuery("from KanjiTag kt where kt.kanji.id = :kanjiId and kt.tag.id = :tagId")
                .setParameter("kanjiId", kanji.getId())
                .setParameter("tagId", tag.getId())
                .getResultList();
        if (listKanjiTag.size() > 1) {
            log.warn("No unicity while trying to remove tag(#0) from Kanji(#1). None removed", kanji.getId(), tag.getId());
        } else if (listKanjiTag.size() == 0) {
            log.warn("No tag(#0) found for Kanji(#1). None removed", kanji.getId(), tag.getId());
        } else {
            entityManager.remove(listKanjiTag.get(0));
            kanji.removeTag(listKanjiTag.get(0));
            entityManager.merge(kanji);
        }
    }

    public void addReference(Kanji kanji, Reference reference, long referenceTypeId) {
        KanjiReference kanjiReference = new KanjiReference();
        kanjiReference.setKanji(kanji);
        kanjiReference.setReference(reference);
        //kanjiReference.setReferenceType(entityManager.find(ReferenceType.class, referenceTypeId));
        entityManager.persist(kanjiReference);
        kanji.addReference(kanjiReference);
        entityManager.merge(kanji);
    }

    public void removeReferenceFromKanji(Kanji kanji, Reference reference) {
        List<KanjiReference> listKanjiReferences = entityManager.createQuery("from KanjiReference kr where kr.kanji.id = :kanjiId and kr.reference.id = :refId")
                .setParameter("kanjiId", kanji.getId())
                .setParameter("refId", reference.getId())
                .getResultList();
        if (listKanjiReferences.size() > 1) {
            log.warn("No unicity while trying to remove reference(#0) from Kanji(#1). None removed", kanji.getId(), reference.getId());
        } else if (listKanjiReferences.size() == 0) {
            log.warn("No reference(#0) found for Kanji(#1). None removed", kanji.getId(), reference.getId());
        } else {
            entityManager.remove(listKanjiReferences.get(0));
            kanji.removeReference(listKanjiReferences.get(0));
            entityManager.merge(kanji);
        }
    }

    public void addSpellingToKanji(Kanji kanji, Spelling spelling) {
        KanjiSpelling kanjiSpelling = new KanjiSpelling();
        kanjiSpelling.setKanji(kanji);
        kanjiSpelling.setSpelling(spelling);
        entityManager.persist(kanjiSpelling);
        kanji.addSpelling(kanjiSpelling);
        entityManager.merge(kanji);
    }

    public void removeSpellingFromKanji(Kanji kanji, Spelling spelling) {
        List<KanjiSpelling> listKanjiSpellings = entityManager.createQuery("from KanjiSpelling ks where ks.kanji.id = :kanjiId and ks.spelling.id = :spellingId")
                .setParameter("kanjiId", kanji.getId())
                .setParameter("spellingId", spelling.getId())
                .getResultList();
        if (listKanjiSpellings.size() > 1) {
            log.warn("No unicity while trying to remove spelling(#0) from Kanji(#1). None removed", kanji.getId(), spelling.getId());
        } else if (listKanjiSpellings.size() == 0) {
            log.warn("No spelling(#0) found for Kanji(#1). None removed", kanji.getId(), spelling.getId());
        } else {
            entityManager.remove(listKanjiSpellings.get(0));
            kanji.removeSpelling(listKanjiSpellings.get(0));
            entityManager.merge(kanji);
        }
    }
}
