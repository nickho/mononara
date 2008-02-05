/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.miragedev.mononara.core.io;


import com.miragedev.mononara.core.dao.DictionnaryEntryDao;
import com.miragedev.mononara.core.dao.TagDao;
import com.miragedev.mononara.core.model.DictionnaryEntry;
import com.miragedev.mononara.core.model.Tag;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.List;
import java.util.Vector;

/**
 * DictionnaryHandle
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 */
public class DictionnaryHandlerImpl extends DictionnaryHandler {

    private Log log = LogFactory.getLog(DictionnaryHandlerImpl.class);

    private int parentId;
    private boolean inKanji;
    private boolean inKana;
    private boolean inRomaji;
    private boolean inDescription;
    private boolean inTag;
    private boolean isNew;
    private int numberOfEntry;
    private int entryNumber;
    private List<DictionnaryEntryAddedListener> listeners;
    private DictionnaryEntryDao dictionnaryEntryDao;
    private TagDao tagDao;


    public void setDictionnaryEntryDao(DictionnaryEntryDao dictionnaryEntryDao) {
        this.dictionnaryEntryDao = dictionnaryEntryDao;
    }

    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public DictionnaryHandlerImpl() {
        listeners = new Vector<DictionnaryEntryAddedListener>();
        numberOfEntry = -1;
        entryNumber = 0;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);    //To change body of overridden methods use File | Settings | File Templates.
        //System.out.println("Parsing: uri("+uri+") localName("+localName+") qName("+qName+")");
        if (qName.equalsIgnoreCase("dictionnary")) {
            if (attributes.getLength() > 0) {
                numberOfEntry = Integer.parseInt(attributes.getValue("size"));
            }
        } else if (qName.equalsIgnoreCase("entry")) {
            parentId = Integer.parseInt(attributes.getValue("id"));
            DictionnaryEntry dictionnaryEntry = dictionnaryEntryDao.findById(parentId);
            if (dictionnaryEntry != null) {
                dictionnaryEntryDao.delete(dictionnaryEntry);
            }
            //isNew = false;
            //if (dictionnaryEntry == null) {
            dictionnaryEntry = new DictionnaryEntry();
            dictionnaryEntry.setId(parentId);
            dictionnaryEntryDao.save(dictionnaryEntry);
            isNew = true;
            //}

        } else if (qName.equalsIgnoreCase("kanji")) {
            inKanji = true;
            inKana = false;
            inRomaji = false;
            inDescription = false;
            inTag = false;
        } else if (qName.equalsIgnoreCase("kana")) {
            inKanji = false;
            inKana = true;
            inRomaji = false;
            inDescription = false;
            inTag = false;
        } else if (qName.equalsIgnoreCase("romaji")) {
            inKanji = false;
            inKana = false;
            inRomaji = true;
            inDescription = false;
            inTag = false;
        } else if (qName.equalsIgnoreCase("description")) {
            inKanji = false;
            inKana = false;
            inRomaji = false;
            inDescription = true;
            inTag = false;
        } else if (qName.equalsIgnoreCase("tag")) {
            inKanji = false;
            inKana = false;
            inRomaji = false;
            inDescription = false;
            inTag = true;
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("entry")) {
            DictionnaryEntry dictionnaryEntry = dictionnaryEntryDao.findById(parentId);
            entryNumber++;
            for (DictionnaryEntryAddedListener listener : listeners) {
                listener.entryAdded(new DictionnaryEntryAddedEvent(dictionnaryEntry, numberOfEntry, entryNumber));
            }
        }
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (inKanji) {
            inKanji = false;
            //log.info("kanji spelling (" + String.valueOf(ch, start, length) + ") importing from dictionnary " + parentId);
            DictionnaryEntry dictionnaryEntry = dictionnaryEntryDao.findById(parentId);
            dictionnaryEntry.setSpellingInKanji(String.valueOf(ch, start, length));
            dictionnaryEntryDao.update(dictionnaryEntry);
            //log.info("kanji spelling (" + String.valueOf(ch, start, length) + ") imported from dictionnary " + parentId);
        } else if (inKana) {
            inKana = false;
            DictionnaryEntry dictionnaryEntry = dictionnaryEntryDao.findById(parentId);
            dictionnaryEntry.setSpellingInKana(String.valueOf(ch, start, length));
            dictionnaryEntryDao.update(dictionnaryEntry);
        } else if (inRomaji) {
            inRomaji = false;
            DictionnaryEntry dictionnaryEntry = dictionnaryEntryDao.findById(parentId);
            dictionnaryEntry.setSpellingInRomaji(String.valueOf(ch, start, length));
            dictionnaryEntryDao.update(dictionnaryEntry);
        } else if (inDescription) {
            inDescription = false;
            DictionnaryEntry dictionnaryEntry = dictionnaryEntryDao.findById(parentId);
            dictionnaryEntry.setDescription(String.valueOf(ch, start, length));
            dictionnaryEntryDao.update(dictionnaryEntry);
        } else if (inTag) {
            inTag = false;
            Tag tag = tagDao.findByCode(String.valueOf(ch, start, length));
            DictionnaryEntry dictionnaryEntry = dictionnaryEntryDao.findById(parentId);
            if (tag == null) {
                log.info("tag (" + String.valueOf(ch, start, length) + ") importing from dictionnary " + parentId);
                tag = new Tag();
                tag.setCode(String.valueOf(ch, start, length));
                tagDao.save(tag);
                log.info("tag (" + tagDao.findByCode(String.valueOf(ch, start, length)).getCode() + ") imported from dictionnary " + parentId);
            }
            if (isNew) {
                List<Tag> tagsDictionnaryEntry = dictionnaryEntry.getTags();
                if (tagsDictionnaryEntry == null || !tagsDictionnaryEntry.contains(tag)) {
                    dictionnaryEntry.addTag(tag);
                    dictionnaryEntryDao.update(dictionnaryEntry);
                }
            }
        }
    }

    /**
     * Add a new DictionnaryEntryAddedListener to the list.
     *
     * @param listener
     */
    public void addDictionnaryEntryAddedListener(DictionnaryEntryAddedListener listener) {
        listeners.add(listener);
    }

}