/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.miragedev.mononara.core.io;

import com.miragedev.mononara.core.model.Kanji;

/**
 * KanjiAddedEvent
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement KanjiAddedEvent
 */
public class KanjiAddedEvent {

    private Kanji kanji;
    private int numberOfEntry;
    private int entryNumber;

    public KanjiAddedEvent(Kanji kanji, int numberOfEntry, int entryNumber) {
        this.kanji = kanji;
        this.numberOfEntry = numberOfEntry;
        this.entryNumber = entryNumber;
    }

    public Kanji getKanji() {
        return kanji;
    }

    public void setKanji(Kanji kanji) {
        this.kanji = kanji;
    }

    public int getNumberOfEntry() {
        return numberOfEntry;
    }

    public void setNumberOfEntry(int numberOfEntry) {
        this.numberOfEntry = numberOfEntry;
    }

    public int getEntryNumber() {
        return entryNumber;
    }

    public void setEntryNumber(int entryNumber) {
        this.entryNumber = entryNumber;
    }
}
