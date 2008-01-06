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

    public float computeFadingLvl(Knowledge knowledge) {
     if (knowledge.getLastTest() == null && knowledge.getFirstTimeSuccess() == null) {
            return 0f;
        } else if (knowledge.getFirstTimeSuccess() != null && knowledge.getLastTest() == null) {
            return -1f;
        } else {
            return 1f + (System.currentTimeMillis() - knowledge.getLastTest().getTimeInMillis()) / (knowledge.getLastTest().getTimeInMillis() - knowledge.getFirstTimeSuccess().getTimeInMillis() + forgetTimeOffset);
        }
    }

    public Knowledge updateLearningResult(Knowledge knowledge, int maxResultCorrect , int resultCorrect) {
       if (maxResultCorrect != resultCorrect) {
            if (knowledge.getFirstTimeSuccess() == null) {
                knowledge.setFirstTimeSuccess(Calendar.getInstance());
                knowledge.getFirstTimeSuccess().setTimeInMillis(System.currentTimeMillis());
            }
            knowledge.setLastTest(null);
        } else {
            if (knowledge.getLastTest() == null) {
                knowledge.setLastTest(Calendar.getInstance());
            }
            knowledge.getLastTest().setTimeInMillis(System.currentTimeMillis());
            if (knowledge.getFirstTimeSuccess() == null) {
                knowledge.setFirstTimeSuccess(Calendar.getInstance());
                knowledge.getFirstTimeSuccess().setTimeInMillis(System.currentTimeMillis());
            }
        }
        return knowledge;
    }
}
