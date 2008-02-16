/*****************************************
 *                                       *
 *  JBoss Portal: The OpenSource Portal  *
 *                                       *
 *   Distributable under LGPL license.   *
 *   See terms of license at gnu.org.    *
 *                                       *
 *****************************************/
package com.miragedev.mononara.core.business;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.Vector;

/**
 * @author <a href="mailto:julien@jboss.org">Julien Viet</a>
 * @version $Revision: 1.1 $
 */
public class Exam {

    private Log log = LogFactory.getLog(Exam.class);
    LearningMethod learningMethod;

    Vector<ExamContext> data;
    Vector<Boolean> results;
    int pos;

    /**
     * Init the Exam
     */
    public Exam() {
        pos = 0;
        data = new Vector<ExamContext>();
        results = new Vector<Boolean>();
    }

    /**
     * Return the number of question inside the test
     *
     * @return the number of question
     */
    public int size() {
        return data.size();
    }

    public boolean getResult(int index) {
        return results.get(index);
    }

    public ExamContext getContextTest() {
        return data.get(pos);
    }

    public void add(ExamContext examContext) {
        data.add(examContext);
        Collections.shuffle(data);
    }

    /**
     * Compute the results of the test
     *
     * @return the test results
     */
    public ExamResults getResults() {
        ExamResults res = new ExamResults();
        for (int i = 0; i < data.size(); i++) {
            res.add(data.get(i).getKnowledge(), results.get(i));
        }
        return res;
    }

    public boolean test(String input) {
        ExamContext cont = data.get(pos);
        results.add(pos, cont.getDictionnaryEntry().isTheSame(cont.getKnowledgePos(), input));
        return results.get(pos);
    }

    public boolean next() {
        pos++;
        return (pos < size());
    }

    public int getPosition() {
        return pos;
    }
}
