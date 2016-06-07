package com.example.administrator.musictest.Dao;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.administrator.musictest.entity.MusicEntity;

import java.util.List;

/**
 * Created by zofnk on 2016/6/7.
 */
public final class DaoClient {
    public DaoClient() {
    }

    public static void insert(MusicEntity musicEntity) {
        MusicEntity newArticle = musicEntity.clone();
        newArticle.save();
    }

    public static void deleteAll(MusicEntity musicEntity) {
        new Delete()
                .from(MusicEntity.class)
                .where("url = ?", musicEntity.getUrl())
                .execute();
    }

    public static MusicEntity getQueryResult() {
        return new Select()
                .from(MusicEntity.class)
                .orderBy("DESC()")
                .executeSingle();
    }

    public static List<MusicEntity> getAllQueryResult(MusicEntity musicentity) {
        return new Select()
                .from(MusicEntity.class)
                .where("MusicEntity = ?", musicentity.getUrl())
                .orderBy("DESC()")
                .execute();
    }
}
