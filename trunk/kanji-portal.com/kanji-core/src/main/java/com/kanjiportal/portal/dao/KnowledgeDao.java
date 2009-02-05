package com.kanjiportal.portal.dao;

import com.kanjiportal.portal.model.Knowledge;
import com.kanjiportal.portal.model.Tag;
import com.kanjiportal.portal.model.User;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 31, 2009
 * Time: 10:57:23 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface KnowledgeDao {
    public Knowledge findByKanjiAndTagForUser(String kanji, String tag, User user);

    public List<Knowledge> findByTagForUser(Tag tag, User user, int page, int pageSize);

    public Knowledge update(Knowledge knowledge);

    public void create(Knowledge knowledge);

    public void delete(Knowledge knowledge);
}
