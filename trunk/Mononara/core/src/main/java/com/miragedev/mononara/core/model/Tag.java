package com.miragedev.mononara.core.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.*;


/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Dec 30, 2007
 * Time: 10:03:21 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@XmlRootElement(name = "tag")
@XmlAccessorType(XmlAccessType.NONE)
public class Tag {

    @Id
    @XmlAttribute(name = "code")
    private String code;

    @XmlValue
    private String name;


    public Tag() {
    }

    public Tag(String code, String name) {
        setCode(code);
        setName(name);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (!code.equals(tag.code)) return false;
        if (!name.equals(tag.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}