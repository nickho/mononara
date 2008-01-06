package com.miragedev.mononara.core.dao;

import com.miragedev.mononara.core.model.DictionnaryEntry;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 5, 2008
 * Time: 7:42:50 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DictionnaryEntryDao {
    public DictionnaryEntry findById(long id);

    public List<DictionnaryEntry> findBySpelling(String spelling);

    public void save(DictionnaryEntry dictionnaryEntry);

    public DictionnaryEntry update(DictionnaryEntry dictionnaryEntry);

    public void delete(DictionnaryEntry dictionnaryEntry);
}
