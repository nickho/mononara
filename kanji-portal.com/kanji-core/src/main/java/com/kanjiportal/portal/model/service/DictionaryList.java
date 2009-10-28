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

import com.kanjiportal.portal.model.Dictionary;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Dictionnary
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement Dictionnary
 */

@XmlRootElement(name = "dictionary")
@XmlAccessorType(XmlAccessType.NONE)
public class DictionaryList {

    private List<Dictionary> dictionary;
    private int nbEntry;
    private long totalCount;

    public DictionaryList() {
    }

    public DictionaryList(List<Dictionary> dictionary, long total) {
        this.dictionary = dictionary;
        this.nbEntry = dictionary.size();
        this.totalCount = total;
    }

    @XmlElement(name = "entry")
    public List<Dictionary> getDictionary() {
        return dictionary;
    }

    public void setDictionary(List<Dictionary> dictionary) {
        this.dictionary = dictionary;
    }

    @XmlAttribute(name = "count")
    public int getNbEntry() {
        return nbEntry;
    }

    public void setNbEntry(int nbEntry) {
        this.nbEntry = nbEntry;
    }

    @XmlAttribute(name = "total")
    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
