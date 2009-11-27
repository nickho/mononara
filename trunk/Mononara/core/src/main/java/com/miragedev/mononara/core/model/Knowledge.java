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
    private Calendar lastTimeSuccess;

    private float lastTestSuccess;

    @OneToOne
    private Kanji kanji;

    @OneToOne
    private Tag tag;


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

    @Override
    public String toString() {
        return "Knowledge{" +
                "id=" + id +
                ", firstTimeSuccess=" + firstTimeSuccess +
                ", lastTimeSuccess=" + lastTimeSuccess +
                ", lastTestSuccess=" + lastTestSuccess +
                ", kanji=" + kanji +
                ", tag=" + tag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Knowledge knowledge = (Knowledge) o;

        if (id != knowledge.id) return false;
        if (Float.compare(knowledge.lastTestSuccess, lastTestSuccess) != 0) return false;
        if (firstTimeSuccess != null ? !firstTimeSuccess.equals(knowledge.firstTimeSuccess) : knowledge.firstTimeSuccess != null)
            return false;
        if (!kanji.equals(knowledge.kanji)) return false;
        if (lastTimeSuccess != null ? !lastTimeSuccess.equals(knowledge.lastTimeSuccess) : knowledge.lastTimeSuccess != null)
            return false;
        if (!tag.equals(knowledge.tag)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (firstTimeSuccess != null ? firstTimeSuccess.hashCode() : 0);
        result = 31 * result + (lastTimeSuccess != null ? lastTimeSuccess.hashCode() : 0);
        result = 31 * result + (lastTestSuccess != +0.0f ? Float.floatToIntBits(lastTestSuccess) : 0);
        result = 31 * result + kanji.hashCode();
        result = 31 * result + tag.hashCode();
        return result;
    }
}
