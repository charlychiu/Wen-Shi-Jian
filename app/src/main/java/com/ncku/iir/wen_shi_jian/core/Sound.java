package com.ncku.iir.wen_shi_jian.core;

import android.content.Context;
import android.media.MediaPlayer;

import com.ncku.iir.wen_shi_jian.R;

import java.util.Map;

public class Sound {
    private static MediaPlayer music;
//    private static SoundPool soundPool;
    private static boolean musicSt = true; //音樂開關
//    private static boolean soundSt = true; //音效開關
    private static Context context;
//    private static final int[] musicId = {R.raw.bg};
//    private static Map<Integer,Integer> soundMap; //音效資源id與加載過後的音源id的映射關係表

    /**
     * 初始化方法
     * @param c
     */
    public  Sound(Context c)
    {
        context = c;
        initMusic("budda");
//        initSound();
    }

    //初始化音效播放器
//    private static void initSound()
//    {
//        soundPool = new SoundPool(10,AudioManager.STREAM_MUSIC,100);
//        soundMap = new HashMap<Integer,Integer>();
//        soundMap.put(R.raw.right, soundPool.load(context, R.raw.right, 1));
//        soundMap.put(R.raw.wrong, soundPool.load(context, R.raw.wrong, 1));
//    }

    //初始化音樂播放器
    private static void initMusic(String filename)
    {
//        int r = new Random().nextInt(musicId.length);
        int currentfile = 0;
        if (filename.contains("time")){
            currentfile = R.raw.time;
        }else if(filename.contains("john")){
            currentfile = R.raw.johncena;
        }else if(filename.contains("budda")){
        currentfile = R.raw.budda;
        }

        music = MediaPlayer.create(context,currentfile);
        music.setLooping(true);
    }

    /**
     * 播放音效
     * @param resId 音效資源id
     */
//    public static void playSound(int resId)
//    {
//        if(soundSt == false)
//            return;
//
//        Integer soundId = soundMap.get(resId);
//        if(soundId != null)
//            soundPool.play(soundId, 1, 1, 1, 0, 1);
//    }

    /**
     * 切換一首音樂並播放
     */
    public static void changeAndPlayMusic(String currentmusic)
    {
        if(music != null)
            music.release();
        initMusic(currentmusic);
        setMusicSt(true);
    }

    /**
     * 獲得音樂開關狀態
     * @return
     */
    public static boolean isMusicSt() {
        return musicSt;
    }

    /**
     * 設置音樂開關
     * @param musicSt
     */
    public static void setMusicSt(boolean musicSt) {
        Sound.musicSt = musicSt;
        if(musicSt)
            music.start();
        else
            music.stop();
    }

    /**
     * 獲得音效開關狀態
     * @return
     */
//    public static boolean isSoundSt() {
//        return soundSt;
//    }

    /**
     * 設置音效開關
     * @param soundSt
     */
//    public static void setSoundSt(boolean soundSt) {
//        Sound.soundSt = soundSt;
//    }

    /**
     * 釋放資源
     */
    public void recyle()
    {
        music.release();
//        soundPool.release();
//        soundMap.clear();
    }
}

