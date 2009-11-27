package com.miragedev.mononara.core.service;

import com.miragedev.mononara.core.business.ExamContext;
import com.miragedev.mononara.core.model.DictionaryEntry;
import com.miragedev.mononara.core.model.Knowledge;
import com.miragedev.mononara.core.model.Tag;
import com.miragedev.mononara.core.service.resources.DictionaryListKP;

import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 5, 2008
 * Time: 7:26:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DictionaryService {

    Collection<ExamContext> getContexts(Knowledge knowledge);

    int getDictionnarySize();

    DictionaryEntry getEntryByNumber(long number);

    DictionaryEntry addOrUpdate(String kanji, String kana, String romaji, String description, List<Tag> tags);

    void importDictionaryAndTags(DictionaryListKP listKP);
}
