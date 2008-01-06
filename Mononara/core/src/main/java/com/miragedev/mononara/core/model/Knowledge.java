package com.miragedev.mononara.core.model;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Dec 30, 2007
 * Time: 10:30:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Knowledge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar firstTimeSuccess;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar lastTest;

    @OneToOne(cascade = CascadeType.ALL)
    private Kanji kanji;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Calendar getFirstTimeSuccess() {
        return firstTimeSuccess;
    }

    public void setFirstTimeSuccess(Calendar firstTimeSuccess) {
        this.firstTimeSuccess = firstTimeSuccess;
    }

    public Calendar getLastTest() {
        return lastTest;
    }

    public void setLastTest(Calendar lastTest) {
        this.lastTest = lastTest;
    }

    public Kanji getKanji() {
        return kanji;
    }

    public void setKanji(Kanji kanji) {
        this.kanji = kanji;
    }
}
