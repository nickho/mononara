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
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.util.Set;

/**
 * SpellingValue
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement SpellingValue
 */
@Entity
@Table(name = "tbspl")
public class Spelling extends Audit {

    private long id;
    private String kana;
    private String romaji;
    private Set<SpellingMeaning> meanings;
    private SpellingType type;


    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    @Length(max = 200)
    @Column(name = "lbkna")
    public String getKana() {
        return kana;
    }

    public void setKana(String kana) {
        this.kana = kana;
    }

    @NotNull
    @Length(max = 200)
    @Column(name = "lbrmj")
    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    @ManyToOne
    @JoinColumn(name = "idspltyp")
    public SpellingType getType() {
        return type;
    }

    public void setType(SpellingType type) {
        this.type = type;
    }

    @OneToMany(mappedBy = "spelling")
    public Set<SpellingMeaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(Set<SpellingMeaning> meanings) {
        this.meanings = meanings;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Spelling spelling = (Spelling) o;

        if (id != spelling.id) return false;
        if (kana != null ? !kana.equals(spelling.kana) : spelling.kana != null) return false;
        if (romaji != null ? !romaji.equals(spelling.romaji) : spelling.romaji != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (kana != null ? kana.hashCode() : 0);
        result = 31 * result + (romaji != null ? romaji.hashCode() : 0);
        return result;
    }
}
