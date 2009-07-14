package com.kanjiportal.portal.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jul 13, 2009
 * Time: 5:26:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(
        name = "tbsplmng",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"idspl", "idmng"})}
)
public class SpellingMeaning extends Audit {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "idspl")
    private Spelling spelling;

    @ManyToOne
    @JoinColumn(name = "idmng")
    private Meaning meaning;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Spelling getSpelling() {
        return spelling;
    }

    public void setSpelling(Spelling spelling) {
        this.spelling = spelling;
    }

    public Meaning getMeaning() {
        return meaning;
    }

    public void setMeaning(Meaning meaning) {
        this.meaning = meaning;
    }
}
