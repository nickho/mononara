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