package it.carmine.bouncyrun.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;



public class SqlStorage {  


    SQLiteDatabase mDb;
    DbHelper mDbHelper;
    Context mContext;
    private static final String DB_NAME="bouncyrundb";//nome db
    private static final int DB_VERSION=1; //versione db
    
    //query per la tabella
    private static final String USER_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
    		+ UserMetaData.USER_TABLE + " (" 
    		+ UserMetaData.ID+ " integer primary key autoincrement, "
    		+ UserMetaData.USER_NAME_KEY + " text not null);";
    
    
    
    
    public SqlStorage(Context ctx){
    	mContext=ctx;
    	//helper
    	mDbHelper=new DbHelper(ctx, DB_NAME, null, DB_VERSION);   
    }
    
    public void open(){
    	//prendo il database per scriverci
    	mDb=mDbHelper.getWritableDatabase();    
    }
    
    public void close(){ 
    	//chiudo il database
    	mDb.close();
    }
    //metodo per inserire il nome
    public void insertNick(String name){
    	//metodo per inserire i dati
    	ContentValues cv=new ContentValues();
    	cv.put(UserMetaData.USER_NAME_KEY, name);
    	mDb.insert(UserMetaData.USER_TABLE, null, cv);
    }
    //prendo il nick
    public Cursor fetchNick(){
    	//metodo per fare la query di tutti i dati
    	return mDb.query(UserMetaData.USER_TABLE, null,null,null,null,null,null);               
    }

    static class UserMetaData {
    	// i metadati della tabella, accessibili ovunque
    	static final String USER_TABLE = "users";
    	static final String ID = "_id";
    	static final String USER_NAME_KEY = "name";
    }

    private class DbHelper extends SQLiteOpenHelper {
    	//classe che ci aiuta nella creazione del db
    	public DbHelper(Context context, String name, CursorFactory factory,int version) {
    		super(context, name, factory, version);
    	}
    	@Override
    	public void onCreate(SQLiteDatabase _db) {
    		//solo quando il db viene creato, creiamo la tabella
    		_db.execSQL(USER_TABLE_CREATE);
    	}
    	@Override
    	public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
    		//qui mettiamo eventuali modifiche al db, se nella nostra nuova versione della app, il db cambia numero di versione
    	}
    }
}