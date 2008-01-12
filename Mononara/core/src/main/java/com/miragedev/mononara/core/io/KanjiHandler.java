/*****************************************
 *                                       *
 *  JBoss Portal: The OpenSource Portal  *
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

/**
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 */
public class KanjiHandler extends DefaultHandler {

    private Log log = LogFactory.getLog(KanjiHandler.class);

    private int parentId;
    boolean inName;
    boolean inTag;
    boolean isNew;
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


    public KanjiHandler() {
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);    //To change body of overridden methods use File | Settings | File Templates.
        //System.out.println("Parsing: uri("+uri+") localName("+localName+") qName("+qName+")");
        if (qName.equalsIgnoreCase("kanji")) {
            parentId = Integer.parseInt(attributes.getValue("id"));
            Kanji kanji = kanjiDao.findById(parentId);
            isNew = false;
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
            Tag tag = tagDao.findByCode(String.valueOf(ch, start, length));
            Kanji kanji = kanjiDao.findById(parentId);
            if (tag == null) {
                log.info("tag (" + String.valueOf(ch, start, length) + ") importing from kanji " + parentId);
                tag = new Tag();
                tag.setCode(String.valueOf(ch, start, length));
                tagDao.save(tag);
                log.info("tag (" + tagDao.findByCode(String.valueOf(ch, start, length)).getCode() + ") imported from kanji " + parentId);
            }

            if (isNew) {
                List<Tag> tagsKanji = kanji.getTags();
                if (tagsKanji == null || !tagsKanji.contains(tag)) {
                    Knowledge knowledge = new Knowledge();
                    knowledge.setKanji(kanji);
                    knowledge.setTag(tag);
                    knowledgeDao.save(knowledge);
                    kanji.addTag(tag);
                    kanjiDao.update(kanji);
                }
            }
        }
    }
}
