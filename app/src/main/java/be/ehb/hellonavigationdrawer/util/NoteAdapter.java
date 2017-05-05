package be.ehb.hellonavigationdrawer.util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import be.ehb.hellonavigationdrawer.R;
import be.ehb.hellonavigationdrawer.model.Note;

/**
 * Created by Maarten De Weerdt on 27/04/2017.
 */

public class NoteAdapter extends BaseAdapter implements Filterable{
    private SharedPreferences sharedPreferences;

    private class Viewholder
    {
        public TextView tvRowTitle;
        public TextView tvRowDate;
    }

    private Viewholder holder;
    private ArrayList<Note> notities;
    private ArrayList<Note> filterNotities;
    private Activity context;

    public NoteAdapter(Activity context, ArrayList<Note> notities) {
        //layoutinflater toevoegen
        this.context = context;
        this.notities = notities;
        filterNotities = notities;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);


        switch (sharedPreferences.getString("pref_sorteermethode", "Titels oplopend"))
        {
            case "Titels oplopend":
                Collections.sort(filterNotities, Note.TitelsOplopendComparator);
                break;
            case "Aanmaakdatums oplopend":
                Collections.sort(filterNotities, Note.DatumsOplopendComparator);
                break;
            case "Aanmaakdatums aflopend":
                Collections.sort(filterNotities, Note.DatumsAflopendComparator);
                break;
        }

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return filterNotities.size();
    }

    @Override
    public Object getItem(int position) {
        return filterNotities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return filterNotities.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            convertView = context.getLayoutInflater().inflate(R.layout.note_row, parent, false);

            holder = new Viewholder();

            holder.tvRowTitle = (TextView) convertView.findViewById(R.id.tv_row_title);
            holder.tvRowDate = (TextView) convertView.findViewById(R.id.tv_row_date);

            convertView.setTag(holder);
        }
        else
        {
            holder = (Viewholder) convertView.getTag();
        }

        Note l = filterNotities.get(position);
        holder.tvRowTitle.setText(l.getTitel());
        holder.tvRowDate.setText(l.getGewijzigdDatum().toString());

        return convertView;
    }

    public void remove(Note n) {
        filterNotities.remove(n);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                filterNotities = (ArrayList<Note>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {


                FilterResults results = new FilterResults();
                ArrayList<Note> filteredArrayNames = new ArrayList<Note>();

                if(constraint.length() == 0)
                {
                    filteredArrayNames = notities;
                }
                else {
                    for (Note n : notities) {
                        if (n.getTitel().contains(constraint))
                            filteredArrayNames.add(n);
                    }
                }
                results.count = filteredArrayNames.size();
                results.values = filteredArrayNames;

                return results;
            }
        };

        return filter;
    }
}
