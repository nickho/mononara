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
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Entity
@Table(name = "tbkan")
@Indexed
public class Kanji extends Audit {
    private long id;
    private String kanji;
    private String description;
    private Set<KanjiTag> tags;
    private Set<KanjiReference> references;
    private Set<KanjiSpelling> spellings;
    private Set<KanjiMeaning> meanings;

    @Id
    @DocumentId
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Length(max = 50)
    @NotNull
    @XmlElement
    @Column(name = "lbkan")
    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    @Length(max = 500)
    @Column(name = "lbdsc")
    @Field(index = Index.TOKENIZED)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "kanji")
    @XmlElementRef
    @XmlElementWrapper(name = "tags")
    public Set<KanjiTag> getTags() {
        return tags;
    }

    public void setTags(Set<KanjiTag> tags) {
        this.tags = tags;
    }

    public void addTag(KanjiTag tag) {
        if (tags == null) {
            tags = new HashSet<KanjiTag>();
        }
        this.tags.add(tag);
    }

    public void removeTag(KanjiTag tag) {
        if (tags != null) {
            boolean ok = this.tags.remove(tag);
        }
    }

    @OneToMany(mappedBy = "kanji")
    public Set<KanjiReference> getReferences() {
        return references;
    }

    public void setReferences(Set<KanjiReference> references) {
        this.references = references;
    }


    public void addReference(KanjiReference reference) {
        if (this.references == null) {
            this.references = new HashSet<KanjiReference>();
        }
        this.references.add(reference);
    }

    public void removeReference(KanjiReference reference) {
        if (this.references != null) {
            this.references.remove(reference);
        }
    }

    @OneToMany(mappedBy = "kanji")
    public Set<KanjiSpelling> getSpellings() {
        return spellings;
    }

    public void setSpellings(Set<KanjiSpelling> spellings) {
        this.spellings = spellings;
    }

    public void addSpelling(KanjiSpelling spelling) {
        if (spelling == null) {
            this.spellings = new HashSet<KanjiSpelling>();
        }
        this.spellings.add(spelling);
    }

    public void removeSpelling(KanjiSpelling spelling) {
        if (spellings != null) {
            this.spellings.remove(spelling);
        }
    }

    @OneToMany(mappedBy = "kanji")
    @XmlElementRef
    @XmlElementWrapper(name = "meanings")
    @IndexedEmbedded
    public Set<KanjiMeaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(Set<KanjiMeaning> meanings) {
        this.meanings = meanings;
    }

    public void addMeaning(KanjiMeaning meaning) {
        if (meanings == null) {
            this.meanings = new HashSet<KanjiMeaning>();
        }
        this.meanings.add(meaning);
    }

    public void removeMeaning(KanjiMeaning meaning) {
        if (meanings != null) {
            this.meanings.remove(meaning);
        }
    }

    @Override
    public String toString() {
        return "Kanji(" + kanji + "," + description + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kanji kanji1 = (Kanji) o;

        if (id != kanji1.id) return false;
        if (description != null ? !description.equals(kanji1.description) : kanji1.description != null) return false;
        if (kanji != null ? !kanji.equals(kanji1.kanji) : kanji1.kanji != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (kanji != null ? kanji.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
