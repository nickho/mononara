package com.miragedev.mononara.core.business;

import com.miragedev.mononara.core.model.Knowledge;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 6, 2008
 * Time: 2:03:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class CardLearningMethod implements LearningMethod {

    public long forgetTimeOffset;

    public long getForgetTimeOffset() {
        return forgetTimeOffset;
    }

    public void setForgetTimeOffset(long forgetTimeOffset) {
        this.forgetTimeOffset = forgetTimeOffset;
    }

    /**
     * Return 0 => n
     * i => i+1 = maximum contigus time when the test was successful
     *
     * @param knowledge
     * @return
     */
    public float computeFadingLvl(Knowledge knowledge) {
        if (knowledge.getLastTimeSuccess() != null && knowledge.getFirstTimeSuccess() != null) {
            long a = System.currentTimeMillis();
            long b = knowledge.getLastTimeSuccess().getTimeInMillis();
            long c = knowledge.getLastTimeSuccess().getTimeInMillis();
            long d = knowledge.getFirstTimeSuccess().getTimeInMillis();
            double num = (a - b);
            double denom = (c - d + forgetTimeOffset);
            double e = num / denom;
            return (float) e;
        } else {
            return 0;
        }
    }

    /**
     * Always update the LastTimeSuccess to Now
     * If it's a success :
     * - If FirstTimeSuccess is NULL set it to now
     *
     * @param knowledge
     * @param maxResultCorrect
     * @param resultCorrect
     * @return
     */
    public Knowledge updateLearningResult(Knowledge knowledge, int maxResultCorrect, int resultCorrect) {
        knowledge.setLastTestSuccess(resultCorrect * 1f / maxResultCorrect);
        //if (maxResultCorrect != resultCorrect) {
        //     if (knowledge.getFirstTimeSuccess() == null) {
        //         knowledge.setFirstTimeSuccess(Calendar.getInstance());
        //         knowledge.getFirstTimeSuccess().setTimeInMillis(System.currentTimeMillis());
        //     }
        //     knowledge.setLastTimeSuccess(null);
        // } else {
        if (knowledge.getLastTimeSuccess() == null) {
            knowledge.setLastTimeSuccess(Calendar.getInstance());
        }
        knowledge.getLastTimeSuccess().setTimeInMillis(System.currentTimeMillis());

        if (maxResultCorrect == resultCorrect) {
            if (knowledge.getFirstTimeSuccess() == null) {
                knowledge.setFirstTimeSuccess(Calendar.getInstance());
                knowledge.getFirstTimeSuccess().setTimeInMillis(System.currentTimeMillis());
            }
        }
        return knowledge;
    }

    public boolean isTested(Knowledge knowledge) {
        return knowledge.getFirstTimeSuccess() != null || knowledge.getLastTimeSuccess() != null;
    }
}
