package com.example.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.aidlserver.Book;
import com.example.aidlserver.BookController;

import java.util.List;

public class MainActivity extends AppCompatActivity {
	public static final String TAG = "Client";
	BookController mBookController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.btnGetBookList).setOnClickListener(mOnClickListener);
		findViewById(R.id.btnAddBook).setOnClickListener(mOnClickListener);
		bindService();
	}
	private int bookCnt;
	View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btnGetBookList:
					getBooks();
					break;
				case R.id.btnAddBook:
					if(mConnected){
						Book book=new Book("Girl:"+bookCnt++);
						try {
							mBookController.addBookInOut(book);
							Log.d(TAG,"add a new book to server");
							Log.d(TAG,"book name-- "+book.getName());
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
					break;
			}
		}
	};
	
	private void getBooks() {
		try {
			mBooks = mBookController.getBookList();
			printBookName();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private boolean mConnected;
	ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mBookController = BookController.Stub.asInterface(service);
			mConnected = true;
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mConnected = false;
		}
	};
	
	private void bindService() {
		Intent intent = new Intent();
		intent.setPackage("com.example.aidlserver");
		intent.setAction("com.example.aidlserver.action_book");
		bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mConnected) {
			unbindService(mServiceConnection);
		}
	}
	
	List<Book> mBooks;
	
	private void printBookName() {
		if (null != mBooks) {
			for (Book book : mBooks) {
				Log.d(TAG, book.getName());
			}
		}
		
	}
	
}
