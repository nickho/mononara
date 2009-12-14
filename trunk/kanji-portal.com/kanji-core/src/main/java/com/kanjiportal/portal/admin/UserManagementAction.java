/**
 * This file is part of kanji-portal.com.
 *
 * kanji-portal.com is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * kanji-portal.com is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with kanji-portal.com.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (c) 2008-2009 Nicolas Radde <nicolas@radde.org>
 */
package com.kanjiportal.portal.admin;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.security.management.IdentityManager;

import java.util.List;

/**
 * Seam action class to manage the users and roles.
 * This is mostly a wrapper to identityManager methods.
 * <p/>
 * User: Nickho
 * Date: Dec 9, 2009
 * Time: 8:07:48 AM
 */
@Scope(ScopeType.SESSION)
@Name("userManagement")
public class UserManagementAction implements UserManagement {

    private String username;
    private String password;
    private String role;

    @In
    private IdentityManager identityManager;

    @DataModel
    private List<String> users;

    @DataModelSelection
    private String selectedUser;

    @Out
    private List<String> roles;

    @Out(required = false)
    private List<String> userRoles;

    @Override
    public void load() {
        refreshUsers();
        refreshRoles();
    }

    @Override
    public void createUser() {
        identityManager.createUser(username, password);
        refreshUsers();
    }

    @Override
    public void createRole() {
        identityManager.createRole(role);
        refreshRoles();
    }

    @Override
    public void grantRole() {
        identityManager.grantRole(username, role);
        refreshUserRoles(username);
    }

    @Override
    public void selectUser() {
        refreshUserRoles(selectedUser);
    }

    private void refreshUsers() {
        users = identityManager.listUsers();
    }

    private void refreshRoles() {
        roles = identityManager.listRoles();
    }

    private void refreshUserRoles(String user) {
        userRoles = identityManager.getGrantedRoles(user);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
