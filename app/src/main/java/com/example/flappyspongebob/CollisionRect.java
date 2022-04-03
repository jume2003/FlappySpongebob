package com.example.flappyspongebob;

public class CollisionRect {
    public int x;
    public int y;
    public int w;
    public int h;
    CollisionRect(){}
    CollisionRect(int _x,int _y,int _w,int _h)
    {
        x =_x;
        y = _y;
        w = _w;
        h = _h;
    }
    public int GetLeft()
    {
        return x;
    }

    public int GetRight()
    {
        return x+w;
    }

    public int GetTop()
    {
        return y;
    }

    public int GetBottom()
    {
        return y+h;
    }
}
