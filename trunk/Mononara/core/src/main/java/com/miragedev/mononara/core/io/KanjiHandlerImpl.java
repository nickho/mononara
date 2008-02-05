/*****************************************
 *                                       *
 *  Mononara : The Kanji card reviewer   *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.miragedev.mononara.core.io;


import com.miragedev.mononara.core.dao.KanjiDao;
import com.miragedev.mononara.core.dao.KnowledgeDao;
import com.miragedev.mononara.core.dao.TagDao;
import com.miragedev.mononara.core.model.Kanji;
import com.miragedev.mononara.core.model.Knowledge;
import com.miragedev.mononara.core.model.Tag;
import com.miragedev.mononara.core.model.DictionnaryEntry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * KanjiHandler
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 */
public class KanjiHandlerImpl extends KanjiHandler {

    private Log log = LogFactory.getLog(KanjiHandlerImpl.class);

    private int parentId;
    private Vector<String> currentTagList;
    boolean inName;
    boolean inTag;
    boolean isNew;
    private int numberOfEntry;
    private int entryNumber;
    private List<KanjiAddedListener> listeners;
    KanjiDao kanjiDao;
    KnowledgeDao knowledgeDao;
    TagDao tagDao;


    public void setKanjiDao(KanjiDao kanjiDao) {
        this.kanjiDao = kanjiDao;
    }

    public void setKnowledgeDao(KnowledgeDao knowledgeDao) {
        this.knowledgeDao = knowledgeDao;
    }

    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }


    public KanjiHandlerImpl() {
        listeners = new Vector<KanjiAddedListener>();
        numberOfEntry = -1;
        entryNumber = 0;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);    //To change body of overridden methods use File | Settings | File Templates.
        //System.out.println("Parsing: uri("+uri+") localName("+localName+") qName("+qName+")");
        if (qName.equalsIgnoreCase("kanjis")) {
            if (attributes.getLength() > 0) {
                numberOfEntry = Integer.parseInt(attributes.getValue("size"));
            }
        } else if (qName.equalsIgnoreCase("kanji")) {
            parentId = Integer.parseInt(attributes.getValue("id"));
            Kanji kanji = kanjiDao.findById(parentId);
            isNew = false;
            currentTagList = new Vector<String>();
            if (kanji == null) {
                kanji = new Kanji();
                kanji.setId(parentId);
                kanjiDao.save(kanji);
                isNew = true;
            }
        } else if (qName.equalsIgnoreCase("name")) {
            inName = true;
            inTag = false;
        } else if (qName.equalsIgnoreCase("tag")) {
            inTag = true;
            inName = false;
        }
    }

    /**
     * Need to empty the tags that are no longer used
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("kanji")) {
            Kanji kanji = kanjiDao.findById(parentId);
            entryNumber++;
            for (KanjiAddedListener listener : listeners) {
                listener.kanjiAdded(new KanjiAddedEvent(kanji, numberOfEntry, entryNumber));
            }

            List<Tag> tags = kanji.getTags();
            List<Tag> tagsAfter = new ArrayList<Tag>();
            for (Tag tag : tags) {
                if (!currentTagList.contains(tag.getCode())) {
                    knowledgeDao.delete(knowledgeDao.findByTagAndKanji(tag.getCode(), kanji));
                    log.info("Deleted tag and Knowledge for (tag : " + tag.getCode() + ", kanji : " + kanji.getId() + ")");
                } else {
                    tagsAfter.add(tag);
                }
            }
            kanji.setTags(tagsAfter);
            kanjiDao.update(kanji);
        }
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        super.characters(ch, start, length);    //To change body of overridden methods use File | Settings | File Templates.
        //System.out.println("Processing Chars ("+String.valueOf(ch, start, length)+")");
        if (inName) {
            inName = false;
            Kanji kanji = kanjiDao.findById(parentId);
            kanji.setCharacter(String.valueOf(ch, start, length).trim());
            kanjiDao.update(kanji);
        } else if (inTag) {
            inTag = false;
            currentTagList.add(String.valueOf(ch, start, length));
            Tag tag = tagDao.findByCode(String.valueOf(ch, start, length));
            Kanji kanji = kanjiDao.findById(parentId);
            if (tag == null) {
                log.info("tag (" + String.valueOf(ch, start, length) + ") importing for kanji " + parentId);
                tag = new Tag();
                tag.setCode(String.valueOf(ch, start, length));
                tagDao.save(tag);
                log.info("tag (" + tagDao.findByCode(String.valueOf(ch, start, length)).getCode() + ") imported for kanji " + parentId);
            }

            //if (isNew) {
            List<Tag> tagsKanji = kanji.getTags();
            if (tagsKanji == null || !tagsKanji.contains(tag)) {
                log.info("Adding a new Knowledge,Tag to kanji (" + kanji.getId() + "," + tag.getCode() + ")");
                Knowledge knowledge = new Knowledge();
                knowledge.setKanji(kanji);
                knowledge.setTag(tag);
                knowledgeDao.save(knowledge);
                kanji.addTag(tag);
                kanjiDao.update(kanji);
            }
            //}

        }
    }

    /**
     * Add a new DictionnaryEntryAddedListener to the list.
     *
     * @param listener
     */
    public void addKanjiAddedListener(KanjiAddedListener listener) {
        listeners.add(listener);
    }

}
