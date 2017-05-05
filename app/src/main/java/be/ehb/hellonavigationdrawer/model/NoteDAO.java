package be.ehb.hellonavigationdrawer.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Maarten De Weerdt on 27/04/2017.
 */

public class NoteDAO {

    public static final NoteDAO INSTANCE = new NoteDAO();

    private DBHelper mDbHelper;
    private SQLiteDatabase mDatabase;

    public void openConnection(Context context){
        mDbHelper = new DBHelper(context);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public boolean deleteNote(Note n)
    {
        if(mDatabase == null)
            return false;

        long idToDelete = n.getId();

        int nrOfRemovedRows = mDatabase.delete(DBNote.TABLE_NOTES, DBNote._ID + " = " + idToDelete, null);

        if(nrOfRemovedRows == 0)
            return false;
        else
            return true;
    }

    public boolean addNote(Note n)
    {
        if(mDatabase == null)
            return false;

        ContentValues mValues = new ContentValues();

        mValues.put(DBNote.NOTE_TITLE, n.getTitel());
        mValues.put(DBNote.NOTE_CONTENT, n.getInhoud());
        mValues.put(DBNote.NOTE_PUBLISHEDDATE, String.valueOf(n.getAanmaakDatum()));
        mValues.put(DBNote.NOTE_ADAPTEDDATE, String.valueOf(n.getGewijzigdDatum()));

        long resultID = mDatabase.insert(DBNote.TABLE_NOTES, null, mValues);

        if(resultID == -1)
            return false;
        else
            return true;
    }

    public  void updateNote(Note n)
    {
        ContentValues mValues = new ContentValues();

        mValues.put(DBNote._ID, n.getId());
        mValues.put(DBNote.NOTE_TITLE, n.getTitel());
        mValues.put(DBNote.NOTE_CONTENT, n.getInhoud());
        mValues.put(DBNote.NOTE_PUBLISHEDDATE, String.valueOf(n.getAanmaakDatum()));
        mValues.put(DBNote.NOTE_ADAPTEDDATE, String.valueOf(n.getGewijzigdDatum()));

        int idToUpdate = n.getId();

        mDatabase.update(DBNote.TABLE_NOTES, mValues, DBNote._ID + " = " + idToUpdate, null);
    }

    public ArrayList<Note> getAllNotes(){

        ArrayList<Note> notes = new ArrayList<>();

        Cursor mCursor = mDatabase.rawQuery("SELECT * FROM " + DBNote.TABLE_NOTES, null);

        mCursor.moveToFirst();

        while(!mCursor.isAfterLast()){

            Note tempNote = new Note();

            int idIndex = mCursor.getColumnIndex(DBNote._ID);
            tempNote.setId(mCursor.getInt(idIndex));

            int titleIndex = mCursor.getColumnIndex(DBNote.NOTE_TITLE);
            tempNote.setTitel(mCursor.getString(titleIndex));

            int contentIndex = mCursor.getColumnIndex(DBNote.NOTE_CONTENT);
            tempNote.setInhoud(mCursor.getString(contentIndex));


            int publishedIndex = mCursor.getColumnIndex(DBNote.NOTE_PUBLISHEDDATE);
            int adaptedIndex = mCursor.getColumnIndex(DBNote.NOTE_ADAPTEDDATE);

            try {
                SimpleDateFormat sdf = new SimpleDateFormat();
                Date d1 = sdf.parse(mCursor.getString(publishedIndex));
                Date d2 = sdf.parse(mCursor.getString(adaptedIndex));
                tempNote.setAanmaakDatum(d1);
                tempNote.setGewijzigdDatum(d2);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            notes.add(tempNote);

            mCursor.moveToNext();
        }
        return notes;
    }
}
