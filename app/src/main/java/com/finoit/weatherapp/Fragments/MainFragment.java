package com.finoit.weatherapp.Fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.finoit.weatherapp.Adapters.MyCursorAdapter;
import com.finoit.weatherapp.R;

public class MainFragment extends Fragment {
    ListView list;
    MyCursorAdapter myadapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        list = (ListView)view.findViewById(R.id.listview);
        return view;
    }

    public void setAdaptertoList(Cursor cursor){
        myadapter = new MyCursorAdapter(getActivity(),cursor);
        list.setAdapter(myadapter);
    }
}
