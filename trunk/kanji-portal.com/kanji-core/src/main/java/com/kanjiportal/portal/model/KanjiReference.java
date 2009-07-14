package com.kanjiportal.portal.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Feb 16, 2009
 * Time: 9:24:35 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(
        name = "tbkanref",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"idkan", "idref"})
        }
)
public class KanjiReference extends Audit {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "idkan")
    private Kanji kanji;

    @ManyToOne
    @JoinColumn(name = "idref")
    private Reference reference;


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

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }
}
