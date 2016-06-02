package com.example.administrator.musictest.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zoi.
 * E-mail：KyluZoi@gmail.com
 * 2016/5/31
 */
public class MusicEntity implements Parcelable {
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

    String name;
    String url;
    String imag;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.imag);
    }

    public MusicEntity() {
    }

    protected MusicEntity(Parcel in) {
        this.name = in.readString();
        this.url = in.readString();
        this.imag = in.readString();
    }

    public static final Parcelable.Creator<MusicEntity> CREATOR = new Parcelable.Creator<MusicEntity>() {
        public MusicEntity createFromParcel(Parcel source) {
            return new MusicEntity(source);
        }

        public MusicEntity[] newArray(int size) {
            return new MusicEntity[size];
        }
    };
}
