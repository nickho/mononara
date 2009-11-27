package com.miragedev.mononara.core.service;

import com.miragedev.mononara.core.model.Kanji;
import com.miragedev.mononara.core.model.Knowledge;
import com.miragedev.mononara.core.model.Tag;
import com.miragedev.mononara.core.service.resources.KanjiListKP;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 4, 2008
 * Time: 7:24:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface KanjiService {
    List<Knowledge> findKnowledgesByTag(String tag);

    Date findLastUpdate();

    Kanji updateOrAdd(Kanji kanji, List<Tag> localTags);

    void importKanjisAndTags(KanjiListKP listKP);
}
