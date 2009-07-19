package com.kanjiportal.portal.dao;

import com.kanjiportal.portal.model.Kanji;
import com.kanjiportal.portal.model.Reference;
import com.kanjiportal.portal.model.Spelling;
import com.kanjiportal.portal.model.Tag;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 24, 2009
 * Time: 10:51:35 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface KanjiDao {

    Kanji findByKanji(String kanji);

    List<Kanji> findByPattern(String pattern, int page, int pageSize);

    List<Kanji> findByPatternWithLucene(String pattern, int page, int pageSize) throws SearchTooGenericException;

    List<Kanji> findByPatternForcingFullFetch(String pattern, int page, int pageSize);

    List<Kanji> findBySinceDateForcingFullFetch(Date date, int page, int pageSize);

    List<Kanji> findByTagForcingFullFetch(String tag, int page, int itemPerPage);

    List<Kanji> findByRefForcingFullFetch(String reference, String value, int page, int itemPerPage);

    long countByRef(String reference, String value);

    void addTagToKanji(Kanji kanji, Tag tag);

    void removeTagFromKanji(Kanji kanji, Tag tagTemp);

    void addReference(Kanji kanji, Reference reference, long referenceTypeId);

    void removeReferenceFromKanji(Kanji kanji, Reference reference);

    void addSpellingToKanji(Kanji kanji, Spelling spelling);

    void removeSpellingFromKanji(Kanji kanji, Spelling spelling);

}
