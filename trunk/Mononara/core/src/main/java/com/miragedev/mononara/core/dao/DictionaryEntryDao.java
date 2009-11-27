package com.miragedev.mononara.core.dao;

import com.miragedev.mononara.core.model.DictionaryEntry;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 5, 2008
 * Time: 7:42:50 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DictionaryEntryDao {
    public DictionaryEntry findById(long id);

    public List<DictionaryEntry> findBySpelling(String spelling);

    public void save(DictionaryEntry dictionnaryEntry);

    public DictionaryEntry update(DictionaryEntry dictionnaryEntry);

    public void delete(DictionaryEntry dictionnaryEntry);

    public int size();

    public DictionaryEntry findByNumber(int number);

    DictionaryEntry findByKanjiAndKana(String kanji, String kana);
}
