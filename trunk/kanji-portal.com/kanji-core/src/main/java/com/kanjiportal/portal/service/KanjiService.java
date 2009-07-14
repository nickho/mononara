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

/**
 * KanjiService
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement KanjiService
 */
//@Local
public interface KanjiService {

    KanjiList getKanjisByPattern(String pattern);

    KanjiList getKanjisByPatternWithPaging(String pattern, int pageNumber, int nbItemPerPage);

    KanjiList getKanjisBySinceWithPaging(String since, int page, int itemPerPage) throws InvalidArgumentsException;

    KanjiList getKanjisBySince(String since) throws InvalidArgumentsException;

    KanjiList getKanjisByTagWithPaging(String tag, int page, int itemPerPage) throws InvalidArgumentsException;

    KanjiList getKanjisByTag(String tag) throws InvalidArgumentsException;

    KanjiList getKanjisByRefWithPaging(String reference, String value, int page, int itemPerPage) throws InvalidArgumentsException;

    KanjiList getKanjisByRef(String reference, String value) throws InvalidArgumentsException;
}
