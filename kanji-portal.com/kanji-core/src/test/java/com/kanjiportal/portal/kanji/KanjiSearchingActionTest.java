package com.kanjiportal.portal.kanji;

import com.kanjiportal.portal.dao.KanjiDao;
import com.kanjiportal.portal.model.Kanji;
import org.easymock.EasyMock;
import org.jboss.seam.log.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 25, 2009
 * Time: 11:36:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class KanjiSearchingActionTest {

    private KanjiSearchingAction action;
    private KanjiDao kanjiDaoMock;

    @Before
    public void before() {
        kanjiDaoMock = EasyMock.createMock(KanjiDao.class);
        action = new KanjiSearchingAction();
        action.setKanjiDao(kanjiDaoMock);
        action.setLogger(EasyMock.createMock(Log.class));
    }

    @Test
    public void testInitialFind() {
        EasyMock.expect(kanjiDaoMock.findByPattern("%", 0, 10)).andReturn(buildKanjiList(1));
        EasyMock.replay(kanjiDaoMock);

        action.find();

        EasyMock.verify(kanjiDaoMock);
        Assert.assertEquals("Initial find size :", 1, action.getKanjis().size());
        Assert.assertEquals("Initial find :", 1, action.getKanjis().get(0).getId());
    }

    @Test
    public void testFindUn() {
        EasyMock.expect(kanjiDaoMock.findByPattern("%un%", 0, 10)).andReturn(buildKanjiList(1));
        EasyMock.replay(kanjiDaoMock);

        action.setSearchString("un");
        action.find();

        EasyMock.verify(kanjiDaoMock);
        Assert.assertEquals("Initial find size :", 1, action.getKanjis().size());
        Assert.assertEquals("Initial find :", 1, action.getKanjis().get(0).getId());
    }

    @Test
    public void testNextPage() {
        EasyMock.expect(kanjiDaoMock.findByPattern("%", 1, 2)).andReturn(buildKanjiList(3, 4));
        EasyMock.replay(kanjiDaoMock);

        action.setPageSize(2);
        action.nextPage();

        EasyMock.verify(kanjiDaoMock);
        Assert.assertEquals("SecondPage size :", 2, action.getKanjis().size());
        Assert.assertEquals("SecondPage :", 3, action.getKanjis().get(0).getId());
        Assert.assertEquals("SecondPage :", 4, action.getKanjis().get(1).getId());
    }

    private List<Kanji> buildKanjiList(int... ids) {
        List<Kanji> kanjis = new ArrayList<Kanji>();
        for (int i = 0; i < ids.length; i++) {
            Kanji kanji = new Kanji();
            kanji.setId(ids[i]);
            kanjis.add(kanji);
        }
        return kanjis;
    }

}
