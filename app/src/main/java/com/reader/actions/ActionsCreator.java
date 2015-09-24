package com.reader.actions;

import com.reader.dispatcher.Dispatcher;

/**
 * Created by Administrator on 2015/9/22.
 */
public abstract class ActionsCreator {
   // private static ActionsCreator instance;
    protected final Dispatcher dispatcher;

    ActionsCreator() {
        this.dispatcher = Dispatcher.getInstance();
    }

//    public static ActionsCreator get(Dispatcher dispatcher) {
//        if (instance == null) {
//            instance = new ActionsCreator(dispatcher);
//        }
//        return instance;
//    }


    //public  abstract   ActionsCreator getInstance();

    //销毁actionCreators
    public abstract void destory();
//    public void create(String text) {
//        dispatcher.dispatch(
//                TodoConstants.TODO_CREATE,
//                TodoConstants.KEY_TEXT, text
//        );
//
//    }

}
