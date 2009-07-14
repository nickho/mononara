package com.kanjiportal.portal.model;

import org.hibernate.validator.Length;
import org.jboss.seam.Component;

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
        User userLogged = (User) Component.getInstance("user");
        if (userLogged != null) {
            creationUser = userLogged.getUsername();
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
        User userLogged = (User) Component.getInstance("user");
        if (userLogged != null) {
            updateUser = userLogged.getUsername();
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
