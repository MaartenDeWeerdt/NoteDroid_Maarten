package be.ehb.hellonavigationdrawer.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import be.ehb.hellonavigationdrawer.R;
import be.ehb.hellonavigationdrawer.model.Note;
import be.ehb.hellonavigationdrawer.model.NoteDAO;
import be.ehb.hellonavigationdrawer.util.NoteAdapter;


public class NoteFragment extends Fragment {

    private EditText etTitel, etInhoud;
    private Button btnSave;

    private Boolean nieuw;
    private Note oldNote;
    private Note newNote = new Note();

    public NoteFragment() {
    }

    public static NoteFragment newInstance() {
        NoteFragment fragment = new NoteFragment();

        return fragment;
    }

    public static Fragment newInstance(Note editNote) {

        NoteFragment noteFragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putSerializable("note",editNote);
        noteFragment.setArguments(args);

        return noteFragment;
    }

    private View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(nieuw == false)
            {
                oldNote.setTitel(etTitel.getText().toString());
                oldNote.setInhoud(etInhoud.getText().toString());
                NoteDAO.INSTANCE.updateNote(oldNote);
            }
            else
            {
                newNote.setTitel(etTitel.getText().toString());
                newNote.setInhoud(etInhoud.getText().toString());
                NoteDAO.INSTANCE.addNote(newNote);
            }

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, MainFragment.newInstance()).commit();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
            oldNote = (Note) getArguments().getSerializable("note");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_note, container, false);

        etTitel = (EditText) rootView.findViewById(R.id.et_detail_titel);
        etInhoud = (EditText) rootView.findViewById(R.id.et_detail_inhoud);
        btnSave = (Button) rootView.findViewById(R.id.btn_detail_save);

        btnSave.setOnClickListener(saveListener);



        if(oldNote != null)
        {
            etTitel.setText(oldNote.getTitel());
            etInhoud.setText(oldNote.getInhoud());
            nieuw = false;
        }
        else
        {
            nieuw = true;
        }

        return rootView;
    }
}
