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
import org.jboss.seam.log.Log;

import javax.persistence.EntityManager;
import java.util.Set;

/**
 * KanjiView
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement KanjiView
 */
public interface KanjiView {

    void view();

    Log getLog();

    void setLog(Log log);

    EntityManager getEntityManager();

    void setEntityManager(EntityManager entityManager);

    KanjiDao getKanjiDao();

    void setKanjiDao(KanjiDao kanjiDao);

    long getKanjiId();

    void setKanjiId(long kanjiId);

    Set<Spelling> getOtherSpellings();

    void setOtherSpellings(Set<Spelling> otherSpellings);

    Set<Spelling> getJapaneseSpellings();

    void setJapaneseSpellings(Set<Spelling> japaneseSpellings);

    Set<KanjiReference> getReferences();

    void setReferences(Set<KanjiReference> references);

    Set<KanjiMeaning> getMeanings();

    void setMeanings(Set<KanjiMeaning> meanings);

    Set<KanjiTag> getTags();

    void setTags(Set<KanjiTag> tags);

    Set<Dictionnary> getCompounds();

    void setCompounds(Set<Dictionnary> compounds);

    Kanji getKanji();

    void setKanji(Kanji kanji);
}
