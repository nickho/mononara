/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
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
