package com.miragedev.mononara.core.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Dec 30, 2007
 * Time: 10:17:58 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class DictionnaryEntry {

    @Id
    private long id = -1;

    private String spellingInKanji;

    private String spellingInKana;

    private String spellingInRomaji;

    private String description;

    @ManyToMany
    private List<Tag> tags;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSpellingInKanji() {
        return spellingInKanji;
    }

    public void setSpellingInKanji(String spellingInKanji) {
        this.spellingInKanji = spellingInKanji;
    }

    public String getSpellingInKana() {
        return spellingInKana;
    }

    public void setSpellingInKana(String spellingInKana) {
        this.spellingInKana = spellingInKana;
    }

    public String getSpellingInRomaji() {
        return spellingInRomaji;
    }

    public void setSpellingInRomaji(String spellingInRomaji) {
        this.spellingInRomaji = spellingInRomaji;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Tag getTag(int i) {
        return tags.get(i);
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        if (tags == null) {
            tags = new Vector<Tag>();
        }
        tags.add(tag);
    }

    public int indexOf(String knowledge, int start) {
        return spellingInKanji.indexOf(knowledge, start);
    }

    public int size() {
        return spellingInKanji.length();
    }

    public boolean isTheSame(int index, String spelling) {
        boolean res;
        String[] spellingSplited = spellingInRomaji.split("\\.");
        res = spellingInRomaji.split("\\.")[index].equalsIgnoreCase(spelling);
        res = res || spellingInKana.split("\\.")[index].equalsIgnoreCase(spelling);
        return res;
    }
}
