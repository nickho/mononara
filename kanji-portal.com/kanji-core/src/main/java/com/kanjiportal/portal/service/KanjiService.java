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
package com.kanjiportal.portal.service;

import com.kanjiportal.portal.dao.SearchTooGenericException;
import com.kanjiportal.portal.model.service.KanjiList;

/**
 * KanjiService
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 */
public interface KanjiService {

    KanjiList getKanjisByPattern(String pattern) throws SearchTooGenericException;

    KanjiList getKanjisByPatternWithPaging(String pattern, int pageNumber, int nbItemPerPage) throws SearchTooGenericException;

    KanjiList getKanjisBySinceWithPaging(String since, int page, int itemPerPage) throws InvalidArgumentsException;

    KanjiList getKanjisBySince(String since) throws InvalidArgumentsException;

    KanjiList getKanjisByTagWithPaging(String tag, int page, int itemPerPage) throws InvalidArgumentsException;

    KanjiList getKanjisByTag(String tag) throws InvalidArgumentsException;

    KanjiList getKanjisByRefWithPaging(String reference, String value, int page, int itemPerPage) throws InvalidArgumentsException;

    KanjiList getKanjisByRef(String reference, String value) throws InvalidArgumentsException;
}
