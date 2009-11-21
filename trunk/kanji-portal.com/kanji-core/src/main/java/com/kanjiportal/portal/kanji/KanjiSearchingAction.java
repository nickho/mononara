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
import com.kanjiportal.portal.dao.SearchTooGenericException;
import com.kanjiportal.portal.model.Kanji;
import com.kanjiportal.portal.model.KanjiMeaning;
import com.kanjiportal.portal.model.Meaning;
import com.kanjiportal.portal.model.service.KanjiList;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Name("kanjiSearch")
@Scope(ScopeType.SESSION)
@Synchronized(timeout = 10000)
public class KanjiSearchingAction implements KanjiSearching {

    @In
    private KanjiDao kanjiDao;

    @Logger
    private Log logger;

    private String searchString;
    private int pageSize = 10;
    private int page;

    @DataModel
    private List<Kanji> kanjis;

    @Out(required = false)
    private Map<Long, String> searchMeanings;

    @In
    private FacesMessages facesMessages;

    @In("#{localeSelector.language}")
    private String language;

    @Out
    private String searchStatus = "";

    public void find() {
        logger.info("try : (" + searchString + ")");
        page = 0;
        queryKanjis();
    }

    public void nextPage() {
        page++;
        queryKanjis();
    }

    private void queryKanjis() {
        searchStatus = "";
        searchMeanings = new HashMap<Long, String>();

        try {
            KanjiList kanjiLists = kanjiDao.findByPattern(getSearchPattern(), language, page, pageSize);
            kanjis = kanjiLists.getKanjiLinks();
            logger.debug("total number of kanji found : #0", kanjiLists.getTotalCount());
        } catch (SearchTooGenericException e) {
            logger.info("Too many clauses for search :", getSearchPattern());
            facesMessages.add("Recherhe trop générique");
        }

        for (Kanji kanji : kanjis) {
            StringBuffer meaningsTemp = new StringBuffer();
            int i = 0;
            for (KanjiMeaning km : kanji.getMeanings()) {
                Meaning meaning = km.getMeaning();
                if (i != 0) {
                    meaningsTemp.append(", ");
                }
                meaningsTemp.append(meaning.getMeaning());
                i++;
            }
            if (meaningsTemp.length() >= 60) {
                meaningsTemp.setLength(60);
                searchMeanings.put(kanji.getId(), meaningsTemp.append("...").toString());
            } else {
                searchMeanings.put(kanji.getId(), meaningsTemp.toString());
            }
        }
    }

    public boolean isNextPageAvailable() {
        return kanjis != null && kanjis.size() == pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Factory(value = "patternKanji", scope = ScopeType.EVENT)
    public String getSearchPattern() {
        //return searchString == null ? "*" : '*' + searchString + '*';
        return searchString == null ? "*" : searchString + '*';
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public void setKanjiDao(KanjiDao kanjiDao) {
        this.kanjiDao = kanjiDao;
    }

    public void setLogger(Log logger) {
        this.logger = logger;
    }

    public List<Kanji> getKanjis() {
        return kanjis;
    }

    public Map<Long, String> getSearchMeanings() {
        return searchMeanings;
    }

    public void setSearchMeanings(Map<Long, String> searchMeanings) {
        this.searchMeanings = searchMeanings;
    }

    public String getSearchStatus() {
        return searchStatus;
    }

    public void setSearchStatus(String searchStatus) {
        this.searchStatus = searchStatus;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
