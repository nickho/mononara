package com.miragedev.mononara.core.model;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Dec 30, 2007
 * Time: 10:03:21 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Tag {

    @Id
    private String code;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (!code.equals(tag.code)) return false;

        return true;
    }

    public int hashCode() {
        return code.hashCode();
    }
}