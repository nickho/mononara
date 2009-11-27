package com.miragedev.mononara.core.model;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Vector;


/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Dec 30, 2007
 * Time: 10:03:21 AM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@Entity
public class Kanji {

    @Id
    @GeneratedValue
    private long id;

    @XmlElement(name = "kanji")
    private String character;

    private String description;

    private String meaning;

    @Temporal(TemporalType.DATE)
    private Date update;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> tags;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }

    @XmlElementRef
    @XmlElementWrapper(name = "tags")
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        if (tags == null) {
            tags = new Vector<Tag>();
        }
        this.tags.add(tag);
    }

    public boolean removeTag(Tag tag) {
        if (tags == null) {
            return false;
        }
        return this.tags.remove(tag);
    }

    @Override
    public String toString() {
        return String.format("Kanji(%s, %s, %s)", id, character, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kanji kanji = (Kanji) o;

        if (id != kanji.id) return false;
        if (!character.equals(kanji.character)) return false;
        if (description != null ? !description.equals(kanji.description) : kanji.description != null) return false;
        if (meaning != null ? !meaning.equals(kanji.meaning) : kanji.meaning != null) return false;
        if (tags != null ? !tags.equals(kanji.tags) : kanji.tags != null) return false;
        if (update != null ? !update.equals(kanji.update) : kanji.update != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + character.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (meaning != null ? meaning.hashCode() : 0);
        result = 31 * result + (update != null ? update.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }
}
