package com.miragedev.mononara.core.service;

import com.miragedev.mononara.core.dao.KanjiDao;
import com.miragedev.mononara.core.dao.KnowledgeDao;
import com.miragedev.mononara.core.model.Kanji;
import com.miragedev.mononara.core.model.Knowledge;
import com.miragedev.mononara.core.model.Tag;
import com.miragedev.mononara.core.service.resources.KanjiListKP;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 4, 2008
 * Time: 7:36:16 PM
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public class KanjiServiceImpl implements KanjiService {

    private Log log = LogFactory.getLog(KanjiServiceImpl.class);
    KnowledgeDao knowledgeDao;
    KanjiDao kanjiDao;
    TagService tagService;

    public void setKnowledgeDao(KnowledgeDao knowledgeDao) {
        this.knowledgeDao = knowledgeDao;
    }

    public void setKanjiDao(KanjiDao kanjiDao) {
        this.kanjiDao = kanjiDao;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    public List<Knowledge> findKnowledgesByTag(String tag) {
        return knowledgeDao.findByTag(tag);
    }


    public Date findLastUpdate() {
        return kanjiDao.findLastUpdate();
    }

    public Kanji updateOrAdd(Kanji kanji, List<Tag> localTags) {
        List<Kanji> kanjiLocalList = kanjiDao.findByCharacter(kanji.getCharacter());
        if (kanjiLocalList.size() > 1) {
            log.warn("There is more than one kanji for char : " + kanji.getCharacter());
            return null;
        }
        Kanji kanjiLocal;
        if (kanjiLocalList.size() == 0) {
            Kanji kanjiTemp = new Kanji();
            kanjiTemp.setCharacter(kanji.getCharacter());
            kanjiDao.save(kanjiTemp);
            //todo : add more testing
            kanjiLocal = kanjiDao.findByCharacter(kanji.getCharacter()).get(0);
        } else {
            kanjiLocal = kanjiLocalList.get(0);
            kanjiLocal.setCharacter(kanji.getCharacter());
            kanjiLocal.setTags(localTags);
            kanjiLocal.setUpdate(Calendar.getInstance().getTime());
            kanjiDao.update(kanjiLocal);
        }
        return kanjiLocal;
    }

    /**
     * Merge the actual database with the one downloaded (for kanjis and tags)
     *
     * @param listKP
     */
    public void importKanjisAndTags(KanjiListKP listKP) {
        Set<Tag> tags = new HashSet<Tag>();
        for (Kanji kanji : listKP.getKanjis()) {
            if (kanji.getTags() != null) {
                for (Tag tag : kanji.getTags()) {
                    tags.add(tag);
                }
            }
        }
        for (Tag tag : tags) {
            tagService.addTagIfNew(tag.getCode(), tag.getName());
        }

        for (Kanji kanji : listKP.getKanjis()) {
            log.info(kanji.toString());
            List<Tag> localTagList = new ArrayList<Tag>();
            Kanji kanjiLocal = updateOrAdd(kanji, localTagList);
            if (kanji.getTags() != null) {
                for (Tag tag : kanji.getTags()) {
                    log.info("   " + tag.toString());
                    localTagList.add(tagService.findByCode(tag.getCode()));
                    Knowledge knowledge = knowledgeDao.findByTagAndKanji(tag.getCode(), kanji.getCharacter());
                    if (knowledge == null) {
                        knowledge = new Knowledge();
                        knowledge.setKanji(kanjiLocal);
                        knowledge.setTag(tagService.findByCode(tag.getCode()));
                        knowledgeDao.save(knowledge);
                        //Knowledge kn = knowledgeDao.findByTagAndKanji(tag.getCode(), kanji.getCharacter());
                        log.info("   kn: " + knowledge.toString());
                    } else {
                        //System.out.println("oh no! " + tag.getCode()+kanji);
                    }
                }
            }
            updateOrAdd(kanji, localTagList);
        }
        //kanjiService.flush();
    }

}
