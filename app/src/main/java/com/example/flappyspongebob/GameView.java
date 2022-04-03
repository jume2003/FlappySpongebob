package com.example.flappyspongebob;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends View {
    public final int WELCOME_STATE = 0;
    public final int PLAYING_STATE = 1;
    public final int GAMEOVER_STATE = 2;
    public final int ENTERNAME_STATE = 3;
    public final int TIME_MSG = 0;
    public final int BTNOK_MSG = 1;
    private Paint paint = new Paint();
    public int game_state = WELCOME_STATE;
    public int game_sub_state = 0;
    public Timer timer = null;
    public BoardBox boardBox;
    public MusicManager musicManager = new MusicManager();
    public List<Sprite> gamebg = new ArrayList<>();
    public Sprite welcomebg;
    public Sprite basebg;
    public CoralsManager coralsManager = new CoralsManager();
    public JellyFishManager jellyFishManager = new JellyFishManager();
    public SpongeBob spongeBob;
    public long beg_sys_time = 0;
    public long dt_ms = 0;
    public int game_point = 0;
    public int game_time = 0;
    public int change_time = 0;
    public Random ran =new Random(System.currentTimeMillis());
    public SimpleDateFormat formatter = new SimpleDateFormat("m:s:SSS");
    public TopScoreInfo topScoreInfo = new TopScoreInfo();
    public GameView(Context context)
    {
        this(context,null);
    }

    public GameView(Context context, AttributeSet attrs)
    {
        this(context, attrs,0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        musicManager.Init(getContext());
        Init(context);
        StartTimer();
    }

    public void Init(Context context)
    {
        DisplayMetrics dm = new DisplayMetrics();
        timer = new Timer();
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        boardBox = MainActivity.boardBox;
        paint.setStyle(Paint.Style.FILL);//充满
        paint.setAntiAlias(true);// 设置画笔的锯齿效果
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(80);
        int max_background_count = 3;
        for(int i=0;i<max_background_count;i++)
        {
            int [] bgresid = {R.raw.background,R.raw.background1,R.raw.background2,R.raw.background3,R.raw.background4,R.raw.background5};
            gamebg.add(new Sprite(bgresid[i]));
            gamebg.get(i).LoadImgNow(getContext(),bgresid[i]);
            //gamebg.get(i).SetScalWidth(ScreenSize.widthPixels*0.9f/welcomebg.src_width);
            //gamebg.get(i).SetScalHeight(ScreenSize.widthPixels*0.9f/welcomebg.src_width);
            //gamebg.get(i).is_fit_screen = true;
            gamebg.get(i).SetAnchor(0,0);
            gamebg.get(i).SetVisible(false);
        }
        ChangeGameBg();
        welcomebg = new Sprite(R.raw.welcome_message);
        welcomebg.LoadImgNow(getContext(),R.raw.welcome_message);
        welcomebg.SetVisible(false);
        welcomebg.SetAnchor(0.5f,0.5f);
        welcomebg.SetScalWidth(ScreenSize.widthPixels*0.9f/welcomebg.src_width);
        welcomebg.SetScalHeight(ScreenSize.widthPixels*0.9f/welcomebg.src_width);
        welcomebg.SetXY((int)(ScreenSize.widthPixels*0.5f),(int)(ScreenSize.heightPixels*0.5f));

        basebg = new Sprite(R.raw.base);
        basebg.LoadImgNow(getContext(),R.raw.base);
        basebg.SetVisible(false);
        basebg.SetAnchor(0.5f,1.0f);
        basebg.SetScalWidth((float)(ScreenSize.widthPixels)/basebg.src_width);
        basebg.SetScalHeight((ScreenSize.heightPixels-51)*0.3f/basebg.src_height);
        basebg.SetXY((int)(ScreenSize.widthPixels*0.5f),ScreenSize.heightPixels);

        spongeBob = new SpongeBob(R.raw.spongebob_character);
        spongeBob.SetX(ScreenSize.widthPixels/5);
        spongeBob.SetY((ScreenSize.heightPixels-spongeBob.img_height)/2);
        spongeBob.Reset();

        coralsManager.Init(getContext());
        jellyFishManager.Init(getContext());
        beg_sys_time = System.currentTimeMillis();

        topScoreInfo.Init();
        topScoreInfo.Read(getContext());
    }

    public BoardBox GetBoardBox()
    {
        if(boardBox==null)
        {
            boardBox = MainActivity.boardBox;
            boardBox.btn_name.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name_str = GetBoardBox().edit_name.getText().toString();
                    if(name_str!=null&&name_str.length()>0)
                    handler.sendEmptyMessage(BTNOK_MSG);
                }
            });
        }
        return boardBox;
    }

    public void StartTimer()
    {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                handler.sendEmptyMessage(TIME_MSG);
            }
        };
        timer.schedule(timerTask, 1000, 56);
    }

    public void ChangeGameBg()
    {
        for(int i=0;i<gamebg.size();i++)
        {
            gamebg.get(i).SetVisible(false);
        }
        gamebg.get(ran.nextInt(gamebg.size())).SetVisible(true);
    }


    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            if(msg.what==TIME_MSG)
            {
                switch (game_state)
                {
                    case WELCOME_STATE:
                    {
                        if(game_sub_state==0)
                        {
                            musicManager.Play(R.raw.imready,false);
                            welcomebg.SetVisible(true);
                            basebg.SetVisible(true);
                            game_time = 0;
                            game_point = 0;
                            change_time = 0;
                            GetBoardBox().setVisibility(GONE);
                            game_sub_state++;
                        }
                        break;
                    }
                    case PLAYING_STATE:
                    {
                        if(game_sub_state==0)
                        {
                            musicManager.PauseAll();
                            welcomebg.SetVisible(false);
                            musicManager.Play(R.raw.chase,true);
                            game_time = 0;
                            game_point = 0;
                            change_time = 0;
                            beg_sys_time = System.currentTimeMillis();
                            jellyFishManager.ReSet();
                            coralsManager.ResSetCorals();
                            //basebg.SetVisible(false);
                            game_sub_state++;
                        }else if(game_sub_state==1)
                        {
                            long dt = System.currentTimeMillis() -beg_sys_time;
                            beg_sys_time =  System.currentTimeMillis();
                            game_time+=dt;
                            change_time+=dt;
                            if(change_time>15000)
                            {
                                ChangeGameBg();
                                coralsManager.move_speed++;
                                jellyFishManager.move_speed++;
                                change_time = 0;
                            }
                            spongeBob.FreeFall();
                            coralsManager.Loop();
                            if(game_time>15000)
                            jellyFishManager.Loop();
                            if(coralsManager.IsCross(spongeBob)||jellyFishManager.IsCross(spongeBob))
                            {
                                musicManager.PauseAll();
                                musicManager.Play(R.raw.tryagain,false);
                                game_state = topScoreInfo.IsTop(game_time)?ENTERNAME_STATE:GAMEOVER_STATE;
                                game_sub_state = 0;
                            }else if(coralsManager.IsGainPoint(spongeBob))
                            {
                                musicManager.Play(R.raw.gainpoint,false);
                                game_point++;
                            }
                        }
                        break;
                    }
                    case ENTERNAME_STATE:
                    {
                        if(game_sub_state==0)
                        {
                            GetBoardBox().setVisibility(VISIBLE);
                            GetBoardBox().Show(1);
                            game_sub_state++;
                        }
                        break;
                    }
                    case GAMEOVER_STATE:
                    {
                        if(game_sub_state==0)
                        {
                            GetBoardBox().setVisibility(VISIBLE);
                            GetBoardBox().Show(0);
                            GetBoardBox().SetScore("Score:"+game_point+"\n"+"Time:"+formatter.format(game_time));
                            GetBoardBox().SetTop5(topScoreInfo.GetTopString());
                            topScoreInfo.Save(getContext());
                            game_sub_state++;
                        }
                        break;
                    }
                }

                invalidate();
            }else if (msg.what==BTNOK_MSG)
            {
                String name_tem = GetBoardBox().edit_name.getText().toString();
                topScoreInfo.InsterScore(name_tem,game_time);
                GetBoardBox().SetScore("Score:"+game_point+"\n"+"Time:"+formatter.format(game_time));
                GetBoardBox().SetTop5(topScoreInfo.GetTopString());
                GetBoardBox().Show(1);
                game_state = GAMEOVER_STATE;
                game_sub_state = 0;
            }

            return true;
        }
    });

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        final float x = event.getX();
        final float y = event.getY();
        final int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
            {
                if(game_state==WELCOME_STATE)
                {
                    game_state = PLAYING_STATE;
                    game_sub_state = 0;
                    game_point = 0;
                    game_time = 0;
                }else if(game_state==PLAYING_STATE)
                {
                    musicManager.Play(R.raw.flap,false);
                    spongeBob.Flap();
                }else if(game_state==GAMEOVER_STATE)
                {
                    if(!musicManager.IsPlayIng(R.raw.tryagain))
                    {
                        coralsManager.ResSetCorals();
                        spongeBob.Reset();
                        game_state = WELCOME_STATE;
                        game_sub_state = 0;
                    }
                }

                break;
            }
        }
        return  true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0;i<gamebg.size();i++)
        gamebg.get(i).draw(getContext(),canvas);
        spongeBob.draw(getContext(),canvas);
        coralsManager.draw(getContext(),canvas);
        jellyFishManager.draw(getContext(),canvas);
        canvas.drawText(""+game_point,(int)(ScreenSize.widthPixels*0.5),(int)(ScreenSize.heightPixels*0.2f),paint);
        basebg.draw(getContext(),canvas);
        canvas.drawText(""+formatter.format(game_time),(int)(200),(int)(ScreenSize.heightPixels-SpongeBob.GROUND_HEIGHT*0.7),paint);
        welcomebg.draw(getContext(),canvas);
    }
}
