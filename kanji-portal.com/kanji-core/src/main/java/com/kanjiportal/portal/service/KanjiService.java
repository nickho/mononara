/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal.service;

import com.kanjiportal.portal.model.service.KanjiList;

import javax.ejb.Local;

/**
 * KanjiService
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement KanjiService
 */
@Local
//@Path("/kanjis")
public interface KanjiService {

    //@GET
    //public KanjiList getKanjis();

    //@GET
    //@Path("{id}")
    //public Kanji getKanjiById(/*@PathParam("id")*/ long id);

    public KanjiList getKanjisByPattern(String pattern);

    public KanjiList getKanjisByPatternWithPaging(String pattern, int pageNumber, int nbItemPerPage);
}
