package com.kanjiportal.portal.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jul 13, 2009
 * Time: 5:22:18 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(
        name = "tbkanspl",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"idkan", "idspl"})}
)
public class KanjiSpelling extends Audit {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "idkan")
    private Kanji kanji;

    @ManyToOne
    @JoinColumn(name = "idspl")
    private Spelling spelling;


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

    public Spelling getSpelling() {
        return spelling;
    }

    public void setSpelling(Spelling spelling) {
        this.spelling = spelling;
    }
}