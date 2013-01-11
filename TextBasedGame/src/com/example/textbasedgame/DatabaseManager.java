package com.example.textbasedgame;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/com.example.textbasedgame/databases/";
    private static String DB_NAME = "TextBasedGame";
    private SQLiteDatabase myDb;
    private final Context ctx;

    public DatabaseManager(Context ctx) {
	super(ctx, DB_NAME, null, 1);
	this.ctx = ctx;
	// TODO Auto-generated constructor stub
    }

    public void createDataBase() throws IOException
    {

	boolean dbExist = checkDataBase();

	if (!dbExist)
	{
	    this.getReadableDatabase();

	    try
	    {
		copyDataBase();

	    } catch (IOException e)
	    {

		throw new Error("Error copying database");

	    }
	}
    }

    public void initializeDatabase() throws IOException
    {
	boolean dbExist = checkDataBase();

	if (!dbExist)
	{
	    this.getReadableDatabase();
	    copyDataBase();
	}
    }

    private boolean checkDataBase()
    {

	SQLiteDatabase checkDB = null;

	try
	{
	    String path = DB_PATH + DB_NAME;
	    checkDB = SQLiteDatabase.openDatabase(path, null,
		    SQLiteDatabase.OPEN_READONLY);
	} catch (SQLiteException e)
	{

	}
	if (checkDB != null)
	{
	    checkDB.close();
	}

	return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException
    {
	InputStream is = null;
	OutputStream os = null;
	is = ctx.getAssets().open(DB_NAME);
	String outFileName = DB_PATH + DB_NAME;

	os = new FileOutputStream(outFileName);
	if (is != null && os != null)
	{
	    byte[] buffer = new byte[1024];
	    int length;
	    while ((length = is.read(buffer)) > 0)
	    {
		os.write(buffer, 0, length);
	    }

	    os.flush();
	    os.close();
	    is.close();

	}
    }

    public void openDataBase()
    {
	String path = DB_PATH + DB_NAME;
	myDb = SQLiteDatabase.openDatabase(path, null,
		SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close()
    {
	if (myDb != null)
	{
	    myDb.close();
	}
	super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
	// TODO Auto-generated method stub

    }

}
