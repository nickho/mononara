package com.miragedev.mononara.core.service;

import com.miragedev.mononara.core.business.Basket;
import com.miragedev.mononara.core.business.Exam;
import com.miragedev.mononara.core.business.ExamResults;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 4, 2008
 * Time: 10:57:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MononaraService {
    void saveExamResults(ExamResults results);

    boolean onlineSync();

    Exam startExam(Basket basket);
}
