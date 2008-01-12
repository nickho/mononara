package com.miragedev.mononara.gui;

import com.miragedev.mononara.core.business.Basket;
import com.miragedev.mononara.core.business.ContentChangeEvent;
import com.miragedev.mononara.core.business.ContentChangeListener;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 12, 2008
 * Time: 12:20:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class BasketListModel extends AbstractListModel {

    private Basket basket;

    public BasketListModel(Basket basket) {
        this.basket = basket;
        basket.addContentChangeListener(new ContentChangeListener() {
            public void contentChange(ContentChangeEvent e) {
                fireContentsChanged(this, 0, 0);
            }
        });
    }

    public int getSize() {
        //System.out.println("basket size =>" + basket.size());
        return basket.size();
    }

    public Object getElementAt(int index) {
        //System.out.println("basket =>" + index);
        return basket.getKnowledge(index).getKanji().getCharacter();
    }


}
