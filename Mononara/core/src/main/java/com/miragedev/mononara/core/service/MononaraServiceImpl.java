/*****************************************
 *
 *   MononaraService
 *
 *   Distributable under LGPL license.   
 *   See terms of license at gnu.org.    
 *
 *****************************************/
package com.miragedev.mononara.core.service;

import com.miragedev.mononara.core.business.*;
import com.miragedev.mononara.core.dao.KnowledgeDao;
import com.miragedev.mononara.core.model.Knowledge;
import com.miragedev.mononara.core.service.proxy.KanjiPortalResource;
import com.miragedev.mononara.core.service.resources.DictionaryListKP;
import com.miragedev.mononara.core.service.resources.KanjiListKP;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.0 $
 * @todo Add a description for the class MononaraService.java
 */
public class MononaraServiceImpl implements MononaraService {

    private static final int KANJI_PAGE_SIZE = 100;

    private Log log = LogFactory.getLog(MononaraServiceImpl.class);
    private DictionaryService dictionaryService;
    private KanjiService kanjiService;
    private TagService tagService;
    private LearningMethod learningMethod;
    private KnowledgeDao knowledgeDao;
    private KanjiPortalResource kanjiPortalResource;

    public MononaraServiceImpl() throws URISyntaxException {
        ResteasyProviderFactory resteasyProviderFactory = ResteasyProviderFactory.getInstance();
        RegisterBuiltin.register(resteasyProviderFactory);
        HttpClient httpClient = new HttpClient();
        URI uri = new URI("http://localhost:8080/kanji-portal/");
        //kanjiPortalResource = ProxyFactory.create(KanjiPortalResource.class, uri, httpClient, resteasyProviderFactory);
        kanjiPortalResource = ProxyFactory.create(KanjiPortalResource.class, "http://www.kanji-portal.com");
    }

    /**
     * Start the test with the basket elements.
     *
     * @return the Exam to start.
     */
    @Transactional
    public Exam startExam(Basket basket) {
        if (basket.size() == 0) {
            log.warn("Start test with an empty basket");
            return null;
        }

        Exam exam = new Exam();
        for (int i = 0; i < basket.size(); i++) {
            Collection<ExamContext> cts = dictionaryService.getContexts(basket.getKnowledge(i));
            for (ExamContext examContext : cts) {
                exam.add(examContext);
            }
        }
        exam.shuffle();
        log.info("Start test: " + exam.size() + " questions.");
        return exam;
    }

    /**
     * Load the result of a user inside the system.
     *
     * @param results the results to save
     * @todo implement this
     */
    @Transactional
    public void saveExamResults(ExamResults results) {
        log.info("Saving exam results (" + results.size() + " knowledges)");
        for (int i = 0; i < results.size(); i++) {
            Knowledge knowledge = results.getKnowledge(i);
            knowledge = learningMethod.updateLearningResult(knowledge, results.getMaxScore(i), results.getScore(i));
            knowledgeDao.update(knowledge);
        }
    }

    /**
     * Load the study list into the system.
     * The process discard the old one.
     */
    public boolean onlineSync() {
        Date lastUpdate = kanjiService.findLastUpdate();
        //todo : need to add thelastUpdate for Dictionnary
        if (lastUpdate == null) {
            Calendar cal = Calendar.getInstance();
            cal.set(1999, 11, 31);
            lastUpdate = cal.getTime();
        }
        log.info("Last sucessful sync was :" + lastUpdate.toString());
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
        String lastUpdateAsString = dFormat.format(lastUpdate);

        /* Import the kanjis */
        importKanjisAndTags(lastUpdateAsString);

        /* Import the dictionary entries */
        importDictionaryAndTags(lastUpdateAsString);

        return true;

    }

    private void importDictionaryAndTags(String lastUpdateAsString) {
        log.info("Loading dictionary file from resource...");
        DictionaryListKP dictionaryListKP = kanjiPortalResource.searchDictionarySinceWithPaging(lastUpdateAsString, 0, KANJI_PAGE_SIZE);
        long totalCount = dictionaryListKP.getTotal();
        int count = dictionaryListKP.getCount();
        int page = 1;

        while (count > 0) {
            log.info("import the dictionary...");
            dictionaryService.importDictionaryAndTags(dictionaryListKP);

            //fetch next
            log.info("Loading next entries from resource...");
            dictionaryListKP = kanjiPortalResource.searchDictionarySinceWithPaging(lastUpdateAsString, page, KANJI_PAGE_SIZE);
            totalCount = dictionaryListKP.getTotal();
            count = dictionaryListKP.getCount();
            page++;
        }
    }

    private void importKanjisAndTags(String lastUpdateAsString) {
        log.info("Loading kanji file from resource...");
        KanjiListKP kanjiListKP = kanjiPortalResource.findKanjisSinceWithPaging(lastUpdateAsString, 0, KANJI_PAGE_SIZE);
        long totalCount = kanjiListKP.getTotalCount();
        int count = kanjiListKP.getNbKanjis();
        int page = 1;

        while (count > 0) {
            log.info("import the kanjis...");
            kanjiService.importKanjisAndTags(kanjiListKP);

            //fetch next
            log.info("Loading next kanjis from resource...");
            kanjiListKP = kanjiPortalResource.findKanjisSinceWithPaging(lastUpdateAsString, page, KANJI_PAGE_SIZE);
            totalCount = kanjiListKP.getTotalCount();
            count = kanjiListKP.getNbKanjis();
            page++;
        }
    }


    public TagService getTagService() {
        return tagService;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    public void setDictionnaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    public void setKnowledgeDao(KnowledgeDao knowledgeDao) {
        this.knowledgeDao = knowledgeDao;
    }

    public void setLearningMethod(LearningMethod learningMethod) {
        this.learningMethod = learningMethod;
    }

    public KanjiService getKanjiService() {
        return kanjiService;
    }

    public void setKanjiService(KanjiService kanjiService) {
        this.kanjiService = kanjiService;
    }

    public KanjiPortalResource getKanjiPortalResource() {
        return kanjiPortalResource;
    }

    public void setKanjiPortalResource(KanjiPortalResource kanjiPortalResource) {
        this.kanjiPortalResource = kanjiPortalResource;
    }


}
