package com.kanjiportal.portal.model;

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
    private Calendar lastTimeSuccess;

    private float lastTestSuccess;

    @OneToOne
    private Kanji kanji;

    @OneToOne
    private Tag tag;

    @ManyToOne
    private User user;

    @Embedded
    private Audit audit;

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

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

    public Calendar getLastTimeSuccess() {
        return lastTimeSuccess;
    }

    public void setLastTimeSuccess(Calendar lastTimeSuccess) {
        this.lastTimeSuccess = lastTimeSuccess;
    }

    public float getLastTestSuccess() {
        return lastTestSuccess;
    }

    public void setLastTestSuccess(float lastTestSuccess) {
        this.lastTestSuccess = lastTestSuccess;
    }

    public Kanji getKanji() {
        return kanji;
    }

    public void setKanji(Kanji kanji) {
        this.kanji = kanji;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
