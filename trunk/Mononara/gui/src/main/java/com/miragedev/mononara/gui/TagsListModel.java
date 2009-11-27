package com.miragedev.mononara.gui;

import com.miragedev.mononara.core.service.TagService;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 12, 2008
 * Time: 12:04:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class TagsListModel extends DefaultComboBoxModel {

    private TagService tagService;

    public TagsListModel(TagService tagService) {
        this.tagService = tagService;
    }

    public int getSize() {
        return tagService.findAllTags().size();
    }

    public String getElementAt(int index) {
        return tagService.findAllTags().get(index).getCode();
    }
}
