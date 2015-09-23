package com.reader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private EditText article;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        article = (EditText) findViewById(R.id.article);
//        LogUtils.configAllowLog = true;
//        article.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                return false;
//            }
//        });
//         TranslateProtocol.bdTransLate("launch",null);
//        img = (ImageView) findViewById(R.id.img);
//        Drawable drawable = img.getDrawable();
//        article.debug(0);
//        LogUtils.e(drawable.getIntrinsicHeight());
//        LogUtils.e(drawable.getIntrinsicWidth());
//        LogUtils.e(img.getHeight());
//        LogUtils.e(img.getWidth());
//
//        TranslateProtocol.voaTransLate("hello", new HttpCallBack() {
//            @Override
//            public void onGeneralSuccess(String result, long flag) {
//
//                LogUtils.e(result);
//            }
//
//            @Override
//            public void onGeneralError(String e, long flag) {
//
//            }
//        });
//
//        TranslateProtocol.youdaoTransLate("name",null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
