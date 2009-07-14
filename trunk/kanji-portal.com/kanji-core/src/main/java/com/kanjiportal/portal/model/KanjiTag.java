package com.kanjiportal.portal.model;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Feb 16, 2009
 * Time: 9:06:34 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(
        name = "tbkantag",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"idkan", "idtag"})}
)
@XmlRootElement(name = "tag")
@XmlAccessorType(XmlAccessType.NONE)
public class KanjiTag extends Audit {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "idkan")
    private Kanji kanji;

    @ManyToOne
    @JoinColumn(name = "idtag")
    private Tag tag;


    public KanjiTag() {
        //Empty for jpa
    }

    @XmlID
    @XmlAttribute
    @Transient
    public String getRef() {
        return Long.toString(tag.getId());
    }

    @XmlValue
    @Transient
    public String getTagName() {
        return tag.getName();
    }


    public KanjiTag(Kanji kanji, Tag tag) {
        this.kanji = kanji;
        this.tag = tag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Kanji getKanji() {
        return kanji;
    }

    public void setKanji(Kanji kanji) {
        this.kanji = kanji;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
