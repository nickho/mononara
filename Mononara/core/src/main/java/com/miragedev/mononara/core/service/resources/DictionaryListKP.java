//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.11.22 at 02:38:49 PM CET 
//


package com.miragedev.mononara.core.service.resources;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for dictionaryList complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="dictionaryList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="entry" type="{}dictionary" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="count" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="total" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "dictionary")
public class DictionaryListKP {

    @XmlElement(name = "entry")
    private List<DictionaryKP> entries;

    @XmlAttribute(required = true)
    private int count;

    @XmlAttribute(required = true)
    private long total;

    /**
     * Gets the value of the entry property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the entry property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEntry().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link DictionaryKP }
     */
    public List<DictionaryKP> getEntries() {
        if (entries == null) {
            entries = new ArrayList<DictionaryKP>();
        }
        return this.entries;
    }

    /**
     * Gets the value of the count property.
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     */
    public void setCount(int value) {
        this.count = value;
    }

    /**
     * Gets the value of the total property.
     */
    public long getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     */
    public void setTotal(long value) {
        this.total = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DictionaryListKP that = (DictionaryListKP) o;

        if (count != that.count) return false;
        if (total != that.total) return false;
        if (entries != null ? !entries.equals(that.entries) : that.entries != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = entries != null ? entries.hashCode() : 0;
        result = 31 * result + count;
        result = 31 * result + (int) (total ^ (total >>> 32));
        return result;
    }
}