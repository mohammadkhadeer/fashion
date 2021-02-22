package com.fashion.rest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Categories implements Parcelable {
    String name,name_local;
    @SerializedName("image")
    Flag flag;
    @SerializedName("sub_categories")
    ArrayList<Sub_Cat> sub_catArrayList ;

    public Categories(String name, String name_local, Flag flag, ArrayList<Sub_Cat> sub_catArrayList) {
        this.name = name;
        this.name_local = name_local;
        this.flag = flag;
        this.sub_catArrayList = sub_catArrayList;
    }

    protected Categories(Parcel in) {
        name = in.readString();
        name_local = in.readString();
        flag = in.readParcelable(Flag.class.getClassLoader());
        sub_catArrayList = in.createTypedArrayList(Sub_Cat.CREATOR);
    }

    public static final Creator<Categories> CREATOR = new Creator<Categories>() {
        @Override
        public Categories createFromParcel(Parcel in) {
            return new Categories(in);
        }

        @Override
        public Categories[] newArray(int size) {
            return new Categories[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_local() {
        return name_local;
    }

    public void setName_local(String name_local) {
        this.name_local = name_local;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public ArrayList<Sub_Cat> getSub_catArrayList() {
        return sub_catArrayList;
    }

    public void setSub_catArrayList(ArrayList<Sub_Cat> sub_catArrayList) {
        this.sub_catArrayList = sub_catArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(name_local);
        dest.writeParcelable(flag, flags);
        dest.writeTypedList(sub_catArrayList);
    }
}
