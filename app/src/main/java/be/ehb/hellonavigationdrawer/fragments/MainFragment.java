package be.ehb.hellonavigationdrawer.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import be.ehb.hellonavigationdrawer.R;
import be.ehb.hellonavigationdrawer.model.Note;
import be.ehb.hellonavigationdrawer.model.NoteDAO;
import be.ehb.hellonavigationdrawer.util.NoteAdapter;


public class MainFragment extends Fragment {

    private NoteAdapter mAdapter;

    private ListView lvNotities;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        lvNotities = (ListView) rootView.findViewById(R.id.lv_notes);

        mAdapter = new NoteAdapter(getActivity(), NoteDAO.INSTANCE.getAllNotes());
        lvNotities.setAdapter(mAdapter);


        lvNotities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, NoteFragment.newInstance((Note) mAdapter.getItem(position))).commit();
            }
        });

        registerForContextMenu(lvNotities);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

        menuInflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.mi_search);
        MenuItem addItem = menu.findItem(R.id.mi_add);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });

        addItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, NoteFragment.newInstance()).commit();
                return true;
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mi = getActivity().getMenuInflater();
        mi.inflate(R.menu.menu_context_main, menu);

    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId())
        {
            case R.id.delete_item :
                Note n = (Note) mAdapter.getItem(info.position);
                //verwijder uit datasource
                NoteDAO.INSTANCE.deleteNote(n);
                //verwijder uit listview
                mAdapter.remove(n);
                mAdapter.notifyDataSetChanged();

                break;
        }
        return super.onContextItemSelected(item);
    }
}
