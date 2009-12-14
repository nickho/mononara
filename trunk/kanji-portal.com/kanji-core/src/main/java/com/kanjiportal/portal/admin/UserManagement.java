package com.kanjiportal.portal.admin;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Dec 9, 2009
 * Time: 8:05:20 AM
 * To change this template use File | Settings | File Templates.
 */
public interface UserManagement {

    void createUser();

    void createRole();

    void grantRole();

    void load();

    void selectUser();
}
