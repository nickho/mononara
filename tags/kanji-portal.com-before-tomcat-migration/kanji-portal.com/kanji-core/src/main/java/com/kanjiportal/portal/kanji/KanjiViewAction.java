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
import com.kanjiportal.portal.model.*;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Log;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

/**
 * KanjiViewAction
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement KanjiViewAction
 */
@Name("kanjiView")
@Scope(ScopeType.SESSION)
@Stateless
public class KanjiViewAction implements KanjiView {

    private final static String ON_SPELLING_TYPE_ID = "on";
    private final static String KUN_SPELLING_TYPE_ID = "kun";

    @Logger
    private Log log;

    @In
    private EntityManager entityManager;

    @In
    private KanjiDao kanjiDao;

    @RequestParameter
    private long kanjiId;

    @DataModel
    private Set<Spelling> otherSpellings;

    @DataModel
    private Set<Spelling> japaneseSpellings;

    @DataModel
    private Set<KanjiReference> references;

    @DataModel
    private Set<KanjiMeaning> meanings;

    @DataModel
    private Set<KanjiTag> tags;

    @DataModel
    private Set<Dictionnary> compounds;

    @Out
    private Kanji kanji;


    public void view() {
        kanji = (Kanji) entityManager.createQuery("select k from Kanji k where k.id = :id")
                .setParameter("id", kanjiId)
                .getSingleResult();
        log.debug("Kanji set to : #0", kanji.getId());

        //Meanings
        meanings = kanji.getMeanings();
        log.debug("Nb Meanings : #0", meanings.size());

        //References
        references = kanji.getReferences();
        log.debug("Nb References : #0", references.size());

        //Spellings
        otherSpellings = new HashSet<Spelling>();
        japaneseSpellings = new HashSet<Spelling>();
        for (KanjiSpelling kanjiSpelling : kanji.getSpellings()) {
            Spelling spelling = kanjiSpelling.getSpelling();
            String type = spelling.getType().getCode();
            if (!ON_SPELLING_TYPE_ID.equals(type) || !KUN_SPELLING_TYPE_ID.equals(type)) {
                otherSpellings.add(spelling);
            } else {
                japaneseSpellings.add(spelling);
            }
        }

        //Tags
        tags = kanji.getTags();
        log.debug("Nb Tags : #0", tags.size());
    }


    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public KanjiDao getKanjiDao() {
        return kanjiDao;
    }

    public void setKanjiDao(KanjiDao kanjiDao) {
        this.kanjiDao = kanjiDao;
    }

    public long getKanjiId() {
        return kanjiId;
    }

    public void setKanjiId(long kanjiId) {
        this.kanjiId = kanjiId;
    }

    public Set<Spelling> getOtherSpellings() {
        return otherSpellings;
    }

    public void setOtherSpellings(Set<Spelling> otherSpellings) {
        this.otherSpellings = otherSpellings;
    }

    public Set<Spelling> getJapaneseSpellings() {
        return japaneseSpellings;
    }

    public void setJapaneseSpellings(Set<Spelling> japaneseSpellings) {
        this.japaneseSpellings = japaneseSpellings;
    }

    public Set<KanjiReference> getReferences() {
        return references;
    }

    public void setReferences(Set<KanjiReference> references) {
        this.references = references;
    }

    public Set<KanjiMeaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(Set<KanjiMeaning> meanings) {
        this.meanings = meanings;
    }

    public Set<KanjiTag> getTags() {
        return tags;
    }

    public void setTags(Set<KanjiTag> tags) {
        this.tags = tags;
    }

    public Set<Dictionnary> getCompounds() {
        return compounds;
    }

    public void setCompounds(Set<Dictionnary> compounds) {
        this.compounds = compounds;
    }

    public Kanji getKanji() {
        return kanji;
    }

    public void setKanji(Kanji kanji) {
        this.kanji = kanji;
    }
}
