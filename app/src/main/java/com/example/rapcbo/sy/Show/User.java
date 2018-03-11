package com.example.rapcbo.sy.Show;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RaPCBO on 30/01/18.
 */

public class User implements Parcelable{
    private String user_id;
    private String username;
    private String email;


    public User(String user_id, String email, String username) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;

    }

    public User() {

    }


    protected User(Parcel in) {
        user_id = in.readString();
        username = in.readString();
        email = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_id);
        dest.writeString(username);
        dest.writeString(email);
    }
}
