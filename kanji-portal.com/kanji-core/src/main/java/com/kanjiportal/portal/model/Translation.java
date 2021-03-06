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

import org.hibernate.search.annotations.*;
import org.hibernate.validator.Length;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

/**
 * DictionnaryEntry
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement DictionnaryEntry
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Table(name = "tbdictra")
@Indexed
public class Translation extends Audit {

    private long id;
    private String pos;
    private String posDetail;
    private String description;
    private Language language;
    private String detail;
    private String note;
    private Dictionary dictionary;

    @Id
    @GeneratedValue
    @DocumentId
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Length(max = 20)
    @Column(name = "lbpos")
    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    @Length(max = 20)
    @Column(name = "lbposdet")
    public String getPosDetail() {
        return posDetail;
    }

    public void setPosDetail(String posDetail) {
        this.posDetail = posDetail;
    }

    @Length(max = 500)
    @XmlValue
    @Field(index = Index.TOKENIZED)
    @Column(name = "lbdsc")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToOne
    @JoinColumn(name = "idlan")
    @IndexedEmbedded
    @XmlAttribute
    @XmlIDREF
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Length(max = 500)
    @Field(index = Index.TOKENIZED)
    @Column(name = "lbdet")
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Length(max = 500)
    @Column(name = "lbnot")
    @Field(index = Index.TOKENIZED)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @ManyToOne
    @JoinColumn(name = "iddic")
    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
}