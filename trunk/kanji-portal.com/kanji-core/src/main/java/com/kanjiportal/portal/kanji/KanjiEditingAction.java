/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal.kanji;

import com.kanjiportal.portal.dao.KanjiDao;
import com.kanjiportal.portal.dao.ReferenceDao;
import com.kanjiportal.portal.dao.TagDao;
import com.kanjiportal.portal.model.*;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.core.Events;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * KanjiEditingAction
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 */

@Name("kanjiEdit")
@Scope(ScopeType.SESSION)
/*@Restrict("#{identity.loggedIn}")*/
public class KanjiEditingAction implements KanjiEditing {

    /* Seam management */
    @In
    private EntityManager entityManager;

    @In
    private TagDao tagDao;

    @In
    private KanjiDao kanjiDao;

    @In
    private ReferenceDao referenceDao;

    @In
    private FacesMessages facesMessages;

    @In
    private Events events;

    @Logger
    private Log log;


    /* Kanji management */
    @Out
    private Kanji kanji;

    @RequestParameter
    @Out(required = false)
    private String kanjiId;

    /* Spelling managenement */
    @DataModel
    private Set<KanjiSpelling> spellings;

    private long spellingTypeId;

    private String spellingKana;

    private String spellingRomaji;

    private String spellingMeanings;

    private Map<String, Long> spellingTypesAvailables;

    /* Tags management */
    @DataModel
    private Set<KanjiTag> tags;

    private long tagId;

    private Map<String, Long> tagsAvailables;

    /* References management */
    @DataModel
    private Set<KanjiReference> references;

    private long referenceTypeId;

    private String referenceValue;

    private Map<String, Long> referencesTypesAvalaibles;


    public void edit() {
        log.info("Begin editing Kanji : #0", kanjiId);
        if (kanjiId != null) {
            this.kanji = (Kanji) entityManager.createQuery("select distinct k from Kanji k where k.id = :id")
                    .setParameter("id", Long.parseLong(kanjiId))
                    .getSingleResult();
        }

        log.info("Getting tags for Kanji : #0", kanjiId);
        this.tags = kanji.getTags();


        log.info("Getting tags available");
        List<Tag> tagsTemp = entityManager.createQuery("select t from Tag t order by t.name").getResultList();
        tagsAvailables = new HashMap<String, Long>();
        for (Tag tag : tagsTemp) {
            tagsAvailables.put(tag.getName(), tag.getId());
        }

        log.info("Getting Spellings for Kanji : #0", kanjiId);
        this.spellings = kanji.getSpellings();

        log.info("Getting Spellings available");
        List<SpellingType> spellingsTypesTemp = entityManager.createQuery("select st from SpellingType st order by st.name").getResultList();
        spellingTypesAvailables = new HashMap<String, Long>();
        for (SpellingType st : spellingsTypesTemp) {
            spellingTypesAvailables.put(st.getName(), st.getId());
        }

        log.info("Getting references for Kanji : #0", kanjiId);
        this.references = kanji.getReferences();

        log.info("Getting references available");
        List<ReferenceType> refTypesTemp = (List<ReferenceType>) entityManager.createQuery("select rt from ReferenceType rt order by rt.name").getResultList();
        referencesTypesAvalaibles = new HashMap<String, Long>();
        for (ReferenceType rt : refTypesTemp) {
            referencesTypesAvalaibles.put(rt.getName(), rt.getId());
        }

    }

    public void saveMainInfos() {
        log.info("Saving Main Infos for Kanji : #{kanji.id}");
        kanji = entityManager.merge(kanji);
    }

    public void addTag() {
        Tag tagTemp = tagDao.findById(tagId);
        log.info("Adding new tag(#0) for : #{kanji.id}", tagTemp.getName());
        kanjiDao.addTagToKanji(kanji, tagTemp);
        //kanji = entityManager.merge(kanji);
    }

    public void removeTag(long tagId) {
        Tag tagTemp = tagDao.findById(tagId);
        //log.info("count tags ref before : #0", kanji.getTags().size());
        log.info("Removing tag : #0 (#1) from #{kanji.id} (#{kanji.kanji})", tagTemp.getId(), tagTemp.getName());
        kanjiDao.removeTagFromKanji(kanji, tagTemp);
        //log.info("count tags ref after : #0", kanji.getTags().size());
        //kanji = entityManager.merge(kanji);
    }

    public void addSpelling() {
        log.info("Add Spelling");
        SpellingType st = entityManager.find(SpellingType.class, spellingTypeId);
        Spelling spelling = new Spelling();
        spelling.setType(st);
        spelling.setKana(spellingKana);
        spelling.setRomaji(spellingRomaji);
        entityManager.persist(spelling);
        kanjiDao.addSpellingToKanji(kanji, spelling);
        kanji = entityManager.merge(kanji);
    }

    public void removeSpelling(long spellingId) {
        log.info("remove Spelling : #0", spellingId);
        Spelling spelling = entityManager.find(Spelling.class, spellingId);
        kanjiDao.removeSpellingFromKanji(kanji, spelling);
        kanji = entityManager.merge(kanji);
    }


    public void addReference() {
        log.info("Add reference (#0, #1) to kanji #{kanji.id}", referenceTypeId, referenceValue);
        //ReferenceType refTypeTemp = entityManager.find(ReferenceType.class, referenceTypeId);
        Reference reference = referenceDao.findReferenceByKanjiAndType(kanji, referenceTypeId);
        if (reference == null) {
            reference = referenceDao.addReference(referenceValue);
            kanjiDao.addReference(kanji, reference, referenceTypeId);
        } else {
            referenceDao.updateReferenceValue(reference, referenceValue);
        }
    }

    public void removeReference(long refTypeId) {
        log.info("Remove reference (#0) from kanji #{kanji.id}", refTypeId);
        Reference reference = referenceDao.findReferenceByKanjiAndType(kanji, referenceTypeId);
        kanjiDao.removeReferenceFromKanji(kanji, reference);
        referenceDao.removeReference(reference);
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public long getTagId() {
        return tagId;
    }

    public Map<String, Long> getTagsAvailables() {
        return tagsAvailables;
    }

    public long getSpellingTypeId() {
        return spellingTypeId;
    }

    public void setSpellingTypeId(long spellingTypeId) {
        this.spellingTypeId = spellingTypeId;
    }

    public String getSpellingKana() {
        return spellingKana;
    }

    public void setSpellingKana(String spellingKana) {
        this.spellingKana = spellingKana;
    }

    public String getSpellingRomaji() {
        return spellingRomaji;
    }

    public void setSpellingRomaji(String spellingRomaji) {
        this.spellingRomaji = spellingRomaji;
    }

    public String getSpellingMeanings() {
        return spellingMeanings;
    }

    public void setSpellingMeanings(String spellingMeanings) {
        this.spellingMeanings = spellingMeanings;
    }

    public Map<String, Long> getSpellingTypesAvailables() {
        return spellingTypesAvailables;
    }

    public long getReferenceTypeId() {
        return referenceTypeId;
    }

    public void setReferenceTypeId(long referenceTypeId) {
        this.referenceTypeId = referenceTypeId;
    }

    public String getReferenceValue() {
        return referenceValue;
    }

    public void setReferenceValue(String referenceValue) {
        this.referenceValue = referenceValue;
    }

    public Map<String, Long> getReferencesTypesAvailables() {
        return referencesTypesAvalaibles;
    }

    public void exit() {
        log.info("End editing Kanji : #{kanji.id}");
    }

}
