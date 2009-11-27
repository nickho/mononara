/*****************************************
 *
 *   Mononara
 *
 *   Distributable under LGPL license.
 *   See terms of license at gnu.org.
 *
 *****************************************/
package com.miragedev.mononara.core.business;

import com.miragedev.mononara.core.model.DictionaryEntry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.1 $
 */
public class Exam {

    private Log log = LogFactory.getLog(Exam.class);
    //private LearningMethod learningMethod;

    private List<ExamContext> data;
    private List<Boolean> results;
    int pos;

    /**
     * Init the Exam
     */
    public Exam() {
        pos = 0;
        data = new ArrayList<ExamContext>();
        results = new ArrayList<Boolean>();
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
    }

    public void shuffle() {
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
        results.add(pos, compareInputWithDictionary(cont.getDictionnaryEntry(), cont.getKnowledgePos(), input));
        return results.get(pos);
    }

    public boolean next() {
        pos++;
        return (pos < size());
    }

    public int getPosition() {
        return pos;
    }

    /**
     * Compare the input with the dictionary entry
     * case 1) Test first the romaji entry if its non null
     * case 2) Then test the kana entry
     * case 3) finally ignore the index and test the whole kana spelling
     *
     * @param entry
     * @param index
     * @param spelling
     * @return
     */
    private boolean compareInputWithDictionary(DictionaryEntry entry, int index, String spelling) {
        boolean res;
        String spellingInRomaji = entry.getSpellingInRomaji();
        String spellingInKana = entry.getSpellingInKana();
        String spellingInKanji = entry.getSpellingInKanji();

        if (spellingInRomaji != null) {
            /* If the romaji is present we test against it */
            String[] spellingRomajiSplited = spellingInRomaji.split("\\.");

            if (index >= spellingRomajiSplited.length) {
                LogFactory.getLog(DictionaryEntry.class).error("Entry " + spellingInKanji + " isnt legal");
                return false;
            }
            res = spellingRomajiSplited[index].equalsIgnoreCase(spelling);
        } else {
            /* If the romaji isnt present we test with the kana spelling */
            String[] spellingKanaSplited = spellingInKana.split("\\.");

            /* If the kana is splited we test only the selected char */
            if (spellingKanaSplited.length > 1) {
                if (index >= spellingKanaSplited.length) {
                    LogFactory.getLog(DictionaryEntry.class).error("Entry " + spellingInKanji + " isnt legal");
                    return false;
                }
                res = spellingKanaSplited[index].equalsIgnoreCase(spelling);
            } else {
                /* If the kana isnt splited (worst case) we test the whole word */
                res = spellingInKana.equalsIgnoreCase(spelling);
            }

        }
        return res;
    }
}
