package com.yoyo.test01;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yoyo.yotest01.R;

import java.security.Key;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionbarDrawerToggle;
    private boolean mUserLearnedDrawer ; //  用户是否已知有抽屉
    private boolean fromSaveInstanceState ; // 是否是从保存的状态

    public static final String PREF_FILE_NAME = "SharedPreferenceFile";
    private static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer = Boolean.valueOf(readFromPreference(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));

        if(savedInstanceState != null){
            fromSaveInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    public void setUp(DrawerLayout drawerLayout, Toolbar toolbar) {
        mDrawerLayout = drawerLayout;
        mActionbarDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout, toolbar, R.string.open, R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mActionbarDrawerToggle);
    }

    public static void saveToPreference(Context context, String preferenceName, String defaultValue){
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(preferenceName,defaultValue);
        editor.apply();
    }

    public static String readFromPreference(Context context, String preferenceName, String defaultValue){
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);

        return sharedPref.getString(preferenceName, defaultValue);
    }


}
