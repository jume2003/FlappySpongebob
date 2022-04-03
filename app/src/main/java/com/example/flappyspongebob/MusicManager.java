package com.example.flappyspongebob;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

public class MusicManager {
    private Context context;
    private  int []sound_res_id = {R.raw.disappointing,R.raw.flap,R.raw.gainpoint,R.raw.imready,R.raw.chase,R.raw.collide,R.raw.swoosh,R.raw.tryagain};
    private MediaPlayer []mediaPlayer = new MediaPlayer[sound_res_id.length];
    public void Init(Context _context)
    {
        context = _context;
        for(int i=0;i<mediaPlayer.length;i++)
        {
            mediaPlayer[i]  = MediaPlayer.create(context,sound_res_id[i]);
        }
    }

    public int GetSoundIndexByResID(int id)
    {
        for(int i=0;i<sound_res_id.length;i++)
        {
           if(sound_res_id[i]==id)
           {
               return  i;
           }
        }
        return  0;
    }

    public boolean IsPlayIng(int res_id)
    {
        return  mediaPlayer[GetSoundIndexByResID(res_id)].isPlaying();
    }

    public void Play(int res_id,boolean isloop)
    {
        try {
            AssetFileDescriptor file = context.getResources().openRawResourceFd(res_id);
            mediaPlayer[GetSoundIndexByResID(res_id)].reset();
            mediaPlayer[GetSoundIndexByResID(res_id)].setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
            mediaPlayer[GetSoundIndexByResID(res_id)].prepare();
            mediaPlayer[GetSoundIndexByResID(res_id)].start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Pause(int res_id)
    {
        if(mediaPlayer[GetSoundIndexByResID(res_id)].isPlaying())
        mediaPlayer[GetSoundIndexByResID(res_id)].pause();
    }

    public void PauseAll()
    {
        for(int i=0;i<sound_res_id.length;i++)
        {
            if(mediaPlayer[i].isPlaying())
            mediaPlayer[i].pause();
        }
    }

}
