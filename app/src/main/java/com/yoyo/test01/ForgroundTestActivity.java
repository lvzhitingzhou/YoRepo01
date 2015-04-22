package com.yoyo.test01;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yoyo.yotest01.MainActivity;
import com.yoyo.yotest01.R;

public class ForgroundTestActivity extends ActionBarActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("==============> oncreate");
        setContentView(R.layout.activity_forground_test);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("ToolBar");
        setSupportActionBar(toolbar);
    }

    public void jumpToMainAtivity(View v){
        Intent intent = new Intent(this, com.yoyo.yotest01.MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forground_test, menu);
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

    @Override
    protected void onStart() {
        System.out.println("=================> onstart !!");
        super.onStart();
    }

    @Override
    protected void onResume() {
        System.out.println("=================> onresume !");
        super.onResume();
    }


    @Override
    protected void onPause() {
        System.out.println("==================> onPause !");
        super.onPause();
    }

    @Override
    protected void onStop() {
        System.out.println("=================> onstop !!");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        System.out.println("=================> ondestroy !!!");
        super.onDestroy();
    }
}
