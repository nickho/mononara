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
import org.jboss.seam.security.Identity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 31, 2009
 * Time: 1:24:57 PM
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
@XmlTransient
public class Audit {

    @Temporal(TemporalType.TIMESTAMP)
    /*@Column(name = "audtecre")*/
    private Calendar creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    /*@Column(name = "audteupd")*/
    private Calendar updateDate;

    @Length(max = 255)
    /*@Column(name = "ausrccre")*/
    private String creationSource;

    @Length(max = 255)
    /*@Column(name = "ausrcupd")*/
    private String updateSource;

    @Length(max = 255)
    /*@Column(name = "auusrcre")*/
    private String creationUser;

    @Length(max = 255)
    /*@Column(name = "auusrupd")*/
    private String updateUser;

    @PrePersist
    public void updateCreationAuditData() {
        if (creationDate == null) {
            creationDate = Calendar.getInstance();
        }
        creationDate.setTimeInMillis(System.currentTimeMillis());
        creationSource = "kanji-portal.com";
        if (Identity.instance().isLoggedIn()) {
            creationUser = Identity.instance().getCredentials().getUsername();
        } else {
            creationUser = "web-anon";
        }
        updateUpdateAuditData();

    }

    @PreUpdate
    public void updateUpdateAuditData() {
        if (updateDate == null) {
            updateDate = Calendar.getInstance();
        }
        updateSource = "kanji-portal.com";
        if (Identity.instance().isLoggedIn()) {
            updateUser = Identity.instance().getCredentials().getUsername();
        } else {
            updateUser = "web-anon";
        }
        updateDate.setTimeInMillis(System.currentTimeMillis());
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public Calendar getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Calendar updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreationSource() {
        return creationSource;
    }

    public void setCreationSource(String creationSource) {
        this.creationSource = creationSource;
    }

    public String getUpdateSource() {
        return updateSource;
    }

    public void setUpdateSource(String updateSource) {
        this.updateSource = updateSource;
    }

    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
