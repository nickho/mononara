/*****************************************
 *
 *   MononaraService
 *
 *   Distributable under LGPL license.   
 *   See terms of license at gnu.org.    
 *
 *****************************************/
package com.miragedev.mononara.core.business;

import com.miragedev.mononara.core.model.Knowledge;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.0 $
 * @todo Add a description for the class ExamResults.java
 */
public class ExamResults {

    private List<Knowledge> knowledges;
    private List<Integer> results;
    private List<Integer> maxResults;

    public ExamResults() {
        knowledges = new ArrayList<Knowledge>();
        results = new ArrayList<Integer>();
        maxResults = new ArrayList<Integer>();
    }

    /**
     * @param knowledge
     * @param res
     * @todo javadoc and optimize method
     */
    public void add(Knowledge knowledge, boolean res) {
        int index = knowledges.indexOf(knowledge);
        if (index < 0) {
            knowledges.add(knowledge);
            results.add(0);
            maxResults.add(0);
            index = knowledges.indexOf(knowledge);
        }
        if (res) {
            results.set(index, results.get(index) + 1);
        }
        maxResults.set(index, maxResults.get(index) + 1);
    }

    public int size() {
        return results.size();
    }

    public int getScore(int index) {
        return results.get(index);
    }

    public int getMaxScore(int index) {
        return maxResults.get(index);
    }

    public Knowledge getKnowledge(int index) {
        return knowledges.get(index);
    }

}
