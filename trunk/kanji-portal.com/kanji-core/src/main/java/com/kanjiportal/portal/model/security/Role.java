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
import org.jboss.seam.annotations.security.management.RoleConditional;
import org.jboss.seam.annotations.security.management.RoleGroups;
import org.jboss.seam.annotations.security.management.RoleName;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Dec 6, 2009
 * Time: 9:43:26 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "tbrle")
public class Role extends Audit {
    private Integer roleId;
    private String rolename;
    private boolean conditional;
    private Set<Role> groups;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @RoleName
    @Column(name = "lbnme")
    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    @RoleConditional
    @Column(name = "iscnd")
    public boolean isConditional() {
        return conditional;
    }

    public void setConditional(boolean conditional) {
        this.conditional = conditional;
    }

    @RoleGroups
    @ManyToMany(targetEntity = Role.class)
    @JoinTable(name = "tbrlegrp",
            joinColumns = @JoinColumn(name = "idrle"),
            inverseJoinColumns = @JoinColumn(name = "idgrp"))
    public Set<Role> getGroups() {
        return groups;
    }

    public void setGroups(Set<Role> groups) {
        this.groups = groups;
    }

}
