/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal.service;

import com.kanjiportal.portal.model.service.DictionnaryList;

import javax.ejb.Local;

/**
 * DictionnaryService
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement DictionnaryService
 */
@Local
//@Path("/dictionnary")
public interface DictionnaryService {

    public DictionnaryList getDictionnaryEntriesByPattern(String pattern);

    public DictionnaryList getDictionnaryEntriesByPatternWithPaging(String pattern, int page, int pageSize);
}
