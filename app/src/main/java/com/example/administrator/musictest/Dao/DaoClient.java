package com.example.administrator.musictest.Dao;

import android.util.Log;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.administrator.musictest.entity.MusicEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zofnk on 2016/6/7.
 */
public class DaoClient {
    private DaoClient() {}




    public static void insert(ArrayList<MusicEntity> musicEntity) {
        if (musicEntity.size() != 0) {
            for (int i = 0; i < musicEntity.size(); i++) {
                MusicEntity newArticle = musicEntity.get(i).clone();
                newArticle.save();
            }
        } else Log.d("DaoClient", "insert null");
    }

    public static void deleteAll(ArrayList<MusicEntity> musicEntity) {
        if (musicEntity.size() != 0) {
            for(int i=0;i<musicEntity.size();i++)
            new Delete()
                    .from(MusicEntity.class)
                    .where("url = ?", musicEntity.get(i).getUrl())
                    .execute();
        } else Log.d("DaoClient", "delete null");
    }

    /**
     *  单曲查询
     * @return
     */
    public static MusicEntity getQueryResult() {
        return new Select()
                .from(MusicEntity.class)
                .orderBy("DESC()")
                .executeSingle();
    }


    /**
     * 列表查询
     * @param musicentity
     * @return
     */
    public static ArrayList<MusicEntity> getAllQueryResult(MusicEntity musicentity) {
        List<MusicEntity> data = new Select()
                .from(MusicEntity.class)
                .where("MusicEntity = ?", musicentity.getUrl())
                .orderBy("DESC()")
                .execute();
        ArrayList<MusicEntity> datas = new ArrayList<>();
        datas.addAll(data);
        return datas;
    }

    /**
     * 
     * 随机查询
     * @return
     */
    public static ArrayList<MusicEntity> getRandomResult()
    {
        // TODO: 2016/6/8 填充随机查询代码  
        return null;
    }
}
