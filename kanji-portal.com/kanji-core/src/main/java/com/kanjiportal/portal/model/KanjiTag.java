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
