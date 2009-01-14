/******************************************************************************
 * JBoss, a division of Red Hat                                               *
 * Copyright 2006, Red Hat Middleware, LLC, and individual                    *
 * contributors as indicated by the @authors tag. See the                     *
 * copyright.txt in the distribution for a full listing of                    *
 * individual contributors.                                                   *
 *                                                                            *
 * This is free software; you can redistribute it and/or modify it            *
 * under the terms of the GNU Lesser General Public License as                *
 * published by the Free Software Foundation; either version 2.1 of           *
 * the License, or (at your option) any later version.                        *
 *                                                                            *
 * This software is distributed in the hope that it will be useful,           *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU           *
 * Lesser General Public License for more details.                            *
 *                                                                            *
 * You should have received a copy of the GNU Lesser General Public           *
 * License along with this software; if not, write to the Free                *
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA         *
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.                   *
 ******************************************************************************/
package com.kanjiportal.portal.kanji;

import com.kanjiportal.portal.Reference;
import com.kanjiportal.portal.ReferenceType;
import com.kanjiportal.portal.Tag;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Table(name = "kanji")
public class Kanji implements Serializable {
    private long id;
    private String kanji;
    private String meaning;
    private String description;
    private Set<Tag> tags;
    private Map<ReferenceType, Reference> references;
    private Set<Spelling> spellings;
    private Set<Meaning> meanings;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @XmlID
    @XmlAttribute
    @Transient
    public String getRef() {
        return Long.toString(id);
    }

    @Length(max = 50)
    @NotNull
    @XmlElement
    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    @Length(max = 255)
    @NotNull
    @XmlElement
    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Length(max = 500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @XmlElement
    @XmlIDREF
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        if (tags == null) {
            tags = new HashSet();
        }
        this.tags.add(tag);
    }

    public void removeTag(Tag tag) {
        if (tags != null) {
            boolean ok = this.tags.remove(tag);
        }
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Map<ReferenceType, Reference> getReferences() {
        return references;
    }

    public void setReferences(Map<ReferenceType, Reference> references) {
        this.references = references;
    }

    public Reference getReference(ReferenceType type) {
        if (this.references != null) {
            return references.get(type);
        }
        return null;
    }

    public void addReference(ReferenceType type, Reference reference) {
        if (this.references == null) {
            this.references = new HashMap<ReferenceType, Reference>();
        }
        this.references.put(type, reference);
    }

    public void removeReference(ReferenceType type) {
        if (this.references != null) {
            this.references.remove(type);
        }
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<Spelling> getSpellings() {
        return spellings;
    }

    public void setSpellings(Set<Spelling> spellings) {
        this.spellings = spellings;
    }

    public void addSpelling(Spelling spelling) {
        if (spelling == null) {
            this.spellings = new HashSet();
        }
        this.spellings.add(spelling);
    }

    public void removeSpelling(Spelling spelling) {
        if (spellings != null) {
            this.spellings.remove(spelling);
        }
    }

    @OneToMany(fetch = FetchType.EAGER)
    public Set<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(Set<Meaning> meanings) {
        this.meanings = meanings;
    }

    @Override
    public String toString() {
        return "Kanji(" + kanji + "," + meaning + "," + description + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kanji kanji1 = (Kanji) o;

        if (id != kanji1.id) return false;
        if (description != null ? !description.equals(kanji1.description) : kanji1.description != null) return false;
        if (kanji != null ? !kanji.equals(kanji1.kanji) : kanji1.kanji != null) return false;
        if (meaning != null ? !meaning.equals(kanji1.meaning) : kanji1.meaning != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (kanji != null ? kanji.hashCode() : 0);
        result = 31 * result + (meaning != null ? meaning.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
