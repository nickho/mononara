package com.miragedev.mononara.core.dao;

import com.miragedev.mononara.core.model.Kanji;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Dec 30, 2007
 * Time: 10:14:45 AM
 * To change this template use File | Settings | File Templates.
 */
public interface KanjiDao {
    public Kanji findById(long id);

    public List<Kanji> findByCharacter(String kanji);

    public void save(Kanji kanji);

    public Kanji update(Kanji kanji);

    public void delete(Kanji kanji);

}
