package com.miragedev.mononara.core.dao;

import com.miragedev.mononara.core.model.Tag;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 2, 2008
 * Time: 10:40:40 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TagDao {
    public Tag findByCode(String code);

    public List<Tag> findAll();

    public void save(Tag tag);

    public Tag update(Tag tag);

    public void delete(Tag tag);

    public void refresh(Tag tag);

    public boolean exist(Tag tag);
}
