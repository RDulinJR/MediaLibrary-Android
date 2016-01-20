package com.rdulinjr.medialibrary;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class DisplayCollection extends Fragment implements View.OnClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PAGE = "ARG_PAGE";
    private ListView libraryListView;
    private LibraryDBHandler myDB;
    private int mPage;
    private MovieRowAdapter libraryAdapter;

    public DisplayCollection() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     **/
    public static DisplayCollection newInstance(int page) {
        DisplayCollection fragment = new DisplayCollection();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_collection, container, false);
        libraryListView = (ListView) view.findViewById(R.id.librarylistview);
        libraryListView.setAdapter(libraryAdapter);
        libraryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putLong("id", id);
                Intent intent = new Intent(getActivity(), DisplayMedia.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        Button addButton = (Button) view.findViewById(R.id.add_button);
        addButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_button: {
                // intent to start add movie activity
                Intent addIntent = new Intent(getActivity(), AddMediaActivity.class);
                startActivity(addIntent);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myDB = new LibraryDBHandler(context);
        libraryAdapter = new MovieRowAdapter(context, myDB.getAllMovies(), 0);
    }
}
