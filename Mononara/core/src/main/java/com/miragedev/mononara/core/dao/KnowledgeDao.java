package com.miragedev.mononara.core.dao;

import com.miragedev.mononara.core.model.Kanji;
import com.miragedev.mononara.core.model.Knowledge;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 2, 2008
 * Time: 6:19:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface KnowledgeDao {
    public Knowledge findById(long id);

    public List<Knowledge> findByTag(String tag);

    public Knowledge findByTagAndKanji(String tag, Kanji kanji);

    public void save(Knowledge knowledge);

    public Knowledge update(Knowledge knowledge);

    public void delete(Knowledge knowledge);
}
