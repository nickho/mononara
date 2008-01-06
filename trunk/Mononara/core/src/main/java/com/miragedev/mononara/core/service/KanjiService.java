package com.miragedev.mononara.core.service;

import com.miragedev.mononara.core.model.Knowledge;
import com.miragedev.mononara.core.model.Tag;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 4, 2008
 * Time: 7:24:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface KanjiService {
    public List<Knowledge> findKnowledgesByTag(String tag);

    public List<Tag> findAllTags();
}
