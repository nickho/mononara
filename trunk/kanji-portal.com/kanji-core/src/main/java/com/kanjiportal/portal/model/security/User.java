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
package com.kanjiportal.portal.model.security;

import com.kanjiportal.portal.model.Audit;
import org.jboss.seam.annotations.security.management.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Dec 6, 2009
 * Time: 9:42:41 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "tbusr")
public class User extends Audit {

    private Integer userId;
    private String username;
    private String passwordHash;
    private Set<Role> roles;
    private String firstname;
    private String lastname;
    private boolean enabled;

    @Id
    @Column(name = "id")
    @GeneratedValue
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @UserPrincipal
    //@Length(min = 4, max = 15)
    //@Pattern(regex = "^\\w*$", message = "not a valid username")
    @Column(name = "cdusr")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @UserPassword(hash = "md5")
    @Column(name = "lbpwd")
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @UserFirstName
    @Column(name = "lbfstnme")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @UserLastName
    @Column(name = "lblstnme")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @UserEnabled
    @Column(name = "isele")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @UserRoles
    @ManyToMany(targetEntity = Role.class)
    @JoinTable(name = "tbusrrle",
            joinColumns = @JoinColumn(name = "idusr"),
            inverseJoinColumns = @JoinColumn(name = "idrle"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
