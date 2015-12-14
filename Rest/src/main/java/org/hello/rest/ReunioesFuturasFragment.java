package org.hello.rest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReunioesFuturasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReunioesFuturasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReunioesFuturasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ReunioesFuturasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReunioesFuturasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReunioesFuturasFragment newInstance(String param1, String param2) {
        ReunioesFuturasFragment fragment = new ReunioesFuturasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reunioes_futuras, container, false);
        ExpandableListView expandableListView = (ExpandableListView)view.findViewById(R.id.listaReunioes);
        ArrayList<Object> data = new ArrayList<>();
        ArrayList<String> group = new ArrayList<>();
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Reuniao");
            List<ParseObject> results = query.find();
            Date hoje = new Date();
            if (results.size() > 0) {
                for (final ParseObject o : results) {
                    Log.d("Reuniao", o.getDate("Data").toString());
                    if (o.getDate("Data").compareTo(hoje) >= 0) {
                        group.add(o.getString("Assunto"));
                        ArrayList<Object> tmp = new ArrayList<>();
                        tmp.add("Data: " + o.getDate("Data").toLocaleString());
                        data.add(tmp);
                    }
                }
                MyExpandableAdapter adapter = new MyExpandableAdapter(group, data);
                adapter.setInflater((LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
                expandableListView.setAdapter(adapter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
