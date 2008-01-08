package com.miragedev.mononara.core.model;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 8, 2008
 * Time: 6:45:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class DictionnaryEntryTest extends TestCase {

    public void testIsTheSame() {
        DictionnaryEntry entry = new DictionnaryEntry();
        entry.setSpellingInKanji("???");
        entry.setSpellingInKana("?.??.?");
        entry.setSpellingInRomaji("ka.you.bi");

        Assert.assertTrue(entry.isTheSame(0, "ka"));
        Assert.assertTrue(entry.isTheSame(1, "you"));
        Assert.assertTrue(entry.isTheSame(2, "bi"));
        Assert.assertTrue(entry.isTheSame(0, "?"));
        Assert.assertTrue(entry.isTheSame(1, "??"));
        Assert.assertTrue(entry.isTheSame(2, "?"));

        Assert.assertFalse(entry.isTheSame(0, "ga"));
        Assert.assertFalse(entry.isTheSame(1, "ka"));
        Assert.assertFalse(entry.isTheSame(1, "?"));
    }
}
