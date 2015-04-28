package com.yoyo.test01;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yoyo.test01.Adapter.MyRecyclerAdapter;
import com.yoyo.test01.data.ListDataInfo;
import com.yoyo.yotest01.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {
    private DrawerLayout mDrawerLayout;
    private View mDrawerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView mRecyclerView;

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

    public static List<ListDataInfo> getData() {
        int[] iconIds = {R.drawable.ic_number1,R.drawable.ic_number2,
                R.drawable.ic_number3,R.drawable.ic_number4,};
        String[] titles = {"title1" , "title2","title3", "title4"};

        List<ListDataInfo> data = new ArrayList<ListDataInfo>();
        for(int i = 0; i < 100; i++)
        {
            ListDataInfo currData = new ListDataInfo();
            currData.iconId = iconIds[i%4];
            currData.title = titles[i%4];
            data.add(currData);
        }
        return data;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_list);

        mRecyclerView.setAdapter(new MyRecyclerAdapter(getActivity(), getData()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(getActivity(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int Position) {
                Toast.makeText(getActivity(),Position + " clicked.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int Position) {
                Toast.makeText(getActivity(),Position + " LongClicked.", Toast.LENGTH_SHORT).show();
            }
        }));
        return layout;
    }

    public void setUp(int drawerFragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        mDrawerView = getActivity().findViewById(drawerFragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout, toolbar, R.string.open, R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if(slideOffset < 0.6){
                    toolbar.setAlpha(1-slideOffset);
                }
            }
        };

        if(!mUserLearnedDrawer && !fromSaveInstanceState){
            mDrawerLayout.openDrawer(mDrawerView);
        }

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();  // 三条杠
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
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


    class RecyclerItemTouchListener implements RecyclerView.OnItemTouchListener{
        ClickListener clickListener;
        GestureDetector gestureDetector;
        public RecyclerItemTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
            this.clickListener = clickListener;
            this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = null;
                    if(recyclerView != null){
                        child = recyclerView.findChildViewUnder(e.getX(),e.getY());
                    }
                    if(clickListener!=null && child != null){
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());
            if(clickListener != null && child != null && gestureDetector.onTouchEvent(motionEvent)){
                clickListener.onClick(child, recyclerView.getChildPosition(child));
            }
            return false;
        }

        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

        }
    }

    public interface ClickListener{
        public void onClick(View view, int Position);
        public void onLongClick(View view, int Position);
    }
}
