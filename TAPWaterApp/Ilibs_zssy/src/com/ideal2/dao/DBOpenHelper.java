package com.ideal2.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	public DBOpenHelper(Context context) {
		super(context, "ideal.db", null, 1);//<��>/databases/
	}

	@Override
	public void onCreate(SQLiteDatabase db) {//�������ݿ�ÿһ�α�������ʱ����õ�
		db.execSQL("CREATE TABLE EditTestPrompt(id integer primary key autoincrement, mark varchar(20) Null, item VARCHAR(500) Null)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL("ALTER TABLE person ADD amount integer");
	}

}
