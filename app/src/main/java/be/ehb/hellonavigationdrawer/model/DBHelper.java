package be.ehb.hellonavigationdrawer.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Maarten De Weerdt on 4/05/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context, DBNote.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStatement = "CREATE table " + DBNote.TABLE_NOTES + " ( "
                + DBNote._ID + " integer primary key autoincrement, "
                + DBNote.NOTE_TITLE + " text not null, "
                + DBNote.NOTE_CONTENT + " text not null, "
                + DBNote.NOTE_PUBLISHEDDATE + " text not null, "
                + DBNote.NOTE_ADAPTEDDATE + " text not null "
                + ")";

        db.execSQL(createStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
