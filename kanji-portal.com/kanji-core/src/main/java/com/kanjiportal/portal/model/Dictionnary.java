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

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.List;

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
@Table(name = "tbdic")
public class Dictionnary extends Audit {

    private long id;
    private String romaji;
    private String kanji;
    private String kana;
    private String pos;
    private String posDetail;
    private String description;
    private Language language;
    private String detail;
    private String note;
    private List<DictionnaryTag> tags;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlAttribute
    @XmlID
    @Transient
    public String getRef() {
        return Long.toString(id);
    }

    @Length(max = 255)
    @XmlElement
    @Column(name = "lbrom")
    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    @Length(max = 255)
    @XmlElement
    @Column(name = "lbkan")
    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    @Length(max = 255)
    @XmlElement
    @Column(name = "lbkna")
    public String getKana() {
        return kana;
    }

    public void setKana(String kana) {
        this.kana = kana;
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
    @XmlElement
    @Column(name = "lbdsc")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToOne
    @JoinColumn(name = "idlan")
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Length(max = 500)
    @XmlElement
    @Column(name = "lbdet")
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Length(max = 500)
    @Column(name = "lbnot")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @OneToMany(mappedBy = "dictionnary")
    @XmlElement
    @XmlIDREF
    public List<DictionnaryTag> getTags() {
        return tags;
    }

    public void setTags(List<DictionnaryTag> tags) {
        this.tags = tags;
    }

}