package com.asiainfo.netxpert.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.asiainfo.netxpert.R;

/**
 * Created by uuom on 16-8-12.
 */
public class AIButton extends LinearLayout {

    public AIButton(Context context, AttributeSet attributeSet) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.ai_button, this, true);
    }


}
