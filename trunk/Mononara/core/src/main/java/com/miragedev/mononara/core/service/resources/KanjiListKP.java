package com.miragedev.mononara.core.service.resources;

import com.miragedev.mononara.core.model.Kanji;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jul 26, 2009
 * Time: 9:33:53 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "kanjis")
public class KanjiListKP {

    private List<Kanji> kanjis;
    private int nbKanjis;
    private long totalCount;

    public KanjiListKP() {
    }

    public KanjiListKP(List<Kanji> kanjis, long totalCount) {
        this.kanjis = kanjis;
        this.nbKanjis = kanjis.size();
        this.totalCount = totalCount;
    }

    @XmlElement(name = "kanji")
    public List<Kanji> getKanjis() {
        return kanjis;
    }

    public void setKanjis(List<Kanji> kanjis) {
        this.kanjis = kanjis;
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

