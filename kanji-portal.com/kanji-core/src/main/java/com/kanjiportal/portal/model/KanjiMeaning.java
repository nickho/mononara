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

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jul 13, 2009
 * Time: 5:16:16 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(
        name = "tbkanmng",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"idkan", "idmng"})}
)
@XmlRootElement(name = "meaning")
@XmlAccessorType(XmlAccessType.NONE)
@Indexed
public class KanjiMeaning extends Audit {

    @Id
    @GeneratedValue
    @DocumentId
    private long id;

    @ManyToOne
    @JoinColumn(name = "idkan")
    @ContainedIn
    private Kanji kanji;

    @ManyToOne
    @JoinColumn(name = "idmng")
    @IndexedEmbedded
    private Meaning meaning;

    @XmlValue
    @Transient
    public String getMeaningName() {
        return meaning.getMeaning();
    }

    @XmlAttribute
    @Transient
    @XmlID
    public String getRef() {
        return Long.toString(meaning.getId());
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

    public Meaning getMeaning() {
        return meaning;
    }

    public void setMeaning(Meaning meaning) {
        this.meaning = meaning;
    }
}
