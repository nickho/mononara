package com.miragedev.mononara.core.service.resources;

import junit.framework.Assert;
import junit.framework.TestCase;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Nov 22, 2009
 * Time: 7:06:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class DictionaryListKPTest extends TestCase {

    public void testUnmarshalingDictionaryKP1000() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(DictionaryListKP.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        DictionaryListKP dictionaryListKP = (DictionaryListKP) unmarshaller.unmarshal(DictionaryListKP.class.getResourceAsStream("dictionarylist-1000.xml"));
        Assert.assertNotNull(dictionaryListKP);
        Assert.assertEquals(1000, dictionaryListKP.getCount());
        Assert.assertEquals(-1, dictionaryListKP.getTotal());
        for (DictionaryKP entry : dictionaryListKP.getEntries()) {
            Assert.assertNotNull(entry.getKanji());
            List<DictionaryTagKP> tags = entry.getTags().getTag();
            if (tags != null) {
                Assert.assertNotNull(tags.size() > 0);
                for (DictionaryTagKP tag : tags) {
                    Assert.assertNotNull(tag.getCode());
                }
            }
            List<TranslationKP> transs = entry.getTranslations().getTranslation();
            if (transs != null) {
                Assert.assertNotNull(transs.size() > 0);
                for (TranslationKP trans : transs) {
                    Assert.assertNotNull(trans.getLanguage());
                    if ("zz".equals(trans.getLanguage())) {
                        Assert.assertEquals("", trans.getValue());
                    } else {
                        Assert.assertNotNull(trans.getValue());
                    }
                }
            }
        }
    }

    public void testUnmarshalingDictionary100() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(DictionaryListKP.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        DictionaryListKP dictionaryListKP = (DictionaryListKP) unmarshaller.unmarshal(DictionaryListKP.class.getResourceAsStream("dictionarylist-100.xml"));
        Assert.assertNotNull(dictionaryListKP);
        Assert.assertEquals(100, dictionaryListKP.getCount());
        Assert.assertEquals(-1, dictionaryListKP.getTotal());
        for (DictionaryKP entry : dictionaryListKP.getEntries()) {
            Assert.assertNotNull(entry.getKanji());
            List<DictionaryTagKP> tags = entry.getTags().getTag();
            if (tags != null) {
                Assert.assertNotNull(tags.size() > 0);
                for (DictionaryTagKP tag : tags) {
                    Assert.assertNotNull(tag.getCode());
                }
            }
            List<TranslationKP> transs = entry.getTranslations().getTranslation();
            if (transs != null) {
                Assert.assertNotNull(transs.size() > 0);
                for (TranslationKP trans : transs) {
                    Assert.assertNotNull(trans.getLanguage());
                    if ("zz".equals(trans.getLanguage())) {
                        Assert.assertEquals("", trans.getValue());
                    } else {
                        Assert.assertNotNull(trans.getValue());
                    }
                }
            }
        }
    }

}
