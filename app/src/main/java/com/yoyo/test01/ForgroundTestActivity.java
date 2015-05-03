package com.yoyo.test01;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoyo.view.SlidingTabLayout;
import com.yoyo.yotest01.R;

public class ForgroundTestActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private SlidingTabLayout slideTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.foreground_acitivity_layout_cover_toolbar);
        setContentView(R.layout.foreground_slding_tab_layout);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("ForgroundTestActivity");
        setSupportActionBar(toolbar);


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // 必须写这句才能出来抽屉

        NavigationDrawerFragment navigationDrawerFragment =
                (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationDrawerFragment.setUp(R.id.drawer_fragment, drawerLayout, toolbar);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        slideTabLayout = (SlidingTabLayout) findViewById(R.id.slidng_tab_layout);
        slideTabLayout.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
        slideTabLayout.setDistributeEvenly(true);  // tabs平分屏幕宽度
        slideTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.accentColor);
            }
        });

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        slideTabLayout.setViewPager(viewPager);

    }

    public void jumpToMainAtivity(View v){
        Intent intent = new Intent(this, SecondActivity.class);
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
            startActivity(new Intent(this, SecondActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * viewpager adapter
     */
    class MyPagerAdapter extends FragmentPagerAdapter{

        String[] tabTexts;
        int[] icons = {R.drawable.ic_action_home, R.drawable.ic_action_personal, R.drawable.ic_action_articles};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabTexts = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int i) {
            TabFragment f = TabFragment.getInstance(i);
            return f;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable =getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,36,36);
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            return spannableString;
        }
    }

    public static class TabFragment extends Fragment{

        public static TabFragment getInstance(int position){
            TabFragment fragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.tab_fragment_layout, container, false);
            TextView tv = (TextView) layout.findViewById(R.id.tv);
            if(getArguments() != null){
                int position = getArguments().getInt("position", -1);
                tv.setText("this is fragment " + position);
            }
            return layout;
        }
    }

}
