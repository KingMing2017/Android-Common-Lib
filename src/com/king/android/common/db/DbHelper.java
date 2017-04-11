package com.king.android.common.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class DbHelper {

	protected SQLiteOpenHelper mSQLiteOpenHelper = null;
	protected SQLiteDatabase mSQLiteDatabase = null;

	//	public DbHelper(DatabaseOpenHelper helper) {
	//		super();
	//		this.mDatabaseOpenHelper = helper;
	//	}

	public abstract SQLiteOpenHelper getSQLiteOpenHelper();

	public void openDb(){
		if (mSQLiteOpenHelper == null){
			mSQLiteOpenHelper = getSQLiteOpenHelper();
			if (mSQLiteOpenHelper == null){
				try {
					throw new Exception("SQLiteOpenHelper is null.");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		}

		mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
	}

	public void closeDb(){
		if (mSQLiteDatabase != null && mSQLiteDatabase.isOpen()){
			mSQLiteDatabase.close();
		}
	}

	protected long insert(String table, ContentValues values){
		long ret = 0;
		try {
			mSQLiteDatabase.beginTransaction();
			ret = mSQLiteDatabase.insert(table, null, values);
			mSQLiteDatabase.setTransactionSuccessful();
		} finally {
			if (mSQLiteDatabase != null)
				mSQLiteDatabase.endTransaction();
		}
		return ret;
	}

	/**
	 * 
	 * @param table
	 * @param values
	 * @param id : Primary key is id
	 * @return
	 */
	protected int update(String table, ContentValues values, String id){
		int ret = mSQLiteDatabase.update(table, values, "id = ?", new String[]{id});
		return ret;
	}
	
	protected int update(String table, ContentValues values, String columnName, String columnValue){
		int ret = mSQLiteDatabase.update(table, values, columnName+" = ?", new String[]{columnValue});
		return ret;
	}
	
	protected int update(String table, ContentValues values, String columnNames, String[] columnValues){
		int ret = mSQLiteDatabase.update(table, values, columnNames, columnValues);
		return ret;
	}

	/**
	 * 
	 * @param table
	 * @param id : Primary key is id
	 * @return
	 */
	protected int delete(String table, String id){
		int ret = mSQLiteDatabase.delete(table, "id = ?", new String[]{id});
		return ret;
	}

	/**
	 * 
	 * @param table
	 * @param id : Primary key is id
	 * @return 查询结果条数
	 */
	protected int queryById(String table, String id){

		int ret = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ");
		sb.append(table);
		sb.append(" WHERE id = ? ");
		Cursor c = null;
		try {
			c = mSQLiteDatabase.rawQuery(sb.toString(), new String[]{id});
			if (c.moveToFirst()){
				ret = c.getCount();
			}
		} finally {
			if (c != null)
				c.close();
		}
		return ret;
	}
	
	/**
	 * 清空表数据
	 * @param table
	 */
	protected void deleteAll(String table) {
		mSQLiteDatabase.execSQL("DELETE FROM " + table);
	}

	/**
	 * 删除表
	 * @param table
	 */
	protected void dropTable(String table) {
		mSQLiteDatabase.execSQL("DROP TABLE " + table);
	}
}
