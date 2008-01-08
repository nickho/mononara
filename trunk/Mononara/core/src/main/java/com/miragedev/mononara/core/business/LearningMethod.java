package com.miragedev.mononara.core.business;

import com.miragedev.mononara.core.model.Knowledge;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 6, 2008
 * Time: 1:59:35 PM
 * To change this template use File | Settings | File Templates.
 */
public interface LearningMethod {
    public float computeFadingLvl(Knowledge knowledge);

    public Knowledge updateLearningResult(Knowledge knowledge, int maxResultCorrect, int resultCorrect);

    public boolean isTested(Knowledge knowledge);
}
