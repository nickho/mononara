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
package com.kanjiportal.portal.model;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Pattern;
import org.hibernate.validator.Range;
import static org.jboss.seam.ScopeType.SESSION;
import org.jboss.seam.annotations.Scope;

import javax.persistence.*;

/**
 * User
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement User
 */

@Entity
@Scope(SESSION)
@Table(name = "tbusr")
public class User extends Audit {
    private long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private int level;

    public User(String password, String username, int level) {
        this.level = level;
        this.password = password;
        this.username = username;
    }

    public User() {
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    @Length(max = 100)
    @Column(name = "lbfstnme")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @NotNull
    @Length(max = 100)
    @Column(name = "lblstnme")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    @NotNull
    @Length(min = 5, max = 15)
    @Column(name = "lbpwd")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Length(min = 4, max = 15)
    @Pattern(regex = "^\\w*$", message = "not a valid username")
    @Column(name = "cdusr")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @Range(min = 1, max = 10)
    @Column(name = "vllvl")
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "User(" + username + ")";
    }


}
