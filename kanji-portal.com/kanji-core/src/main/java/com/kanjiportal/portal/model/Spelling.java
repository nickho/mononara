/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
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
@Table(name = "spelling")
public class Spelling {

    private long id;
    private String kana;
    private String romaji;
    private Set<Meaning> meanings;
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
    public String getKana() {
        return kana;
    }

    public void setKana(String kana) {
        this.kana = kana;
    }

    @NotNull
    @Length(max = 200)
    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public SpellingType getType() {
        return type;
    }

    public void setType(SpellingType type) {
        this.type = type;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public Set<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(Set<Meaning> meanings) {
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
