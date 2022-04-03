package com.example.flappyspongebob;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BoardBox extends LinearLayout {
    public TextView text_score;
    public TextView text_top5_score;
    public TextView text_youscore;
    public TextView text_top5;
    public Button btn_name;

    public EditText edit_name;

    public BoardBox(Context context)
    {
        this(context,null);
    }

    public BoardBox(Context context, AttributeSet attrs)
    {
        this(context, attrs,0);
    }

    public BoardBox(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.you_score, this);
        text_score = view.findViewById(R.id.text_score);
        text_top5_score = view.findViewById(R.id.text_topscore);
        text_youscore= view.findViewById(R.id.text_youscore);
        text_top5= view.findViewById(R.id.text_top5);
        btn_name = view.findViewById(R.id.btn_name);
        edit_name = view.findViewById(R.id.edit_name);
    }

    public void SetOkListener(View.OnClickListener l)
    {
        btn_name.setOnClickListener(l);
    }

    public void Show(int mode)
    {
        if(mode==0)
        {
            text_score.setVisibility(VISIBLE);
            text_top5_score.setVisibility(VISIBLE);
            text_youscore.setVisibility(VISIBLE);
            text_top5.setVisibility(VISIBLE);
            edit_name.setVisibility(GONE);
            btn_name.setVisibility(GONE);
        }else
        {
            text_score.setVisibility(GONE);
            text_top5_score.setVisibility(GONE);
            text_youscore.setVisibility(GONE);
            text_top5.setVisibility(GONE);
            edit_name.setVisibility(VISIBLE);
            btn_name.setVisibility(VISIBLE);
            edit_name.setText("");
        }

    }

    public void SetScore(String msg)
    {
        text_score.setText(msg);
    }

    public void SetTop5(String msg)
    {
        text_top5_score.setText(msg);
    }


}
