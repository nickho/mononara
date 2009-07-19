package com.kanjiportal.portal.dao;

import com.kanjiportal.portal.model.User;

import javax.ejb.Local;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 31, 2009
 * Time: 11:41:22 PM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface UserDao {
    public User findByUsername(String username);
}
