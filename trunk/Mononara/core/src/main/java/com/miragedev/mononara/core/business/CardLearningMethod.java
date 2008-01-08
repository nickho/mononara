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

    public float forgetTimeOffset;

    public float getForgetTimeOffset() {
        return forgetTimeOffset;
    }

    public void setForgetTimeOffset(float forgetTimeOffset) {
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
            return (System.currentTimeMillis() - knowledge.getLastTimeSuccess().getTimeInMillis()) / (knowledge.getLastTimeSuccess().getTimeInMillis() - knowledge.getFirstTimeSuccess().getTimeInMillis() + forgetTimeOffset);
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
}
