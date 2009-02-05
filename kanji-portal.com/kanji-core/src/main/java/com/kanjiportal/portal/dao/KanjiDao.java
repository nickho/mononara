package com.kanjiportal.portal.dao;

import com.kanjiportal.portal.model.Kanji;

import javax.ejb.Local;
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

    public List<Kanji> findByPattern(String pattern, int page, int pageSize);

    public Kanji findByKanji(String kanji);
}
