package com.miragedev.mononara.core.business;

import com.miragedev.mononara.core.model.Knowledge;

/**
 * Created by IntelliJ IDEA.
 * User: Nickho
 * Date: Jan 3, 2008
 * Time: 6:36:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContentChangeEvent {

    public static enum Type {
        ADD, REMOVE
    }

    Knowledge knowledge;
    Type type;

    public ContentChangeEvent(Knowledge knowledge, Type type) {
        this.knowledge = knowledge;
        this.type = type;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public Type getType() {
        return type;
    }
}
