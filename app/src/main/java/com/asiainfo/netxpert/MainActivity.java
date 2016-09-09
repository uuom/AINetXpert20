package com.asiainfo.netxpert;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.asiainfo.netxpert.Mine.MineFragment;
import com.asiainfo.netxpert.home.HomeFragment;
import com.asiainfo.netxpert.message.MessageFragment;
import com.asiainfo.netxpert.report.ReportFragment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseAppCompatActivity {

    @BindView(R.id.tabHost) FragmentTabHost mTabHost;

    private Map<String, Fragment> mFragmentMap = new HashMap<>();
    private Class mClass[] = {HomeFragment.class,ReportFragment.class,MessageFragment.class,MineFragment.class};
    private String mTitles[] = {"首页","报表","告警","我"};
    private int mImages[] = {
            R.drawable.tab_home,
            R.drawable.tab_report,
            R.drawable.tab_message,
            R.drawable.tab_mine
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabContent);
        mTabHost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < mTitles.length; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTitles[i]).setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, mClass[i], null);
            mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.WHITE);
        }

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int index = Arrays.asList(mTitles).indexOf(s);
                Class clazz = mClass[index];
                try {
                    Fragment fragment = null;
                    for (Map.Entry<String, Fragment> me:  mFragmentMap.entrySet()){
                        if (s.equals(me.getKey())){
                            fragment = me.getValue();
                        }
                    }
                    if (fragment == null){
                        fragment = (Fragment) clazz.newInstance();
                        mFragmentMap.put(s, fragment);
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.realContent, fragment).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //default fragment
        Fragment defaultFragment = new HomeFragment();
        mFragmentMap.put("首页", defaultFragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.realContent, defaultFragment).commit();
    }

    private View getTabItemView(int index) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item, null);

        ImageView image = (ImageView) view.findViewById(R.id.image);
        TextView title = (TextView) view.findViewById(R.id.title);

        image.setImageResource(mImages[index]);
        title.setText(mTitles[index]);

        return view;
    }
}
