package com.example.administrator.musictest.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/5/31
 */

@Table(name = "MusicInfo")
public class MusicEntity extends Model implements Parcelable, Cloneable {

    @Override
    public MusicEntity clone() {
        try {
            return (MusicEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Expose
    @Column
    String name;
    @Expose
    @Column
    String url;
    @Expose
    @Column
    String imag;
    @Expose
    @Column
    String singerName;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImag() {
        return imag;
    }

    public void setImag(String imag) {
        this.imag = imag;
    }
    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.imag);
        dest.writeString(this.singerName);
    }

    public MusicEntity() {
    }

    protected MusicEntity(Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
        this.imag = in.readString();
        this.singerName = in.readString();
    }

    public static final Creator<MusicEntity> CREATOR = new Creator<MusicEntity>() {
        public MusicEntity createFromParcel(Parcel source) {
            return new MusicEntity(source);
        }

        public MusicEntity[] newArray(int size) {
            return new MusicEntity[size];
        }
    };


    /**
     * 重写判断等于的方法，通过里面的值判断
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MusicEntity)
        {
            MusicEntity mm= (MusicEntity) obj;
            return this.name.equals(mm.getName())&&this.url.equals(mm.getUrl())
                    &&this.imag.equals(mm.getImag())&&this.singerName.equals(mm.getSingerName());
        }
        return super.equals(obj);
    }
}
