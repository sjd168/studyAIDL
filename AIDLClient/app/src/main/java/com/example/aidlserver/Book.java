package com.example.aidlserver;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SuJinde on 2019/9/27.
 */
public class Book implements Parcelable {
	String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Book(String name) {
		this.name = name;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.name);
	}
	
	public void readFromParcel(Parcel in) {
		name=in.readString();
	}
	
	protected Book(Parcel in) {
		this.name = in.readString();
	}
	
	public static final Creator<Book> CREATOR = new Creator<Book>() {
		@Override
		public Book createFromParcel(Parcel source) {
			return new Book(source);
		}
		
		@Override
		public Book[] newArray(int size) {
			return new Book[size];
		}
	};
}
