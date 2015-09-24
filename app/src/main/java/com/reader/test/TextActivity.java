package com.reader.test;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.apkfuns.logutils.LogUtils;
import com.reader.R;
import com.reader.actions.TranslateActionCreators;
import com.reader.components.SuperActivity;
import com.reader.stores.TodoStore;
import com.reader.widget.selectable.SelectableTextView;
import com.squareup.otto.Subscribe;

/**
 * Created by DongZ on 2015/9/23 0023.
 */
public class TextActivity extends SuperActivity{

    private TranslateActionCreators actionsCreator;


    private SelectableTextView mTextView;
    private int mTouchX;
    private int mTouchY;
    private final static int DEFAULT_SELECTION_LEN = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (SelectableTextView) findViewById(R.id.main_text);
        // mTextView.setDefaultSelectionColor(0x40FF00FF);
        mTextView.setSelected(false);

        mTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mTextView.hideCursor();
                // showSelectionCursors(mTouchX, mTouchY);
                Toast.makeText(TextActivity.this, mTextView.getSelectWord(mTextView.getText().toString(), mTextView.extractWordCurOff(mTouchX, mTouchY)), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.hideCursor();
            }
        });
        mTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTouchX = (int) event.getX();
                mTouchY = (int) event.getY();
                return false;
            }
        });

        mTextView.setmCursorSelection(new SelectableTextView.mCursonSelectionTextListener() {
            @Override
            public void selectText(String subText) {
                Log.d("text", subText);
                Toast.makeText(TextActivity.this,subText,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSelectionCursors(int x, int y) {
        int start = mTextView.getPreciseOffset(x, y);

        if (start > -1) {
            int end = start + DEFAULT_SELECTION_LEN;
            if (end >= mTextView.getText().length()) {
                end = mTextView.getText().length() - 1;
            }
            mTextView.showSelectionControls(start, end);
        }
    }

    @Override
    public void initDependencies() {
        super.initDependencies();
        store = TodoStore.get(dispatcher);
        actionsCreator = TranslateActionCreators.getInstance();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //actionsCreator.create("hahahahah");
        actionsCreator.TransLate("yellow");
        //LogUtils.e("creattor");
    }

    @Subscribe
    public void onTodoStoreChange(TodoStore.TodoStoreChangeEvent event) {
        LogUtils.e("wa哈哈哈哈哈");
   }
}
