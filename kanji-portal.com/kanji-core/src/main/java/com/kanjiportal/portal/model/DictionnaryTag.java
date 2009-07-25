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