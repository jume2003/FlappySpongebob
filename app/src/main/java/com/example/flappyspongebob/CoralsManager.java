package com.example.flappyspongebob;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;


public class CoralsManager {
    public Sprite []corals = new Sprite[50];
    public boolean []is_gainpoint = new boolean[50];
    public Random ran =new Random(System.currentTimeMillis());
    public int move_speed = 10;
    public int min_each_space = 500;
    public int max_each_space = 700;
    public int min_up_down_space = 100;
    public int max_up_down_space = 500;
    public float min_up_down_point = 0.3f;
    public float max_up_down_point = 0.8f;
    public void Init(Context context)
    {
        for(int i=0;i<corals.length;i++)
        {
            corals[i] = new Sprite(R.raw.coral);
            corals[i].LoadImgNow(context,R.raw.coral);
        }
        ResSetCorals();
    }

    public void ResSetCorals()
    {
        move_speed = 10;
        min_each_space = 500;
        max_each_space = 800;
        min_up_down_space = 250;
        max_up_down_space = 600;
        min_up_down_point = 0.3f;
        max_up_down_point = 0.7f;
        for(int i=0;i<corals.length;i++)
        {
            corals[i].SetX(ScreenSize.widthPixels+100);
            is_gainpoint[i] = false;
        }
        for(int i=0;i<corals.length;i++)
        {
            BinCoralInfo(i,GetMaxCoralX(),min_each_space,max_each_space,min_up_down_space,max_up_down_space,min_up_down_point,max_up_down_point);
        }
    }

    public void Loop()
    {
        for(int i=0;i<corals.length;i++)
        {
            corals[i].SetX(corals[i].GetX()-move_speed);
            if(i%2==0&&corals[i].GetX()<=-100)
            {
                BinCoralInfo(i,GetMaxCoralX(),min_each_space,max_each_space,min_up_down_space,max_up_down_space,min_up_down_point,max_up_down_point);
            }
        }
    }

    public void BinCoralInfo(int index,int basex,int _min_each_space,int _max_each_space,int _min_up_down_space,int _max_up_down_space,float _min_up_down_point,float _max_up_down_point)
    {
        if(index%2==0)
        {
            int screen_height = ScreenSize.heightPixels-SpongeBob.GROUND_HEIGHT;
            int each_space = _min_each_space+ran.nextInt(_max_each_space-_min_each_space);
            int up_down_space = _min_up_down_space+ran.nextInt(_max_up_down_space-_min_up_down_space);
            float up_down_point = _min_up_down_point+ran.nextInt((int)((_max_up_down_point-_min_up_down_point)*100))/100.0f;

            float up_udpoint = 1.0f-up_down_point;
            float down_udpoint = up_down_point;
            int  up_py=  (int)(screen_height*up_udpoint-corals[index].img_height)-up_down_space/2;
            int down_py  =  (int)(screen_height*down_udpoint-corals[index].img_height)-up_down_space/2;
            if(up_py<-corals[index].img_height)up_py = -corals[index].img_height+100;
            corals[index].SetXY(basex+each_space,up_py);
            corals[index].SetAnchor(0.5f,0.0f);
            is_gainpoint[index] = false;

            corals[index+1].SetXY(basex+each_space,screen_height-down_py);
            corals[index+1].SetAnchor(0.5f,1.0f);
            is_gainpoint[index+1] = false;
        }
    }


    public int GetMaxCoralX()
    {
        int max = 0;
        for(int i=0;i<corals.length;i++)
        {
            if(max<corals[i].GetX())
            {
                max = corals[i].GetX();
            }
        }
        return max;
    }

    public void draw(Context context, Canvas canvas)
    {
        for(int i=0;i<corals.length;i++) {
            corals[i].draw(context, canvas);
        }
    }

    public boolean isCollidingWith(int ax, int ay, int aw, int ah, int bx, int by, int bw, int bh) {
        if (ay > by + bh || by > ay + ah || ax > bx + bw || bx > ax + aw) {
            return false;
        }
        return true;
    }

    public boolean IsCross(Sprite sprite)
    {
        Rect r1 = new Rect(sprite.GetLeft(),sprite.GetTop(),sprite.GetRight(),sprite.GetBottom());
        for(int i=0;i<corals.length;i++) {

            Sprite coral = corals[i];
            boolean is_coll = isCollidingWith(sprite.GetLeft(),sprite.GetTop(),sprite.GetWidth(),sprite.GetHeight(),coral.GetLeft(),coral.GetTop(),coral.GetWidth(),coral.GetHeight());
            if(is_coll)return true;
        }
        return sprite.GetTop()<0;
    }

    public boolean IsGainPoint(Sprite sprite)
    {
        for(int i=0;i<corals.length;i++) {
            if(i%2==0)
            {
                if(is_gainpoint[i]==false&&corals[i].GetX()<sprite.GetX())
                {
                    is_gainpoint[i] = true;
                    return  true;
                }
            }
        }
        return false;
    }




}
