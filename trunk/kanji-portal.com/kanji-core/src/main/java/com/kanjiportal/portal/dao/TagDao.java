package com.kanjiportal.portal.dao;

import com.kanjiportal.portal.model.Tag;

import javax.ejb.Local;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 31, 2009
 * Time: 11:21:43 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface TagDao {
    public Tag findByName(String tag);

    public Tag findById(long id);
}
