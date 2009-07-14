/******************************************************************************
 * JBoss, a division of Red Hat                                               *
 * Copyright 2006, Red Hat Middleware, LLC, and individual                    *
 * contributors as indicated by the @authors tag. See the                     *
 * copyright.txt in the distribution for a full listing of                    *
 * individual contributors.                                                   *
 *                                                                            *
 * This is free software; you can redistribute it and/or modify it            *
 * under the terms of the GNU Lesser General Public License as                *
 * published by the Free Software Foundation; either version 2.1 of           *
 * the License, or (at your option) any later version.                        *
 *                                                                            *
 * This software is distributed in the hope that it will be useful,           *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU           *
 * Lesser General Public License for more details.                            *
 *                                                                            *
 * You should have received a copy of the GNU Lesser General Public           *
 * License along with this software; if not, write to the Free                *
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA         *
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.                   *
 ******************************************************************************/
package com.kanjiportal.portal.kanji;

import com.kanjiportal.portal.dao.KanjiDao;
import com.kanjiportal.portal.model.Kanji;
import com.kanjiportal.portal.model.KanjiMeaning;
import com.kanjiportal.portal.model.Meaning;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.log.Log;

import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@Name("kanjiSearch")
@Scope(ScopeType.SESSION)
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

    @Out(required = false, scope = ScopeType.PAGE)
    private Map<Long, String> searchMeanings;

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
        kanjis = kanjiDao.findByPattern(getSearchPattern(), page, pageSize);
        searchMeanings = new HashMap<Long, String>();
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
        return searchString == null ?
                "%" : '%' + searchString.toLowerCase().replace('*', '%') + '%';
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

    //@Remove
    public void destroy() {
    }
}
