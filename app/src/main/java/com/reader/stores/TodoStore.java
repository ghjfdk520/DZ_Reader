package com.reader.stores;

import com.apkfuns.logutils.LogUtils;
import com.reader.actions.Action;
import com.reader.conf.TodoConstants;
import com.reader.dispatcher.Dispatcher;
import com.reader.model.Todo;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/22.
 */
public class TodoStore extends Store{
    private static TodoStore instance;
    private final List<Todo> todos;
    private Todo lastDeleted;

    protected TodoStore(Dispatcher dispatcher) {
        super(dispatcher);
        todos = new ArrayList<>();
    }

    public static TodoStore get(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new TodoStore(dispatcher);
        }
        return instance;
    }





    @Override
    @Subscribe
    public void onAction(Action action) {
        long id;

        LogUtils.e("onAction");
        switch (action.getType()){
            case TodoConstants.TODO_CREATE:
                String text = ((String) action.getData().get(TodoConstants.KEY_TEXT));
                create(text);
                emitStoreChange();

                break;
        }
    }



    private void create(String text) {
        long id = System.currentTimeMillis();
        Todo todo = new Todo(id, text);
//        addElement(todo);
//        Collections.sort(todos);
    }

    @Override
    StoreChangeEvent changeEvent() {
        return new TodoStoreChangeEvent();
    }


    public class TodoStoreChangeEvent implements StoreChangeEvent{

    }
}
