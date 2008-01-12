package com.miragedev.mononara.gui;

import com.miragedev.mononara.core.service.KanjiService;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 12, 2008
 * Time: 12:04:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class TagsListModel extends DefaultComboBoxModel {

    private KanjiService kanjiService;

    public TagsListModel(KanjiService kanjiService) {
        this.kanjiService = kanjiService;
    }

    public int getSize() {
        return kanjiService.findAllTags().size();
    }

    public String getElementAt(int index) {
        return kanjiService.findAllTags().get(index).getCode();
    }
}
