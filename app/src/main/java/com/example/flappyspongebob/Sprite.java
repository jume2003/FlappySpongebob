package com.example.flappyspongebob;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public class Sprite {
    public Bitmap bit_map;
    public PointF anchor = new PointF(1,1);
    public int img_width;
    public int img_height;
    public int src_width;
    public int src_height;
    public boolean is_fit_screen = false;
    private int x;
    private int y;
    private int width;
    private int height;
    private float scal_width = 1.0f;
    private float scal_height = 1.0f;
    int cur_resid;
    int set_resid;
    private Paint paint = new Paint();
    private Rect srcrect;
    private Rect dstrect;
    private boolean visible =true;

    Sprite(int res_id)
    {
        LoadImg(res_id);
        SetAnchor(0.5f,0.5f);
        paint.setStyle(Paint.Style.STROKE);//充满
        paint.setAntiAlias(true);// 设置画笔的锯齿效果
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(30);
    }

    public void LoadImg(int res_id)
    {
        set_resid = res_id;
    }

    public void LoadImgNow(Context context,int res_id)
    {
        set_resid = res_id;
        LoadImg(context,res_id);
    }

    public void LoadImg(Context context,int res_id)
    {
        bit_map = BitmapFactory.decodeResource(context.getResources(),res_id);
        src_width=img_width = bit_map.getWidth();
        src_height=img_height = bit_map.getHeight();
        srcrect = new Rect(0,0,img_width,img_height);
        dstrect = new Rect(0,0,(int)(ScreenSize.widthPixels),(int)(ScreenSize.heightPixels));
        SetWidth(img_width);
        SetHeight(img_height);
    }

    public void SetAnchor(float x,float y)
    {
        anchor.x = x;
        anchor.y = y;
    }

    public void SetX(int _x)
    {
        x = _x;
    }

    public void SetY(int _y)
    {
        y = _y;
    }

    public void SetWidth(int _w)
    {
        width = _w;
    }

    public void SetHeight(int _h)
    {
        height = _h;
    }

    public void SetVisible(boolean _visible)
    {
        visible = _visible;
    }

    public boolean GetVisible()
    {
        return visible;
    }

    public int GetX()
    {
        return x;
    }

    public int GetY()
    {
        return y;
    }

    public int GetWidth()
    {
        return width;
    }

    public int GetHeight()
    {
        return height;
    }

    public int GetLeft()
    {
        return (int)(x-img_width*anchor.x);
    }

    public int GetTop()
    {
        return (int)(y-img_height*anchor.y);
    }

    public int GetRight()
    {
        return (int)(x+img_width*anchor.x);
    }

    public int GetBottom()
    {
        return (int)(y+img_height*anchor.y);
    }

    public void SetXY(int _x,int _y)
    {
        x = _x;
        y = _y;
    }

    public float GetScalWidth()
    {
        return scal_width;
    }

    public void SetScalWidth(float scalwidth)
    {
        scal_width = scalwidth;
    }

    public float GetScalHeight()
    {
        return scal_height;
    }

    public void SetScalHeight(float scalheight)
    {
        scal_height = scalheight;
    }

    public void draw(Context context,Canvas canvas) {
        if(visible)
        {
            if(set_resid!=cur_resid)
            {
                cur_resid = set_resid;
                LoadImg(context,cur_resid);
            }
            if(is_fit_screen)
            {
                canvas.drawBitmap(bit_map,srcrect,dstrect,null);
            }
            else
            {
                Rect rect1 = new Rect(0,0,src_width,src_height);
                img_width = (int)(src_width*scal_width);
                img_height = (int)(src_height*scal_height);
                Rect rect2 = new Rect(GetLeft(),GetTop(),GetLeft()+img_width,GetTop()+img_height);
                canvas.drawBitmap(bit_map,rect1,rect2, null);
            }

            //canvas.drawRect(GetLeft(),GetTop(),GetRight(),GetBottom(),paint);
        }

    }

}
