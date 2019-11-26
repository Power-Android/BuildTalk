package com.bjjy.buildtalk.entity;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author power
 * @date 2019-11-20 17:10
 * @project BuildTalk
 * @description:
 */
public class PdfInfoEntity implements Parcelable {

    private Uri uri;
    private String name;
    private String path;
    private String url;

    public PdfInfoEntity(Uri uri, String name) {
        this.uri = uri;
        this.name = name;
    }

    public PdfInfoEntity(Uri uri, String name, String path) {
        this.uri = uri;
        this.name = name;
        this.path = path;
    }

    protected PdfInfoEntity(Parcel in) {
        uri = in.readParcelable(Uri.class.getClassLoader());
        name = in.readString();
        path = in.readString();
        url = in.readString();
    }

    public PdfInfoEntity(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public static final Creator<PdfInfoEntity> CREATOR = new Creator<PdfInfoEntity>() {
        @Override
        public PdfInfoEntity createFromParcel(Parcel in) {
            return new PdfInfoEntity(in);
        }

        @Override
        public PdfInfoEntity[] newArray(int size) {
            return new PdfInfoEntity[size];
        }
    };

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(uri, flags);
        dest.writeString(name);
        dest.writeString(path);
        dest.writeString(url);
    }
}
