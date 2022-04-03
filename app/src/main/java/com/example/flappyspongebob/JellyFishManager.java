package com.example.flappyspongebob;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class JellyFishManager {
    public Sprite []jelly_fish = new Sprite[3];
    public Random ran =new Random(System.currentTimeMillis());
    public int move_speed = 7;
    public int min_space = 700;
    public int max_space = 1500;
    public void Init(Context context)
    {
        for(int i=0;i<jelly_fish.length;i++)
        {
            jelly_fish[i] = new Sprite(R.raw.jellyfish);
            jelly_fish[i].LoadImgNow(context,R.raw.jellyfish);
        }
        ReSet();
    }
    public void ReSet()
    {
        move_speed = 7;
        for(int i=0;i<jelly_fish.length;i++)
        {
            jelly_fish[i].SetX(ScreenSize.widthPixels+100);
            jelly_fish[i].SetY(ran.nextInt(ScreenSize.heightPixels-SpongeBob.GROUND_HEIGHT-100));
        }

        for(int i=0;i<jelly_fish.length;i++)
        {
            BinJellyFishInfo(i,GetMaxCoralX(),min_space,max_space);
        }
    }

    public void draw(Context context, Canvas canvas)
    {
        for(int i=0;i<jelly_fish.length;i++) {
            jelly_fish[i].draw(context, canvas);
        }
    }

    public boolean isCollidingWith(int ax, int ay, int aw, int ah, int bx, int by, int bw, int bh) {
        if (ay > by + bh || by > ay + ah || ax > bx + bw || bx > ax + aw) {
            return false;
        }
        return true;
    }
    public void BinJellyFishInfo(int index,int basex,int _min_space,int _max_space)
    {
        int space = _min_space+ran.nextInt(_max_space-_min_space);
        jelly_fish[index].SetX(space+basex);
        jelly_fish[index].SetY(ran.nextInt(ScreenSize.heightPixels-SpongeBob.GROUND_HEIGHT-100));
    }

    public int GetMaxCoralX()
    {
        int max = 0;
        for(int i=0;i<jelly_fish.length;i++)
        {
            if(max<jelly_fish[i].GetX())
            {
                max = jelly_fish[i].GetX();
            }
        }
        return max;
    }

    public void Loop()
    {
        for(int i=0;i<jelly_fish.length;i++)
        {
            jelly_fish[i].SetX(jelly_fish[i].GetX()-move_speed);
            if(jelly_fish[i].GetX()<=-100)
            {
                BinJellyFishInfo(i,GetMaxCoralX(),min_space,max_space);
            }
        }
    }

    public boolean IsCross(Sprite sprite)
    {
        Rect r1 = new Rect(sprite.GetLeft(),sprite.GetTop(),sprite.GetRight(),sprite.GetBottom());
        for(int i=0;i<jelly_fish.length;i++) {

            Sprite coral = jelly_fish[i];
            boolean is_coll = isCollidingWith(sprite.GetLeft(),sprite.GetTop(),sprite.GetWidth(),sprite.GetHeight(),coral.GetLeft(),coral.GetTop(),coral.GetWidth(),coral.GetHeight());
            if(is_coll)return true;
        }
        return sprite.GetTop()<0;
    }

}
