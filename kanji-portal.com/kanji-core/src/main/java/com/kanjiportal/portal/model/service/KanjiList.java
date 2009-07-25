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

import com.kanjiportal.portal.model.Kanji;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * KanjiList
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement KanjiList
 */
@XmlRootElement(name = "kanjis")
public class KanjiList {


    private List<Kanji> kanjiLinks;
    private int nbKanjis;
    private long totalCount;

    public KanjiList() {
    }

    public KanjiList(List<Kanji> kanjiLinks, long totalCount) {
        this.kanjiLinks = kanjiLinks;
        this.nbKanjis = kanjiLinks.size();
        this.totalCount = totalCount;
    }

    @XmlElement(name = "kanji")
    public List<Kanji> getKanjiLinks() {
        return kanjiLinks;
    }

    public void setKanjiLinks(List<Kanji> kanjiLinks) {
        this.kanjiLinks = kanjiLinks;
    }

    @XmlAttribute(name = "count")
    public int getNbKanjis() {
        return nbKanjis;
    }

    public void setNbKanjis(int nbKanjis) {
        this.nbKanjis = nbKanjis;
    }

    @XmlAttribute(name = "total")
    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
