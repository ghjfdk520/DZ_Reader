package com.reader.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.apkfuns.logutils.LogUtils;
import com.reader.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/9/5.
 */
public class articleView extends EditText {
    private String TAG =  this.getClass().getName();

    private PopupWindow popup;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 100;
    private static final long DELAY_TIME = 250;
    private Context context;
    private Magnifier magnifier;
    private Bitmap bitmap;//生成的截图

    private Bitmap resBitmap;
    private Point dstPoint;
    // 移动的阈值
    private static final int TOUCH_SLOP = 20;
    private final int DOUBLE_TAP_TIMEOUT = 350;
    private MotionEvent mCurrentDownEvent;
    private MotionEvent mPreviousUpEvent;

    private int mLastMotionX,mLastMotionY;

    private boolean isLongPressState;
    private final int LONGPRESS = 1;

    private int off;
    private int curOff;

    private boolean isMoved;

    public articleView(Context context) {
        this(context, null);
    }

    public articleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context =context;
        initMagnifier();
    }


    private Handler mPressHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LONGPRESS:
                    isLongPressState = true;
                    Bundle bundle = msg.getData();
                    int X = bundle.getInt("X");
                    int RawX = bundle.getInt("RawX");
                    int Y = bundle.getInt("Y");
                    int RawY = bundle.getInt("RawY");
                    calculate(RawX, RawY, MotionEvent.ACTION_DOWN);
                    break;
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        int action = event.getAction();
        Layout layout = getLayout();
        int line = 0;
        line = layout.getLineForVertical(getScrollY()+ (int)event.getY());
        switch(action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                mLastMotionY = y;
                isMoved = false;
                Message message = mPressHandler != null ? mPressHandler.obtainMessage()
                        : new Message();
                //传对象,过去后,getRawY,不是相对的Y轴.
//                message.obj = event;
                Bundle bundle = new Bundle();
                bundle.putInt("X", (int) event.getX());
                bundle.putInt("RawX", (int) event.getRawX());
                bundle.putInt("Y", (int) event.getY());
                bundle.putInt("RawY", (int) event.getRawY());
                message.setData(bundle);
                message.what = LONGPRESS;
                resBitmap = getBitmap((Activity) context, (int) event.getRawX() - WIDTH / 2, (int) event.getRawY() - HEIGHT / 2, WIDTH, HEIGHT);
                mPressHandler.sendMessageDelayed(message, 500);


                mCurrentDownEvent = MotionEvent.obtain(event);

                off = layout.getOffsetForHorizontal(line, (int)event.getX());
                Selection.setSelection(getEditableText(), off);

                break;
            case MotionEvent.ACTION_MOVE:
                if (isLongPressState)
                    if (Math.abs(mLastMotionX - x) > TOUCH_SLOP
                            || Math.abs(mLastMotionY - y) > TOUCH_SLOP) {
                         //放大镜
                        resBitmap = getBitmap((Activity) context, (int) event.getRawX() - WIDTH / 2, (int) event.getRawY() - HEIGHT / 2, WIDTH, HEIGHT);
                        calculate((int) event.getRawX(), (int) event.getRawY(), MotionEvent.ACTION_MOVE);
                    }
                if (isMoved && !isLongPressState)
                    break;
                //如果移动超过阈值
                if (Math.abs(mLastMotionX - x) > TOUCH_SLOP
                        || Math.abs(mLastMotionY - y) > TOUCH_SLOP)
                    //并且非长按状态下
                    if (!isLongPressState) {
                        // 则表示移动了
                        isMoved = true;
                        cleanLongPress();// 如果超出规定的移动范围--取消[长按事件]

                    }
                curOff = layout.getOffsetForHorizontal(line, (int) event.getX());
                isMoved = true;
                Selection.setSelection(getEditableText(), off, curOff);

                break;
            case MotionEvent.ACTION_UP:
                isMoved = false;
                curOff = layout.getOffsetForHorizontal(line, (int) event.getX());
                if (mPreviousUpEvent != null
                        && mCurrentDownEvent != null
                        && isConsideredDoubleTap(mCurrentDownEvent,
                        mPreviousUpEvent, event)) {
                    getSelectWord(getEditableText(),curOff);
                    LogUtils.e("Double click=============");
                }

                mPreviousUpEvent = MotionEvent.obtain(event);

                if (isLongPressState) {
                    //dis掉放大镜
                    removeCallbacks(showZoom);
                    //drawLayout();
                    popup.dismiss();

                    //TODO --单词pop
                    cleanLongPress();
                    break;
                }
                cleanLongPress();// 只要一抬起就释放[长按事件]
                break;
            case MotionEvent.ACTION_CANCEL:
                // 事件一取消也释放[长按事件],解决在ListView中滑动的时候长按事件的激活
                cleanLongPress();
                break;
        }

        LogUtils.e(getSelectionStart());
        return true;
    }


    public String getSelectWord(Editable content, int curOff) {
        String word = "";
        int start = getWordLeftIndex(content, curOff);
        int end = getWordRightIndex(content, curOff);
        if (start >= 0 && end >= 0) {
            word = content.subSequence(start, end).toString();
            if (!"".equals(word)) {
                // setFocusable(false);
                Selection.setSelection(content, start, end);// 设置当前具有焦点的文本字段的选择范围,当前文本必须具有焦点，否则此方法无效
            }
        }
        return word;
    }

    public int getWordLeftIndex(Editable content, int cur) {
        // --left
        String editableText = content.toString();// getText().toString();
        if (cur >= editableText.length())
            return cur;

        int temp = 0;
        if (cur >= 20)
            temp = cur - 20;
        Pattern pattern = Pattern.compile("[^'A-Za-z]");
        Matcher m = pattern.matcher(editableText.charAt(cur) + "");
        if (m.find())
            return cur;

        String text = editableText.subSequence(temp, cur).toString();
        int i = text.length() - 1;
        for (; i >= 0; i--) {
            Matcher mm = pattern.matcher(text.charAt(i) + "");
            if (mm.find())
                break;
        }
        int start = i + 1;
        start = cur - (text.length() - start);
        return start;
    }

    public int getWordRightIndex(Editable content, int cur) {
        // --right
        String editableText = content.toString();
        if (cur >= editableText.length())
            return cur;

        int templ = editableText.length();
        if (cur <= templ - 20)
            templ = cur + 20;
        Pattern pattern = Pattern.compile("[^'A-Za-z]");
        Matcher m = pattern.matcher(editableText.charAt(cur) + "");
        if (m.find())
            return cur;

        String text1 = editableText.subSequence(cur, templ).toString();
        int i = 0;
        for (; i < text1.length(); i++) {
            Matcher mm = pattern.matcher(text1.charAt(i) + "");
            if (mm.find())
                break;
        }
        int end = i;
        end = cur + end;
        return end;
    }


    //检查是否双击
    private boolean isConsideredDoubleTap(MotionEvent firstDown,
                                          MotionEvent firstUp, MotionEvent secondDown) {
        if (secondDown.getEventTime() - firstUp.getEventTime() > DOUBLE_TAP_TIMEOUT) {
            return false;
        }
        int deltaX = (int) firstUp.getX() - (int) secondDown.getX();
        int deltaY = (int) firstUp.getY() - (int) secondDown.getY();
        return deltaX * deltaX + deltaY * deltaY < 500;
    }

    //==================================================放大镜
    private void cleanLongPress() {
        isLongPressState = false;
        mPressHandler.removeMessages(LONGPRESS);
    }
    private void initMagnifier() {
        BitmapDrawable resDrawable = (BitmapDrawable) context.getResources().getDrawable(R.mipmap.ic_launcher);
        resBitmap = resDrawable.getBitmap();

        magnifier = new Magnifier(context);

        //pop在宽高的基础上多加出边框的宽高
        popup = new PopupWindow(magnifier, WIDTH + 2, HEIGHT + 10);
        popup.setAnimationStyle(android.R.style.Animation_Toast);

        dstPoint = new Point(0, 0);
    }

    Runnable showZoom = new Runnable() {
        public void run() {
            popup.showAtLocation(articleView.this,
                    Gravity.NO_GRAVITY,
                    getLeft() + dstPoint.x,
                    getTop() + dstPoint.y);
        }
    };

    class Magnifier extends View {
        private Paint mPaint;

        public Magnifier(Context context) {
            super(context);
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(0xff008000);
            mPaint.setStyle(Paint.Style.STROKE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.save();
            // draw popup
            mPaint.setAlpha(255);
            canvas.drawBitmap(resBitmap, 0, 0, mPaint);
            canvas.restore();

            //draw popup frame
            mPaint.reset();//重置
            mPaint.setColor(Color.LTGRAY);
            mPaint.setStyle(Paint.Style.STROKE);//设置空心
            mPaint.setStrokeWidth(2);
            Path path1 = new Path();
            path1.moveTo(0, 0);
            path1.lineTo(WIDTH, 0);
            path1.lineTo(WIDTH, HEIGHT);
            path1.lineTo(WIDTH / 2 + 15, HEIGHT);
            path1.lineTo(WIDTH / 2, HEIGHT + 10);
            path1.lineTo(WIDTH / 2 - 15, HEIGHT);
            path1.lineTo(0, HEIGHT);
            path1.close();//封闭
            canvas.drawPath(path1, mPaint);
        }
    }

    private boolean calculate(int x, int y, int action) {
        dstPoint.set(x - WIDTH / 2, y - 3 * HEIGHT);
        if (y < 0) {
            // hide popup if out of bounds
            popup.dismiss();
            return true;
        }
        if (action == MotionEvent.ACTION_DOWN) {
            removeCallbacks(showZoom);
            postDelayed(showZoom, DELAY_TIME);
        } else if (!popup.isShowing()) {
            showZoom.run();
        }
        popup.update(getLeft() + dstPoint.x, getTop() + dstPoint.y, -1, -1);
        magnifier.invalidate();
        return true;
    }

    private Bitmap getBitmap(Activity activity, int x, int y, int width, int height){
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        bitmap = view.getDrawingCache();
        //边界处理,否则会崩滴
        if (x < 0)
            x = 0;
        if (y < 0)
            y = 0;
        if (x + width > bitmap.getWidth()) {
//            x = x + WIDTH / 2;
//            width = bitmap.getWidth() - x;
            //保持不改变,截取图片宽高的原则
            x = bitmap.getWidth() - width;
        }
        if (y + height > bitmap.getHeight()) {
//            y = y + HEIGHT / 2;
//            height = bitmap.getHeight() - y;
            y = bitmap.getHeight() - height;
        }
//        Rect frame = new Rect();
//        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//        int toHeight = frame.top;
        bitmap = Bitmap.createBitmap(bitmap, x, y, width, height);
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }


    public void showAtLocation(int x, int y){
        WindowManager  mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p =new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        p.x =x;
        p.y = y;
        ImageView imageView = new ImageView(context);
        imageView.setBackgroundResource(R.drawable.text_select_handle_left_material);
        mWindowManager.addView(imageView,p);
    }

}
