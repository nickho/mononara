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

import org.hibernate.annotations.BatchSize;
import org.hibernate.search.annotations.*;
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
@Indexed
public class Dictionary extends Audit {

    private long id;
    private String romaji;
    private String kanji;
    private String kana;
    private List<DictionaryTag> tags;
    private List<Translation> translations;

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

    @Length(max = 255)
    @XmlElement
    @Field(index = Index.TOKENIZED)
    @Column(name = "lbrom")
    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    @Length(max = 255)
    @XmlElement
    @Field(index = Index.TOKENIZED)
    @Column(name = "lbkan")
    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    @Length(max = 255)
    @XmlElement
    @Field(index = Index.TOKENIZED)
    @Column(name = "lbkna")
    public String getKana() {
        return kana;
    }

    public void setKana(String kana) {
        this.kana = kana;
    }

    @OneToMany(mappedBy = "dictionary")
    @XmlElementRef
    @XmlElementWrapper(name = "tags")
    @BatchSize(size = 50)
    public List<DictionaryTag> getTags() {
        return tags;
    }

    public void setTags(List<DictionaryTag> tags) {
        this.tags = tags;
    }

    @OneToMany(mappedBy = "dictionary")
    @XmlElementRef
    @XmlElementWrapper(name = "translations")
    @BatchSize(size = 50)
    @IndexedEmbedded
    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

}
