package com.example.sqlite.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by David Sanjaya on 3/17/2020
 * Sqlite
 */
public class Mahasiswa implements Parcelable {
    private String id;
    private String name;
    private int nim;

    public Mahasiswa() {

    }

    protected Mahasiswa(Parcel in) {
        id = in.readString();
        name = in.readString();
        nim = in.readInt();
    }

    public static final Creator<Mahasiswa> CREATOR = new Creator<Mahasiswa>() {
        @Override
        public Mahasiswa createFromParcel(Parcel in) {
            return new Mahasiswa(in);
        }

        @Override
        public Mahasiswa[] newArray(int size) {
            return new Mahasiswa[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNim() {
        return nim;
    }

    public void setNim(int nim) {
        this.nim = nim;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeInt(nim);
    }
}
