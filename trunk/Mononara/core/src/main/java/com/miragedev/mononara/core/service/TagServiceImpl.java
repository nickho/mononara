package com.miragedev.mononara.core.service;

import com.miragedev.mononara.core.dao.TagDao;
import com.miragedev.mononara.core.model.Tag;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class TagServiceImpl implements TagService {

    private Log log = LogFactory.getLog(TagServiceImpl.class);
    TagDao tagDao;

    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public List<Tag> findAllTags() {
        return tagDao.findAll();
    }

    public Tag addTagIfNew(String code, String name) {
        Tag tag = tagDao.findByCode(code);

        if (tag == null) {
            tagDao.save(new Tag(code, name));
            tag = tagDao.findByCode(code);
        } else {
            tag.setName(name);
            tag = tagDao.update(tag);
        }
        return tag;
    }

    public Tag findByCode(String code) {
        return tagDao.findByCode(code);
    }

}