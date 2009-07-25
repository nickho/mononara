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
package com.kanjiportal.portal.model.service;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 31, 2009
 * Time: 9:50:25 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "knowledge")
public class KnowledgeParam {

    private Calendar firstTimeSuccess;
    private Calendar lastTimeSuccess;
    private float lastTestSuccess;
    private String kanji;
    private String tag;

    @XmlElement
    public Calendar getFirstTimeSuccess() {
        return firstTimeSuccess;
    }

    public void setFirstTimeSuccess(Calendar firstTimeSuccess) {
        this.firstTimeSuccess = firstTimeSuccess;
    }

    @XmlElement
    public Calendar getLastTimeSuccess() {
        return lastTimeSuccess;
    }

    public void setLastTimeSuccess(Calendar lastTimeSuccess) {
        this.lastTimeSuccess = lastTimeSuccess;
    }

    @XmlElement
    public float getLastTestSuccess() {
        return lastTestSuccess;
    }

    public void setLastTestSuccess(float lastTestSuccess) {
        this.lastTestSuccess = lastTestSuccess;
    }

    @XmlID
    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    @XmlElement
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
