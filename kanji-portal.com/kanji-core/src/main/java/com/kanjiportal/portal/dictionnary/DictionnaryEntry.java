/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal.dictionnary;

import com.kanjiportal.portal.model.Tag;
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
@Table(name = "dictionnary")
public class DictionnaryEntry {

    private long id;
    private String romaji;
    private String kanji;
    private String kana;
    private String pos;
    private String posDetail;
    private String description;
    private String language;
    private String detail;
    private String note;
    private List<Tag> tags;

    @Id
    @GeneratedValue
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
    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }

    @Length(max = 255)
    @XmlElement
    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    @Length(max = 255)
    @XmlElement
    public String getKana() {
        return kana;
    }

    public void setKana(String kana) {
        this.kana = kana;
    }

    @Length(max = 20)
    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    @Length(max = 20)
    public String getPosDetail() {
        return posDetail;
    }

    public void setPosDetail(String posDetail) {
        this.posDetail = posDetail;
    }

    @Length(max = 500)
    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Length(max = 20)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Length(max = 500)
    @XmlElement
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Length(max = 500)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @XmlElement
    @XmlIDREF
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
