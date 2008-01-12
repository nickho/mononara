/*****************************************
 *                                       *
 *  JBoss Portal: The OpenSource Portal  *
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
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

/**
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 * @todo Implement the parsing for kanjis
 */
public class DictionnaryHandler extends DefaultHandler {

    private Log log = LogFactory.getLog(DictionnaryHandler.class);

    private int parentId;
    boolean inKanji;
    boolean inKana;
    boolean inRomaji;
    boolean inTag;
    boolean isNew;
    DictionnaryEntryDao dictionnaryEntryDao;
    TagDao tagDao;


    public void setDictionnaryEntryDao(DictionnaryEntryDao dictionnaryEntryDao) {
        this.dictionnaryEntryDao = dictionnaryEntryDao;
    }

    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public DictionnaryHandler() {
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);    //To change body of overridden methods use File | Settings | File Templates.
        //System.out.println("Parsing: uri("+uri+") localName("+localName+") qName("+qName+")");
        if (qName.equalsIgnoreCase("entry")) {
            parentId = Integer.parseInt(attributes.getValue("id"));
            DictionnaryEntry dictionnaryEntry = dictionnaryEntryDao.findById(parentId);
            isNew = false;
            if (dictionnaryEntry == null) {
                dictionnaryEntry = new DictionnaryEntry();
                dictionnaryEntry.setId(parentId);
                dictionnaryEntryDao.save(dictionnaryEntry);
                isNew = true;
            }
        } else if (qName.equalsIgnoreCase("kanji")) {
            inKanji = true;
            inKana = false;
            inRomaji = false;
            inTag = false;
        } else if (qName.equalsIgnoreCase("kana")) {
            inKanji = false;
            inKana = true;
            inRomaji = false;
            inTag = false;
        } else if (qName.equalsIgnoreCase("romaji")) {
            inKanji = false;
            inKana = false;
            inRomaji = true;
            inTag = false;
        } else if (qName.equalsIgnoreCase("tag")) {
            inKanji = false;
            inKana = false;
            inRomaji = false;
            inTag = true;
        }
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (inKanji) {
            inKanji = false;
            log.info("kanji spelling (" + String.valueOf(ch, start, length) + ") importing from dictionnary " + parentId);
            DictionnaryEntry dictionnaryEntry = dictionnaryEntryDao.findById(parentId);
            dictionnaryEntry.setSpellingInKanji(String.valueOf(ch, start, length));
            dictionnaryEntryDao.update(dictionnaryEntry);
            log.info("kanji spelling (" + String.valueOf(ch, start, length) + ") imported from dictionnary " + parentId);
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
}