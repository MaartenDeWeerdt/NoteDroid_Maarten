package be.ehb.hellonavigationdrawer.model;

import android.provider.BaseColumns;

import java.util.Date;

/**
 * Created by Maarten De Weerdt on 4/05/2017.
 */

public class DBNote implements BaseColumns {

    public static final String DB_NAME = "dbnotes";
    public static final String TABLE_NOTES = "note";
    public static final String NOTE_TITLE = "titel";
    public static final String NOTE_CONTENT = "inhoud";
    public static final String NOTE_PUBLISHEDDATE = "aanmaakdatum";
    public static final String NOTE_ADAPTEDDATE = "gewijzigddatum";

    public static final String[] TABLE_NOTES_COLUMNS = {_ID, NOTE_TITLE, NOTE_CONTENT, NOTE_PUBLISHEDDATE, NOTE_ADAPTEDDATE};

}
