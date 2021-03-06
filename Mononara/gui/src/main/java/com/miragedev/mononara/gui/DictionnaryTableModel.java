package com.miragedev.mononara.gui;

import com.miragedev.mononara.core.model.DictionaryEntry;
import com.miragedev.mononara.core.model.Tag;
import com.miragedev.mononara.core.service.DictionaryService;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 11, 2008
 * Time: 7:59:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class DictionnaryTableModel extends AbstractTableModel {

    private String[] columnNames = {"Kanji", "Kana", "Romaji", "Description", "tags"};

    private DictionaryService dictionaryService;

    public DictionnaryTableModel(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return dictionaryService.getDictionnarySize();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        DictionaryEntry entry = dictionaryService.getEntryByNumber(row);
        if (entry != null) {
            switch (col) {
                case 0:
                    return entry.getSpellingInKanji();
                case 1:
                    return entry.getSpellingInKana().replace(".", "");
                case 2:
                    return entry.getSpellingInRomaji().replace(".", "");
                case 3:
                    return entry.getDescription();
                case 4:
                    return getTagsAsString(entry.getTags());
                default:
                    return "POUET!!!";
            }
        } else {
            return "NULL";
        }
    }

    private String getTagsAsString(List<Tag> tags) {
        StringBuffer res = new StringBuffer();
        for (Tag tag : tags) {
            if (res.length() != 0) {
                res.append(", ");
            }
            res.append(tag.getCode());
        }
        return res.toString();
    }

}
