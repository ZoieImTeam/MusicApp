package com.example.administrator.musictest.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/5/31
 */
public class MusicEntity implements Parcelable {

    String name;
    String url;
    String imag;
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
}
