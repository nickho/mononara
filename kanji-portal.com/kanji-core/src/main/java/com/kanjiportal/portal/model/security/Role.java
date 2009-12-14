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
