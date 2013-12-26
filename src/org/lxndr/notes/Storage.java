package org.lxndr.notes;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Storage extends SQLiteOpenHelper {
	
	static final int VERSION = 1;
	
	enum ItemType {
		TEXT,
		LIST,
		ALARM
	}
	
	enum TextColor {
		NONE,
		RED,
		ORANGE,
		YELLOW,
		GREEN,
		LIGHT_BLUE,
		BLUE,
		VIOLET
	}
	
	
	private SQLiteDatabase m_DB;
	
	
	public Storage(Context context) {
		super(context, "default", null, VERSION);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		/* folder tree */
		db.execSQL("CREATE TABLE folders ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "parent INTEGER,"
				+ "name TEXT)");
		/* notes */
		db.execSQL("CREATE TABLE notes ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "folder INTEGER,"
				+ "title TEXT,"
				+ "created INTEGER,"
				+ "changed INTEGER,"
				+ "position INTEGER)");
		/* note structure */
		db.execSQL("CREATE TABLE structure ("
				+ "note INTEGER,"
				+ "item INTEGER,"
				+ "position INTEGER,"
				+ "title TEXT)");
		/* text items */
		db.execSQL("CREATE TABLE type_text ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "text TEXT,"
				+ "color INTEGER)");
		/* check list items */
		db.execSQL("CREATE TABLE type_list ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "text TEXT)");
		/* alarm items */
		db.execSQL("CREATE TABLE type_alarm ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "text TEXT,"
				+ "time INTEGER)");
		
		/*
		 * SELECT * FROM folders WHERE parent=?
		 * 
		 * 
		 * SELECT * FROM type_list WHERE 
		 */
	}
	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	
	public void init() {
		m_DB = getWritableDatabase();
	}
	
	
	public long addFolder(long parent, String name) {
		ContentValues values = new ContentValues();
		values.put("parent", parent);
		values.put("name", name);
		return m_DB.insert("structure", null, values);
	}
	
	
	public void editFolder(long id, String name) {
		ContentValues values = new ContentValues();
		values.put("name", name);
		m_DB.update("structure", values, "id=" + id, null);
	}
	
	
	public void moveFolder(long id, long parent) {
		ContentValues values = new ContentValues();
		values.put("parent", parent);
		m_DB.update("structure", values, "id=" + id, null);
	}
	
	
	public void deleteFolder(long id) {
		m_DB.delete("structure", "id=" + id, null);
	}
	
	
	public List<Item> getFolderList(long folder) {
		Cursor q = m_DB.query("structure", new String[] {"id", "name"},
				"parent=" + folder, null, null, null, null);
		List<Item> list = new ArrayList<Item>();
		
		if (q.moveToFirst()) {
			do {
				Item item = new Item();
				item.id = q.getLong(0);
				item.title = q.getString(1);
				list.add(item);
			} while (q.moveToNext());
		}
		
		q.close();
		return list;
	}
	
	
	public long addNote(long folder, String title, String body) {
		ContentValues values = new ContentValues();
		values.put("folder", folder);
		values.put("title", title);
		values.put("body", body);
		return m_DB.insert("notes", null, values);
	}
	
	
	public void editNote(long id, String title, String body) {
		ContentValues values = new ContentValues();
		values.put("title", title);
		values.put("body", body);
		m_DB.update("notes", values, "id=" + id, null);
	}
	
	
	public void editNoteFolder(long id, long folder) {
		ContentValues values = new ContentValues();
		values.put("folder", folder);
		m_DB.update("notes", values, "id=" + id, null);
	}
	
	
	public void deleteNote(long id) {
		m_DB.delete("notes", "id=" + id, null);
	}
	
	
	public List<Item> getNoteList(long folder) {
		Cursor q = m_DB.query("notes", new String[] {"id", "title"},
				"folder=" + folder, null, null, null, null);
		List<Item> list = new ArrayList<Item>();
		
		if (q.moveToFirst()) {
			do {
				Item item = new Item();
				item.id = q.getLong(0);
				item.title = q.getString(1);
				list.add(item);
			} while (q.moveToNext());
		}
		
		q.close();
		return list;
	}
	
	
	class What {
	}
	
	
	public List<What> getNote() {
		List<What> list = new ArrayList<What>();
		return list;
	}
}
