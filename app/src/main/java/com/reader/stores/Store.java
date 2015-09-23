package com.reader.stores;

import com.reader.actions.Action;
import com.reader.dispatcher.Dispatcher;

/**
 * Created by Administrator on 2015/9/22.
 */
public abstract class Store {
    final Dispatcher dispatcher;

    protected Store(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    void emitStoreChange() {
        dispatcher.emitChange(changeEvent());
    }

    void emitStoreChange(StoreChangeEvent storeChangeEvent) {
        dispatcher.emitChange(storeChangeEvent);
    }

    abstract StoreChangeEvent changeEvent();

    public abstract void onAction(Action action);

    public interface StoreChangeEvent {
    }
}
