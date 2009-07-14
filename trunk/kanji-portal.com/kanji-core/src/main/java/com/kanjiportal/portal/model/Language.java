package com.kanjiportal.portal.model;

import org.hibernate.validator.NotNull;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jul 13, 2009
 * Time: 6:02:11 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "tblan")
public class Language extends Audit {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(name = "lblan")
    private String name;

    @NotNull
    @Column(name = "cdiso63901")
    private String codeIso63901;

    @NotNull
    @Column(name = "cdiso63902")
    private String codeIso63902;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeIso63901() {
        return codeIso63901;
    }

    public void setCodeIso63901(String codeIso63901) {
        this.codeIso63901 = codeIso63901;
    }

    public String getCodeIso63902() {
        return codeIso63902;
    }

    public void setCodeIso63902(String codeIso63902) {
        this.codeIso63902 = codeIso63902;
    }
}
