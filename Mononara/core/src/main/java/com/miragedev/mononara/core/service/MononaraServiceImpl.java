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
import com.miragedev.mononara.core.model.Knowledge;
import com.miragedev.mononara.core.dao.KnowledgeDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;

/**
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.0 $
 * @todo Add a description for the class MononaraService.java
 */
@Transactional
public class MononaraServiceImpl implements MononaraService {

    private Log log = LogFactory.getLog(MononaraServiceImpl.class);
    private DefaultHandler handlerKanji;
    private DefaultHandler handlerDictionnary;
    private DictionnaryService dictionnaryService;
    private LearningMethod learningMethod;
    private KnowledgeDao knowledgeDao;

    public void setHandlerKanji(DefaultHandler handlerKanji) {
        this.handlerKanji = handlerKanji;
    }

    public void setHandlerDictionnary(DefaultHandler handlerDictionnary) {
        this.handlerDictionnary = handlerDictionnary;
    }

    public void setDictionnaryService(DictionnaryService dictionnaryService) {
        this.dictionnaryService = dictionnaryService;
    }

    public void setKnowledgeDao(KnowledgeDao knowledgeDao) {
        this.knowledgeDao = knowledgeDao;
    }

    public void setLearningMethod(LearningMethod learningMethod) {
        this.learningMethod = learningMethod;
    }

    public MononaraServiceImpl() {
    }

    /**
     * Start the test with the basket elements.
     *
     * @return the Exam to start.
     */
    public Exam startExam(Basket basket) {
        if (basket.size() == 0) {
            log.info("Start test with an empty basket");
            return null;
        }

        Exam exam = new Exam();
        for (int i = 0; i < basket.size(); i++) {
            Collection<ExamContext> cts = dictionnaryService.getContexts(basket.getKnowledge(i));
            for (ExamContext examContext : cts) {
                exam.add(examContext);
            }
        }
        log.info("Start test: " + exam.size() + " questions.");
        return exam;
    }

    /**
     * Load the result of a user inside the system.
     *
     * @param results the results to save
     * @todo implement this
     */
    public void saveExamResults(ExamResults results) {
        log.info("Saving exam results (" + results.size() + " knowledges)");
        for(int i=0 ; i < results.size() ; i++) {
            Knowledge knowledge = results.getKnowledge(i);
            knowledge = learningMethod.updateLearningResult(knowledge,  results.getMaxScore(i), results.getScore(i));
            knowledgeDao.update(knowledge);
        }
    }

    /**
     * Load the study list into the system.
     * The process discard the old one.
     *
     * @param uriKanji the url of the file to load
     */
    public boolean onlineSync(URI uriKanji, URI uriDictionnary) {
        SAXParser parser;

        try {
            parser = SAXParserFactory.newInstance().newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }

        try {
            log.info("Loading kanji file (" + uriKanji.toString() + ")");
            parser.parse(uriKanji.toURL().openStream(), handlerKanji);
            log.info("Loading dictionnary file (" + uriDictionnary.toString() + ")");
            parser.parse(uriDictionnary.toURL().openStream(), handlerDictionnary);
            return true;
        }
        catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
        catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }
    }

}
