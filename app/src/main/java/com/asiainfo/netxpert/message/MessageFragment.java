package com.asiainfo.netxpert.message;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asiainfo.netxpert.R;
import com.asiainfo.netxpert.message.adapter.ViewPageAdapter;
import com.asiainfo.netxpert.message.view.TroubleFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageFragment extends Fragment {

    @BindView(R.id.tl_topTab) TabLayout mTabLayout;
    @BindView(R.id.view_pager) ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        ButterKnife.bind(this, view);

        String[] tabTitles = this.getResources().getStringArray(R.array.message_tab_title);
        Fragment[] fragments  = new Fragment[]{new TroubleFragment(1),new TroubleFragment(2),new TroubleFragment(3)};
        ViewPageAdapter adapter = new ViewPageAdapter(this.getChildFragmentManager(), tabTitles, fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        return view;
    }

}
