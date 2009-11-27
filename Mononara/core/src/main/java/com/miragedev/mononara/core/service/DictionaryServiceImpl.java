/*****************************************
 *
 *   MononaraService
 *
 *   Distributable under LGPL license.   
 *   See terms of license at gnu.org.    
 *
 *****************************************/
package com.miragedev.mononara.core.service;

import com.miragedev.mononara.core.business.ExamContext;
import com.miragedev.mononara.core.dao.DictionaryEntryDao;
import com.miragedev.mononara.core.model.DictionaryEntry;
import com.miragedev.mononara.core.model.Knowledge;
import com.miragedev.mononara.core.model.Tag;
import com.miragedev.mononara.core.service.resources.DictionaryKP;
import com.miragedev.mononara.core.service.resources.DictionaryListKP;
import com.miragedev.mononara.core.service.resources.DictionaryTagKP;
import com.miragedev.mononara.core.service.resources.TranslationKP;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.0 $
 * @todo Add a description for the class DictionnaryService.java
 */
@Transactional
public class DictionaryServiceImpl implements DictionaryService {

    private Log log = LogFactory.getLog(DictionaryServiceImpl.class);

    DictionaryEntryDao dictionaryEntryDao;
    TagService tagService;

    public void setDictionnaryEntryDao(DictionaryEntryDao dictionaryEntryDao) {
        this.dictionaryEntryDao = dictionaryEntryDao;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    public DictionaryServiceImpl() {
    }

    /**
     * Return the Context and offset inside the context
     * where this knowledge element occur.
     *
     * @param knowledge the knowledge to look for
     * @return All the Context which contain the knowledge
     * @todo Modify to get just a subset of the matching context
     */
    public Collection<ExamContext> getContexts(Knowledge knowledge) {
        List<ExamContext> res = new ArrayList<ExamContext>();

        List<DictionaryEntry> list = dictionaryEntryDao.findBySpelling(knowledge.getKanji().getCharacter());


        DictionaryEntry prec = null;
        for (DictionaryEntry entry : list) {
            int index = -1;//fake one
            boolean hint = false;
            if (prec != null) {
                if (prec.getSpellingInKanji().equals(entry.getSpellingInKanji())) {
                    res.get(res.size() - 1).setHintNeeded(true);
                    hint = true;
                }
            }
            do {
                index = entry.indexOf(knowledge.getKanji().getCharacter(), index + 1);
                if (index >= 0) {
                    ExamContext examContext = new ExamContext(knowledge, entry, index);
                    examContext.setHintNeeded(hint);
                    res.add(examContext);
                }
            } while (index >= 0);
            prec = entry;
        }
        log.debug(knowledge.getKanji().getCharacter() + " => " + res.size() + " compounds");
        return res;
    }

    public int getDictionnarySize() {
        return dictionaryEntryDao.size();
    }

    public DictionaryEntry getEntryByNumber(long number) {
        return dictionaryEntryDao.findById(number);
    }

    public DictionaryEntry addOrUpdate(String kanji, String kana, String romaji, String description, List<Tag> tags) {
        DictionaryEntry entry = dictionaryEntryDao.findByKanjiAndKana(kanji, kana);
        if (entry == null) {
            entry = new DictionaryEntry();
            entry.setSpellingInKanji(kanji);
            entry.setSpellingInKana(kana);
            entry.setSpellingInRomaji(romaji);
            entry.setDescription(description);
            entry.setTags(tags);
            dictionaryEntryDao.save(entry);
            entry = dictionaryEntryDao.findByKanjiAndKana(kanji, kana);
        } else {
            entry.setSpellingInKanji(kanji);
            entry.setSpellingInKana(kana);
            entry.setSpellingInRomaji(romaji);
            entry.setDescription(description);
            entry.setTags(tags);
            entry = dictionaryEntryDao.update(entry);
        }
        return entry;
    }

    /**
     * Merge the actual database with the one downloaded (for dictionary and tags)
     *
     * @param listKP
     */
    public void importDictionaryAndTags(DictionaryListKP listKP) {
        /* First pass to add the non existing tags*/
        Set<DictionaryTagKP> tags = new HashSet<DictionaryTagKP>();
        for (DictionaryKP entry : listKP.getEntries()) {
            if (entry.getTags() != null) {
                for (DictionaryTagKP tag : entry.getTags().getTag()) {
                    tags.add(tag);
                }
            }
        }
        for (DictionaryTagKP tag : tags) {
            tagService.addTagIfNew(tag.getCode(), tag.getValue());
        }

        /* Second pass to add or update the entries*/
        for (DictionaryKP entry : listKP.getEntries()) {
            log.info(entry.toString());
            /* get the tags */
            List<Tag> localTags = new ArrayList<Tag>();
            for (DictionaryTagKP tag : entry.getTags().getTag()) {
                localTags.add(tagService.findByCode(tag.getCode()));
            }
            /* get the translations */
            String translation = "";
            for (TranslationKP trans : entry.getTranslations().getTranslation()) {
                if ("fr".equals(trans.getLanguage())) {
                    translation = trans.getValue();
                }
            }

            addOrUpdate(entry.getKanji(), entry.getKana(), entry.getRomaji(), translation, localTags);
        }

    }


}
