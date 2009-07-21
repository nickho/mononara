/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal.dictionnary;

/**
 * DictionnarySearching
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement DictionnarySearching
 */
public interface DictionnarySearching {

    public int getPageSize();

    public void setPageSize(int pageSize);

    public String getSearchString();

    public void setSearchString(String searchString);

    public String getSearchPattern();

    public void find();

    public void nextPage();

    public boolean isNextPageAvailable();

}
