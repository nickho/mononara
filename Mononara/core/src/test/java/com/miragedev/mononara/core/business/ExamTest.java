package com.miragedev.mononara.core.business;

import com.miragedev.mononara.core.model.DictionaryEntry;
import com.miragedev.mononara.core.model.Kanji;
import com.miragedev.mononara.core.model.Knowledge;
import com.miragedev.mononara.core.model.Tag;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Nov 22, 2009
 * Time: 9:32:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExamTest extends TestCase {

    private Tag tagJlpt4;
    private Kanji kanjiPouet;
    private Kanji kanjiPaf;
    private Kanji kanjiHop;
    private Knowledge knowledgePouet;
    private Knowledge knowledgePaf;
    private Knowledge knowledgeHop;
    private DictionaryEntry entryPouetPafHopSplitedOk;
    private DictionaryEntry entryPouetPafHopNonSplited;

    public void setUp() {
        tagJlpt4 = new Tag();
        tagJlpt4.setCode("jlpt4");
        tagJlpt4.setName("JLPT4 Tag");

        kanjiPouet = new Kanji();
        kanjiPouet.setCharacter("pouet");
        kanjiPaf = new Kanji();
        kanjiPaf.setCharacter("paf");
        kanjiHop = new Kanji();
        kanjiHop.setCharacter("hop");

        knowledgePouet = new Knowledge();
        knowledgePouet.setKanji(kanjiPouet);
        knowledgePaf = new Knowledge();
        knowledgePaf.setKanji(kanjiPaf);
        knowledgeHop = new Knowledge();
        knowledgeHop.setKanji(kanjiHop);

        entryPouetPafHopSplitedOk = new DictionaryEntry();
        entryPouetPafHopSplitedOk.setSpellingInKanji("p.p.h");
        entryPouetPafHopSplitedOk.setSpellingInRomaji("pouet.paf.hop");
        entryPouetPafHopSplitedOk.setSpellingInKana("pouet.paf.hop");
        entryPouetPafHopNonSplited = new DictionaryEntry();
        entryPouetPafHopNonSplited.setSpellingInKanji("pph");
        entryPouetPafHopNonSplited.setSpellingInKana("pouetpafhop");

    }

    public void testTestMethodSplitedOk() {
        Exam exam = new Exam();
        exam.add(new ExamContext(knowledgePouet, entryPouetPafHopSplitedOk, 0));
        exam.add(new ExamContext(knowledgePaf, entryPouetPafHopSplitedOk, 1));
        exam.add(new ExamContext(knowledgeHop, entryPouetPafHopSplitedOk, 2));

        Assert.assertEquals(true, exam.test("pouet"));
        Assert.assertEquals(false, exam.test("paf"));
        exam.next();
        Assert.assertEquals(true, exam.test("paf"));
        Assert.assertEquals(false, exam.test("pouet"));
        exam.next();
        Assert.assertEquals(true, exam.test("hop"));
        Assert.assertEquals(false, exam.test("paf"));
    }

    public void testTestMethodNonSplited() {
        Exam exam = new Exam();
        exam.add(new ExamContext(knowledgePouet, entryPouetPafHopNonSplited, 0));
        exam.add(new ExamContext(knowledgePaf, entryPouetPafHopNonSplited, 1));
        exam.add(new ExamContext(knowledgeHop, entryPouetPafHopNonSplited, 2));

        Assert.assertEquals(true, exam.test("pouetpafhop"));
        Assert.assertEquals(false, exam.test("pouet"));
        exam.next();
        Assert.assertEquals(true, exam.test("pouetpafhop"));
        Assert.assertEquals(false, exam.test("paf"));
        exam.next();
        Assert.assertEquals(true, exam.test("pouetpafhop"));
        Assert.assertEquals(false, exam.test("hop"));
    }

    public void testBasicExamWithResult() {
        Exam exam = new Exam();
        exam.add(new ExamContext(knowledgePouet, entryPouetPafHopSplitedOk, 0));
        exam.add(new ExamContext(knowledgePaf, entryPouetPafHopSplitedOk, 1));
        exam.add(new ExamContext(knowledgeHop, entryPouetPafHopSplitedOk, 2));

        Assert.assertEquals(true, exam.test("pouet"));
        exam.next();
        Assert.assertEquals(false, exam.test("pouet"));
        exam.next();
        Assert.assertEquals(true, exam.test("hop"));

        ExamResults results = exam.getResults();
        Assert.assertEquals(3, results.size());
        Assert.assertEquals(1, results.getScore(0));
        Assert.assertEquals(0, results.getScore(1));
        Assert.assertEquals(1, results.getScore(2));
    }
}
