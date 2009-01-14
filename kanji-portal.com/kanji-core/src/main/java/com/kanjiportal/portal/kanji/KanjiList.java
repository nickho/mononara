/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.kanjiportal.portal.kanji;

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

    @SuppressWarnings("unused")
    @XmlElement(name = "kanji")
    private List<Kanji> kanjiLinks;

    public KanjiList() {
    }

    public KanjiList(final List<Kanji> kanjiLinks) {
        this.kanjiLinks = kanjiLinks;
    }
}
