package com.miragedev.mononara.core.business;

import com.miragedev.mononara.core.model.Knowledge;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Dec 29, 2007
 * Time: 6:38:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class CardLearningMethodTest  extends TestCase {

    CardLearningMethod cardLearningMethod;

    public void setUp() {
        cardLearningMethod = new CardLearningMethod();
        cardLearningMethod.setForgetTimeOffset(100000f);
    }

    /**
     * Simple test for the fading  of Knowledge
     */

    public void testImmediatFading() {
        Knowledge knowledge = new Knowledge();
        Assert.assertTrue(Math.abs(0.0 - cardLearningMethod.computeFadingLvl(knowledge)) < 0.001);
        knowledge = cardLearningMethod.updateLearningResult(knowledge, 5, 5);
        Assert.assertTrue(Math.abs(1.0 - cardLearningMethod.computeFadingLvl(knowledge)) < 0.001);
    }
}
