package com.miragedev.mononara.core.service;

import com.miragedev.mononara.core.dao.KnowledgeDao;
import com.miragedev.mononara.core.dao.TagDao;
import com.miragedev.mononara.core.model.Knowledge;
import com.miragedev.mononara.core.model.Tag;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 4, 2008
 * Time: 7:36:16 PM
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public class KanjiServiceImpl implements KanjiService {

    KnowledgeDao knowledgeDao;

    public void setKnowledgeDao(KnowledgeDao knowledgeDao) {
        this.knowledgeDao = knowledgeDao;
    }

    TagDao tagDao;

    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }


    public List<Knowledge> findKnowledgesByTag(String tag) {
        return knowledgeDao.findByTag(tag);
    }


    public List<Tag> findAllTags() {
        return tagDao.findAll();
    }


}
