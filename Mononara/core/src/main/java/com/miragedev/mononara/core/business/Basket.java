/*****************************************
 *
 *   MononaraService
 *
 *   Distributable under LGPL license.   
 *   See terms of license at gnu.org.    
 *
 *****************************************/
package com.miragedev.mononara.core.business;

import com.miragedev.mononara.core.model.Knowledge;

import java.util.ArrayList;
import java.util.List;

/**
 * A Basket contain the knowledge the user select for the test.
 *
 * @author <a href="mailto:nicolas@radde.org">Nicolas Radde</a>
 * @version $Revision: 1.0 $
 */
public class Basket {

    private List<Knowledge> content;
    private List<ContentChangeListener> listeners;
    private int maxSize;

    public Basket(int size) {
        content = new ArrayList<Knowledge>();
        listeners = new ArrayList<ContentChangeListener>();
        maxSize = size;
    }

    /**
     * Add a knowledge in the basket.
     * If this one is full <code>false</code> is returned
     *
     * @param knowledge the knowledge to add
     * @return false if the Basket is full
     */
    public boolean add(Knowledge knowledge) {
        if (content.size() < maxSize) {
            content.add(knowledge);
            for (ContentChangeListener listener : listeners) {
                listener.contentChange(new ContentChangeEvent(knowledge, ContentChangeEvent.Type.ADD));
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add all the knowledge which need to be checked to the basket.
     *
     * @param tag
     * @todo implement autoAddToBasket()
     */
    public void autoAddToBasket(String tag) {

    }

    /**
     * Remove a knowledge from the Basket.
     * return false if the remove failed (like knowledge not in the basket)
     *
     * @param knowledge
     * @return
     */
    public boolean remove(Knowledge knowledge) {
        boolean res = content.remove(knowledge);
        if (res) {
            for (ContentChangeListener listener : listeners) {
                listener.contentChange(new ContentChangeEvent(knowledge, ContentChangeEvent.Type.REMOVE));
            }
        }
        return res;
    }

    /**
     * Return the number of knowledge in the basket.
     *
     * @return
     */
    public int size() {
        return content.size();
    }

    /**
     * Simple acces method to the knowledge.
     *
     * @param index
     * @return
     */
    public Knowledge getKnowledge(int index) {
        return content.get(index);
    }

    /**
     * Empty the basket.
     */
    public void empty() {
        content.clear();
    }


    /**
     * Return the maximum item the Basket can contain.
     *
     * @return
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Change the maximum items the Basket can contain.
     *
     * @param maxSize
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }


    /**
     * Add a new ContentChangeListener to the Basket.
     *
     * @param listener
     */
    public void addContentChangeListener(ContentChangeListener listener) {
        listeners.add(listener);
    }
}
