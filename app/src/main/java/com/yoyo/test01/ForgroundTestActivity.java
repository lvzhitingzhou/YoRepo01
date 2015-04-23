package com.yoyo.test01;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yoyo.yotest01.R;

public class ForgroundTestActivity extends ActionBarActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forground_test);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("ForgroundTestActivity");
        setSupportActionBar(toolbar);


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // 必须写这句才能出来抽屉

        NavigationDrawerFragment navigationDrawerFragment =
                (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigate_fragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationDrawerFragment.setUp(drawerLayout, toolbar);


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

        if(id == R.id.action_navigate){
            startActivity(new Intent(this, com.yoyo.yotest01.MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
