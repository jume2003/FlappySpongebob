package com.example.flappyspongebob;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class SpongeBob extends Sprite {
    public static final int ACC_FLAP = 14; // players speed on flapping
    public static final double ACC_Y = 2; // players downward acceleration
    public static final int MAX_VEL_Y = 15; // max vel along Y, max descend speed
    public static final int GROUND_HEIGHT = (int)(ScreenSize.heightPixels*0.3f); // max vel along Y, max descend speed
    private int velocity = 0; // bird's velocity along Y, default same as playerFlapped
    private final int BOTTOM_BOUNDARY = ScreenSize.heightPixels - GROUND_HEIGHT-51;

    SpongeBob(int res_id) {
        super(res_id);
        //SetCollisionWidth(GetCollisionWidth()/2);
    }

    public void Reset()
    {
        SetY((int)(BOTTOM_BOUNDARY));
    }

    public void FreeFall() {
        if (velocity < MAX_VEL_Y)
            velocity -= ACC_Y;
        SetY(Math.min((GetY() - velocity), BOTTOM_BOUNDARY));
    }

    public void Flap() {
            velocity = ACC_FLAP;
        }
}
