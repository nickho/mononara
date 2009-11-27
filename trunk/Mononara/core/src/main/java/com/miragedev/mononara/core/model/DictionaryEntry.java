package com.miragedev.mononara.core.model;

import javax.persistence.*;
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
public class DictionaryEntry {

    @Id
    @GeneratedValue
    private long id;

    private String spellingInKanji;

    private String spellingInKana;

    private String spellingInRomaji;

    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DictionaryEntry that = (DictionaryEntry) o;

        if (id != that.id) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (spellingInKana != null ? !spellingInKana.equals(that.spellingInKana) : that.spellingInKana != null)
            return false;
        if (!spellingInKanji.equals(that.spellingInKanji)) return false;
        if (spellingInRomaji != null ? !spellingInRomaji.equals(that.spellingInRomaji) : that.spellingInRomaji != null)
            return false;
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + spellingInKanji.hashCode();
        result = 31 * result + (spellingInKana != null ? spellingInKana.hashCode() : 0);
        result = 31 * result + (spellingInRomaji != null ? spellingInRomaji.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }
}
