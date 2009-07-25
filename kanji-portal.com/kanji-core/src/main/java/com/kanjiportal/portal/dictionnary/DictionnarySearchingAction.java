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
package com.kanjiportal.portal.dictionnary;

import com.kanjiportal.portal.model.Dictionnary;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.log.Log;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * DictionnarySearchingAction
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement DictionnarySearchingAction
 */
@Name("dictionnarySearch")
@Scope(ScopeType.SESSION)
@Synchronized(timeout = 10000)
public class DictionnarySearchingAction implements DictionnarySearching {

    @In
    private EntityManager entityManager;

    private String searchString;
    private int pageSize = 10;
    private int page;

    @DataModel
    private List<Dictionnary> entries;

    @Logger
    private Log log;

    public void find() {
        System.out.println("try : (" + searchString + ")");
        page = 0;
        queryDictionnary();
    }

    public void nextPage() {
        page++;
        queryDictionnary();
    }

    private void queryDictionnary() {
        log.debug("pattern dict : (#{patternDictionnary})");
        entries = entityManager.createQuery("select de from Dictionnary de where de.kanji like #{patternDictionnary} or de.kana like #{patternDictionnary} or lower(de.description) like #{patternDictionnary}")
                .setMaxResults(pageSize)
                .setFirstResult(page * pageSize)
                .getResultList();
    }

    public boolean isNextPageAvailable() {
        return entries != null && entries.size() == pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Factory(value = "patternDictionnary", scope = ScopeType.EVENT)
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

}
