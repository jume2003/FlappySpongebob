package com.example.flappyspongebob;

import android.content.Context;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

public class TopScoreInfo {
    public SimpleDateFormat formatter = new SimpleDateFormat("m:s:SSS");
    public static class ScoreInfo implements Comparable<ScoreInfo>
    {
        public String name;
        public long game_time;
        ScoreInfo(String _name,long _game_time)
        {
            name = _name;
            game_time = _game_time;
        }

        @Override
        public int compareTo(ScoreInfo scoreInfo) {
            int i =(int)(scoreInfo.game_time-this.game_time);
            return i;
        }
    }
    public List<ScoreInfo> score_list = new ArrayList<>();
    public void Init()
    {
        for(int i=0;i<5;i++)
        {
            score_list.add(new ScoreInfo("123"+i,i+100));
        }
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
    }
    public String GetTopString()
    {
        String msg = "";
        String []title = {"1ST","2ND","3RD","4TH","5TH"};
        for(int i=0;i<score_list.size();i++)
        {
            ScoreInfo scoreInfo = score_list.get(i);
            msg += ""+title[i%5]+" "+formatter.format(scoreInfo.game_time)+" "+scoreInfo.name+"\n";
        }
        return msg;
    }

    public boolean IsTop(int game_time)
    {
        for(int i=0;i<score_list.size();i++)
        {
            ScoreInfo scoreInfo = score_list.get(i);
            if(game_time>=scoreInfo.game_time)return true;
        }
        return false;
    }


    public void InsterScore(String name,int game_time)
    {
        score_list.add(new ScoreInfo(name,game_time));
        Collections.sort(score_list);
        score_list.remove(score_list.size()-1);
    }

    public void Save(Context context)
    {
        String []title = {"1ST","2ND","3RD","4TH","5TH"};
        for(int i=0;i<score_list.size();i++)
        {
            SPUtils.putInt(context,title[i]+"_time",(int)score_list.get(i).game_time);
            SPUtils.putString(context,title[i]+"_name",score_list.get(i).name);
        }
    }

    public void Read(Context context)
    {
        int count = score_list.size();
        score_list.clear();
        String []title = {"1ST","2ND","3RD","4TH","5TH"};
        for(int i=0;i<count;i++)
        {
            int game_tiem = SPUtils.getInt(context,title[i]+"_time",0);
            String name =  SPUtils.getString(context,title[i]+"_name","");
            score_list.add(new ScoreInfo(name,game_tiem));
        }
    }


}
