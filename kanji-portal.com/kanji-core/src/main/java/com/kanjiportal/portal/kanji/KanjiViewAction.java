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
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Log;

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
public class KanjiViewAction implements KanjiView {

    private final static String ON_SPELLING_TYPE_ID = "on";
    private final static String KUN_SPELLING_TYPE_ID = "kun";

    @Logger
    private Log log;

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
    private Set<Dictionary> compounds;

    @Out
    private Kanji kanji;


    public void view() {
        kanji = kanjiDao.findById(kanjiId);
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
}
