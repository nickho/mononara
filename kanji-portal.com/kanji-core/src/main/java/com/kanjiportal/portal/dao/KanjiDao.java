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
package com.kanjiportal.portal.dao;

import com.kanjiportal.portal.model.Kanji;
import com.kanjiportal.portal.model.Reference;
import com.kanjiportal.portal.model.Spelling;
import com.kanjiportal.portal.model.Tag;
import com.kanjiportal.portal.model.service.KanjiList;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 24, 2009
 * Time: 10:51:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface KanjiDao {

    Kanji findById(long id);

    Kanji findByKanji(String kanji);

    List<Kanji> findByPatternWithoutLucene(String pattern, int page, int pageSize);

    KanjiList findByPattern(String pattern, int page, int pageSize) throws SearchTooGenericException;

    KanjiList findByPattern(String pattern, String language, int page, int pageSize) throws SearchTooGenericException;

    @Deprecated
    List<Kanji> findByPatternForcingFullFetch(String pattern, int page, int pageSize);

    List<Kanji> findBySinceDate(Date since, int page, int pageSize);

    @Deprecated
    List<Kanji> findBySinceDateForcingFullFetch(Date date, int page, int pageSize);

    List<Kanji> findByTag(String tag, int page, int itemPerPage);

    @Deprecated
    List<Kanji> findByTagForcingFullFetch(String tag, int page, int itemPerPage);

    List<Kanji> findByRef(String reference, String value, int page, int itemPerPage);

    @Deprecated
    List<Kanji> findByRefForcingFullFetch(String reference, String value, int page, int itemPerPage);

    long countByRef(String reference, String value);

    void addTagToKanji(Kanji kanji, Tag tag);

    void removeTagFromKanji(Kanji kanji, Tag tagTemp);

    void addReference(Kanji kanji, Reference reference, long referenceTypeId);

    void removeReferenceFromKanji(Kanji kanji, Reference reference);

    void addSpellingToKanji(Kanji kanji, Spelling spelling);

    void removeSpellingFromKanji(Kanji kanji, Spelling spelling);

}
