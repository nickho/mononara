/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.miragedev.mononara.core.io;

import com.miragedev.mononara.core.model.DictionaryEntry;

/**
 * DictionnaryEntryAddedEvent
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement DictionnaryEntryAddedEvent
 */
public class DictionaryEntryAddedEvent {

    private DictionaryEntry entry;
    private int numberOfEntry;
    private int entryNumber;

    public DictionaryEntryAddedEvent(DictionaryEntry entry, int numberOfEntry, int entryNumber) {
        this.entry = entry;
        this.numberOfEntry = numberOfEntry;
        this.entryNumber = entryNumber;
    }

    public DictionaryEntry getEntry() {
        return entry;
    }

    public int getNumberOfEntry() {
        return numberOfEntry;
    }

    public int getEntryNumber() {
        return entryNumber;
    }
}
