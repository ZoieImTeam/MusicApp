package com.example.administrator.musictest.entity;

import java.util.ArrayList;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/5/31
 */
public class StaticGloaData {

    public static ArrayList<MusicEntity> getMusic() {
        ArrayList<MusicEntity> mMusicList = new ArrayList<>();
        MusicEntity musicEntity=new MusicEntity();
        musicEntity.setImag("http://114.215.119.51/datas/files/binvsheApp/20160419/1461053193901.png");
        musicEntity.setName("演员");
        musicEntity.setUrl("http://pianke.file.alimmdn.com/upload/20160508/b338fe51953d91a7e8c5e57fc9155b0f.MP3");
        mMusicList.add(musicEntity);
        mMusicList.add(musicEntity);
        mMusicList.add(musicEntity);
        mMusicList.add(musicEntity);
        mMusicList.add(musicEntity);
        mMusicList.add(musicEntity);
        mMusicList.add(musicEntity);
        mMusicList.add(musicEntity);
        mMusicList.add(musicEntity);
        mMusicList.add(musicEntity);
        return mMusicList;
    }
}
