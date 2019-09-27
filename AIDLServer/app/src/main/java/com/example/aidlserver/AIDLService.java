package com.example.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SuJinde on 2019/9/27.
 */
public class AIDLService extends Service {
	public static final String TAG = AIDLService.class.getSimpleName();
	List<Book> mBooks;
	
	@Override
	public void onCreate() {
		super.onCreate();
		initData();
	}
	
	private void initData() {
		mBooks = new ArrayList<>();
		mBooks.add(new Book("螺蛳粉"));
		mBooks.add(new Book("羊肉粉"));
		mBooks.add(new Book("老友粉"));
		mBooks.add(new Book("酸辣粉"));
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return mStub;
	}
	
	BookController.Stub mStub = new BookController.Stub() {
		@Override
		public List<Book> getBookList() throws RemoteException {
			return mBooks;
		}
		
		@Override
		public void addBookInOut(Book book) throws RemoteException {
			if (null == book) {
				Log.e(TAG, "receive a null book");
			} else {
				mBooks.add(book);
				printServerBook();
			}
		}
	};
	
	private void printServerBook() {
		for (int i = 0; i < mBooks.size(); i++) {
			Log.d(TAG, "server book " + i + " : " + mBooks.get(i).getName());
		}
	}
}
