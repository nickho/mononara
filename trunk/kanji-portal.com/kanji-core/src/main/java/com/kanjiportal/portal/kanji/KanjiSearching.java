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
import com.kanjiportal.portal.model.Kanji;
import org.jboss.seam.log.Log;

import java.util.List;
import java.util.Map;

public interface KanjiSearching {
    public int getPageSize();

    public void setPageSize(int pageSize);

    public String getSearchString();

    public void setSearchString(String searchString);

    public String getSearchPattern();

    public void find();

    public void nextPage();

    public boolean isNextPageAvailable();

    public void setKanjiDao(KanjiDao kanjiDao);

    public List<Kanji> getKanjis();

    public String getSearchStatus();

    public void setLogger(Log logger);

    Map<Long, String> getSearchMeanings();

    void setSearchMeanings(Map<Long, String> searchMeanings);
}
