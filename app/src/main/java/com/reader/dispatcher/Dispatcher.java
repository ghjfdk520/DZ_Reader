package com.reader.dispatcher;

import com.reader.actions.Action;
import com.reader.stores.Store;
import com.squareup.otto.Bus;

/**
 * Created by Administrator on 2015/9/22.
 */
public class Dispatcher {
    private final Bus bus;
    private static Dispatcher instance;

    public static Dispatcher getInstance() {
        if (instance == null) {
            synchronized (Dispatcher.class) {
                if (instance == null)
                 instance = new Dispatcher();
            }
        }

        return instance;
    }

    private Dispatcher() {
        this.bus = new Bus();
    }

    public void register(final Object cls) {
        bus.register(cls);

    }
    public void emitChange(Store.StoreChangeEvent o) {
        post(o);
    }
    public void unregister(final Object cls) {
        bus.unregister(cls);
    }

    private void post(final Object event) {
        bus.post(event);
    }

    public void dispatch(String type,Object...data){
        if (isEmpty(type)) {
            throw new IllegalArgumentException("Type must not be empty");
        }

        if (data.length % 2 != 0) {
            throw new IllegalArgumentException("Data must be a valid list of key,value pairs");
        }

        Action.Builder actionBuilder = Action.type(type);
        int i = 0;
        while (i < data.length) {
            String key = (String) data[i++];
            Object value = data[i++];
            actionBuilder.bundle(key, value);
        }
        post(actionBuilder.build());
    }

    private boolean isEmpty(String type) {
        return type == null || type.isEmpty();
    }
}
