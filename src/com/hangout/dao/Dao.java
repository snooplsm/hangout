package com.hangout.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hangout.DatabaseHelper;

public abstract class Dao {

	public interface Parser<T> {
		T parse(Cursor cursor);
		
		ContentValues args(T t);
	}
	
	protected <T> List<T> all(Cursor cursor, Parser<T> parser) {
		List<T> list = new ArrayList<T>(cursor.getCount());
		for(int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToNext();
			T t = parser.parse(cursor);
			list.add(t);
		}
		cursor.close();
		return list;
	}
	
	protected Cursor cursor(String sql, Object...vars) {
		return helper.getWritableDatabase().rawQuery(String.format(sql,vars), null);
	}
	
	protected void insert(String table, ContentValues cv) {
		SQLiteDatabase db = db();
		db.beginTransaction();
		try {
			db.insertOrThrow(table, null, cv);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}		
	}
	
	protected SQLiteDatabase db() {
		return helper.getWritableDatabase();
	}
	
	protected void update(String table, ContentValues cv, String where, Object...args) {
		SQLiteDatabase db = db();
		db.beginTransaction();
		try {
			db.update(table, cv, String.format(where,args), null);
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	
	protected int count(String sql, Object...vars) {
		Cursor cursor = cursor(sql,vars);
		cursor.moveToFirst();
		int count = cursor.getInt(0);
		cursor.close();
		return count;
	}
	
	protected DatabaseHelper helper;
	
	protected <T> T only(Cursor cursor, Parser<T> parser) {
		if(cursor.getCount()>0) {
			cursor.moveToFirst();
			T t = parser.parse(cursor);
			cursor.close();
			return t;
		}
		cursor.close();
		return null;		
	}

	public void setHelper(DatabaseHelper helper) {
		this.helper = helper;
	}
	
}
