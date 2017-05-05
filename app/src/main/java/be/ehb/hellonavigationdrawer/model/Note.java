package be.ehb.hellonavigationdrawer.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Maarten De Weerdt on 27/04/2017.
 */

public class Note implements Serializable {
    String titel, inhoud;
    int id;
    Date aanmaakDatum, gewijzigdDatum;

    public Note() {
        this.aanmaakDatum = new Date();
        this.gewijzigdDatum = new Date();
    }

    public Note(String titel, String inhoud) {
        this.titel = titel;
        this.inhoud = inhoud;
    }

    public Note(String titel, String inhoud, Date aanmaakDatum) {
        super();
        this.titel = titel;
        this.inhoud = inhoud;
        this.aanmaakDatum = aanmaakDatum;
    }

    public Note(String titel, String inhoud, int id, Date aanmaakDatum, Date gewijzigdDatum) {
        this.titel = titel;
        this.inhoud = inhoud;
        this.id = id;
        this.aanmaakDatum = aanmaakDatum;
        this.gewijzigdDatum = gewijzigdDatum;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getInhoud() {
        return inhoud;
    }

    public void setInhoud(String inhoud) {
        this.inhoud = inhoud;
    }

    public Date getAanmaakDatum() {
        return aanmaakDatum;
    }

    public void setAanmaakDatum(Date aanmaakDatum) {
        this.aanmaakDatum = aanmaakDatum;
    }

    public Date getGewijzigdDatum() {
        return gewijzigdDatum;
    }

    public void setGewijzigdDatum(Date gewijzigdDatum) {
        this.gewijzigdDatum = gewijzigdDatum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Note{" +
                "titel='" + titel + '\'' +
                ", inhoud='" + inhoud + '\'' +
                ", aanmaakDatum='" + aanmaakDatum + '\'' +
                ", gewijzigdDatum='" + gewijzigdDatum + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        return id == note.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public static Comparator<Note> TitelsOplopendComparator = new Comparator<Note>() {
        @Override
        public int compare(Note o1, Note o2) {
            String noteTitle1 = o1.getTitel().toUpperCase();
            String noteTitle2 = o2.getTitel().toUpperCase();

            return noteTitle1.compareTo(noteTitle2);
        }
    };

    public static Comparator<Note> DatumsOplopendComparator = new Comparator<Note>() {
        @Override
        public int compare(Note o1, Note o2) {
            String noteDate1 = o1.getGewijzigdDatum().toString();
            String noteDate2 = o2.getGewijzigdDatum().toString();

            return noteDate1.compareTo(noteDate2);
        }
    };

    public static Comparator<Note> DatumsAflopendComparator = new Comparator<Note>() {
        @Override
        public int compare(Note o1, Note o2) {
            String noteDate1 = o1.getGewijzigdDatum().toString();
            String noteDate2 = o2.getGewijzigdDatum().toString();

            return noteDate2.compareTo(noteDate1);
        }
    };
}
