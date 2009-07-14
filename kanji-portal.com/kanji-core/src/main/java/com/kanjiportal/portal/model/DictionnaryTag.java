package com.kanjiportal.portal.model;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Feb 25, 2009
 * Time: 8:20:35 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(
        name = "tbdictag",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"iddic", "idtag"})}
)
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class DictionnaryTag extends Audit {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "iddic")
    private Dictionnary dictionnary;

    @ManyToOne
    @JoinColumn(name = "idtag")
    private Tag tag;


    public DictionnaryTag() {
        //Empty for jpa
    }

    public DictionnaryTag(Dictionnary dictionnary, Tag tag) {
        this.dictionnary = dictionnary;
        this.tag = tag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @XmlElement
    @XmlID
    @Transient
    public String getRef() {
        return Long.toString(id);
    }

    public Dictionnary getDictionnary() {
        return dictionnary;
    }

    public void setDictionnary(Dictionnary dictionnary) {
        this.dictionnary = dictionnary;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

}
