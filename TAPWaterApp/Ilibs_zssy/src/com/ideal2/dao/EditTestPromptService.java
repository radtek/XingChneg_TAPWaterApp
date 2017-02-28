package com.ideal2.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class EditTestPromptService {
	private DBOpenHelper dbOpenHelper;
	
	public EditTestPromptService(Context context) {
		this.dbOpenHelper = new DBOpenHelper(context);
	}
	/**
	 * ��Ӽ�¼
	 * @param editTestPromptDto
	 */
	public void save(EditTestPromptDto editTestPromptDto){
		try {
			SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
//		values.put("id", editTestPromptDto.getId());
			values.put("mark", editTestPromptDto.getMark());
			values.put("item", editTestPromptDto.getItem());
			if(!"".equals(editTestPromptDto.getItem())){
				db.insert("EditTestPrompt", null, values);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ɾ����¼
	 * @param id ��¼ID
	 */
	public void delete(String id){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.delete("EditTestPrompt", "id=?", new String[]{id.toString()});
	}
	/**
	 * ���¼�¼
	 * @param editTestPromptDto
	 */
	public void update(EditTestPromptDto editTestPromptDto){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("id", editTestPromptDto.getId());
		values.put("mark", editTestPromptDto.getMark());
		values.put("item",  editTestPromptDto.getItem());
		db.update("EditTestPrompt", values, "id=?", new String[]{editTestPromptDto.getId().toString()});
	}
	/**
	 * ��ѯ��¼
	 * @param id ��¼ID
	 * @return
	 */
	public EditTestPromptDto find(String mark){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("EditTestPrompt", null, "mark=?", new String[]{mark}, null, null, null);
		
		if(cursor.moveToFirst()){
			String item = cursor.getString(cursor.getColumnIndex("item"));
			String id = cursor.getString(cursor.getColumnIndex("id"));
			String emark = cursor.getString(cursor.getColumnIndex("mark"));
			return new EditTestPromptDto(id, emark, item);
		}
		cursor.close();
		return null;
	}
	/**
	 * ��ҳ��ȡ��¼
	 * @param offset ����ǰ���������¼
	 * @param maxResult ÿҳ��ȡ��������¼
	 * @return
	 */
	public List<EditTestPromptDto> findAllByItem(String conditionItem,int offset, int maxResult){
		List<EditTestPromptDto> editTestPromptDtos = new ArrayList<EditTestPromptDto>();
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("EditTestPrompt", null, "item like ?", new String[]{"%"+conditionItem+"%"}, null, null, "id desc", offset+ ","+ maxResult);
		
		while(cursor.moveToNext()){
			String item = cursor.getString(cursor.getColumnIndex("item"));
			String id = cursor.getString(cursor.getColumnIndex("id"));
			String emark = cursor.getString(cursor.getColumnIndex("mark"));
			editTestPromptDtos.add(new EditTestPromptDto(id, emark, item));
		}
		cursor.close();
		return editTestPromptDtos;
	}
	/**
	 * ��ȡ��¼����
	 * @return
	 */
	public long getCount(){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("EditTestPrompt", new String[]{"count(*)"}, null, null, null, null, null);
		cursor.moveToFirst();
		long result = cursor.getLong(0);
		cursor.close();
		return result;
	}
}
