package com.miragedev.mononara.core.dao;

import com.miragedev.mononara.core.model.Kanji;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Dec 30, 2007
 * Time: 10:14:45 AM
 * To change this template use File | Settings | File Templates.
 */
public interface KanjiDao {
    Kanji findById(long id);

    List<Kanji> findByCharacter(String kanji);

    void save(Kanji kanji);

    Kanji update(Kanji kanji);

    void delete(Kanji kanji);

    Date findLastUpdate();

    void flush();
}
