package com.kanjiportal.portal.model;

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
public class KanjiMeaning extends Audit {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "idkan")
    private Kanji kanji;

    @ManyToOne
    @JoinColumn(name = "idmng")
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
