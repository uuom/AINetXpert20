package com.asiainfo.netxpert.Mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asiainfo.netxpert.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineFragment extends Fragment {

    @BindView(R.id.mine_toolbar)
    Toolbar mine_toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        ButterKnife.bind(this, view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mine_toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("我");

        return view;
    }

}
