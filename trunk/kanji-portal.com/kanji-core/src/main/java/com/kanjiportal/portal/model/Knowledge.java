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
@Table(name = "tbknw")
public class Knowledge extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dtfsttimsuc")
    private Calendar firstTimeSuccess;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dtlsttimsuc")
    private Calendar lastTimeSuccess;

    @Column(name = "vllsttstsuc")
    private float lastTestSuccess;

    @OneToOne
    @JoinColumn(name = "idkan")
    private Kanji kanji;

    @OneToOne
    @JoinColumn(name = "idtag")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "idusr")
    private User user;

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
