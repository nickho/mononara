package com.miragedev.mononara.core.business;

import com.miragedev.mononara.core.model.Knowledge;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Dec 29, 2007
 * Time: 6:38:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class CardLearningMethodTest extends TestCase {

    CardLearningMethod cardLearningMethod;

    public void setUp() {
        cardLearningMethod = new CardLearningMethod();
        cardLearningMethod.setForgetTimeOffset(3500000);
    }

    public void testFading() {
        Knowledge knowledge = new Knowledge();
        cardLearningMethod.setForgetTimeOffset(9000);

        Calendar timeOfSuccess = Calendar.getInstance();
        timeOfSuccess.setTimeInMillis(System.currentTimeMillis() - 10000);
        knowledge.setLastTestSuccess(1.0f);
        knowledge.setLastTimeSuccess(timeOfSuccess);
        knowledge.setFirstTimeSuccess(timeOfSuccess);
        Assert.assertTrue(cardLearningMethod.computeFadingLvl(knowledge) > 1);

        timeOfSuccess = Calendar.getInstance();
        timeOfSuccess.setTimeInMillis(System.currentTimeMillis() - 20000);
        knowledge.setLastTestSuccess(1.0f);
        knowledge.setLastTimeSuccess(timeOfSuccess);
        knowledge.setFirstTimeSuccess(timeOfSuccess);
        Assert.assertTrue(cardLearningMethod.computeFadingLvl(knowledge) > 2);

        timeOfSuccess = Calendar.getInstance();
        timeOfSuccess.setTimeInMillis(System.currentTimeMillis() - 30000);
        knowledge.setLastTestSuccess(1.0f);
        knowledge.setLastTimeSuccess(timeOfSuccess);
        knowledge.setFirstTimeSuccess(timeOfSuccess);
        Assert.assertTrue(cardLearningMethod.computeFadingLvl(knowledge) > 3);
    }

    public void testFadingAfterBadResult() {
        Knowledge knowledge = new Knowledge();
        Assert.assertTrue(Math.abs(cardLearningMethod.computeFadingLvl(knowledge)) < 0.001);
        knowledge = cardLearningMethod.updateLearningResult(knowledge, 10, 0);
        Assert.assertTrue(Math.abs(cardLearningMethod.computeFadingLvl(knowledge)) < 0.001);
    }

    public void testFadingAfterGoodResult1HResult() {
        Knowledge knowledge = new Knowledge();
        Calendar nowMinus1Hour = Calendar.getInstance();
        nowMinus1Hour.setTimeInMillis(System.currentTimeMillis() - 3600000);
        knowledge.setFirstTimeSuccess(nowMinus1Hour);
        knowledge.setLastTimeSuccess(nowMinus1Hour);
        Assert.assertTrue(Math.abs(cardLearningMethod.computeFadingLvl(knowledge)) > 1);
    }

    public void testUpdateLearningResultFirstExamSuccess() {
        Knowledge knowledge = new Knowledge();
        knowledge = cardLearningMethod.updateLearningResult(knowledge, 5, 5);
        Assert.assertTrue(knowledge.getLastTestSuccess() > 0.999);
    }

    public void testUpdateLearningResultFirstExamMidResult() {
        Knowledge knowledge = new Knowledge();
        knowledge = cardLearningMethod.updateLearningResult(knowledge, 10, 5);
        Assert.assertTrue(knowledge.getLastTestSuccess() > 0.49);
        Assert.assertTrue(knowledge.getLastTestSuccess() < 0.61);
    }

    public void testUpdateLearningResultFirstExamBadResult() {
        Knowledge knowledge = new Knowledge();
        knowledge = cardLearningMethod.updateLearningResult(knowledge, 10, 0);
        Assert.assertTrue(Math.abs(knowledge.getLastTestSuccess()) < 0.001);
    }

    public void testUpdateLearningResultLastExamAfterGoodResultSuccess() {
        Knowledge knowledge = new Knowledge();
        Calendar nowMinus1Hour = Calendar.getInstance();
        nowMinus1Hour.setTimeInMillis(System.currentTimeMillis() - 3600000);
        knowledge.setFirstTimeSuccess(nowMinus1Hour);
        knowledge.setLastTimeSuccess(nowMinus1Hour);
        knowledge = cardLearningMethod.updateLearningResult(knowledge, 10, 10);
        Assert.assertTrue(Math.abs(1 - knowledge.getLastTestSuccess()) < 0.001);
        Assert.assertTrue(Math.abs(nowMinus1Hour.getTimeInMillis() - knowledge.getFirstTimeSuccess().getTimeInMillis()) < 0.001);
        Assert.assertTrue(Math.abs(System.currentTimeMillis() - knowledge.getLastTimeSuccess().getTimeInMillis()) < 0.001);
    }

    public void testUpdateLearningResultLastExamAfterGoodResultFailure() {
        Knowledge knowledge = new Knowledge();
        Calendar nowMinus1Hour = Calendar.getInstance();
        nowMinus1Hour.setTimeInMillis(System.currentTimeMillis() - 3600000);
        knowledge.setFirstTimeSuccess(nowMinus1Hour);
        knowledge.setLastTimeSuccess(nowMinus1Hour);
        knowledge = cardLearningMethod.updateLearningResult(knowledge, 10, 0);
        Assert.assertTrue(Math.abs(knowledge.getLastTestSuccess()) < 0.001);
        Assert.assertTrue(Math.abs(nowMinus1Hour.getTimeInMillis() - knowledge.getLastTimeSuccess().getTimeInMillis()) < 0.001);
        Assert.assertTrue(Math.abs(System.currentTimeMillis() - knowledge.getLastTimeSuccess().getTimeInMillis()) < 0.001);
    }

    public void testUpdateLearningResultLastExamAfterBadResultSuccess() {
        Knowledge knowledge = new Knowledge();
        Calendar nowMinus1Hour = Calendar.getInstance();
        nowMinus1Hour.setTimeInMillis(System.currentTimeMillis() - 3600000);
        knowledge.setLastTimeSuccess(nowMinus1Hour);
        knowledge = cardLearningMethod.updateLearningResult(knowledge, 10, 10);
        Assert.assertTrue(Math.abs(1 - knowledge.getLastTestSuccess()) < 0.001);
        Assert.assertTrue(Math.abs(nowMinus1Hour.getTimeInMillis() - knowledge.getFirstTimeSuccess().getTimeInMillis()) < 0.001);
        Assert.assertTrue(Math.abs(System.currentTimeMillis() - knowledge.getLastTimeSuccess().getTimeInMillis()) < 0.001);
    }

    public void testUpdateLearningResultLastExamAfterBadResultFailure() {
        Knowledge knowledge = new Knowledge();
        Calendar nowMinus1Hour = Calendar.getInstance();
        nowMinus1Hour.setTimeInMillis(System.currentTimeMillis() - 3600000);
        knowledge.setLastTimeSuccess(nowMinus1Hour);
        knowledge = cardLearningMethod.updateLearningResult(knowledge, 10, 0);
        Assert.assertTrue(Math.abs(knowledge.getLastTestSuccess()) < 0.001);
        Assert.assertNull(knowledge.getFirstTimeSuccess());
        Assert.assertTrue(Math.abs(System.currentTimeMillis() - knowledge.getLastTimeSuccess().getTimeInMillis()) < 0.001);
    }

}
