/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal.dictionnary;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.log.Log;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * DictionnarySearchingAction
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement DictionnarySearchingAction
 */
@Stateful
@Name("dictionnarySearch")
@Scope(ScopeType.SESSION)
public class DictionnarySearchingAction implements DictionnarySearching {

    @PersistenceContext
    private EntityManager em;

    private String searchString;
    private int pageSize = 10;
    private int page;

    @DataModel
    private List<DictionnaryEntry> entries;

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
        entries = em.createQuery("select de from DictionnaryEntry de where de.kanji like #{patternDictionnary} or de.kana like #{patternDictionnary} or lower(de.description) like #{patternDictionnary}")
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

    @Remove
    public void destroy() {
    }

}
