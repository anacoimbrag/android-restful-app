package br.puc.conselho;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FaculdadeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FaculdadeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FaculdadeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FaculdadeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FaculdadeFragment newInstance() {
        FaculdadeFragment fragment = new FaculdadeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FaculdadeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_faculdade, container, false);
        // get the listview
        expListView = (ExpandableListView)view.findViewById(R.id.expListView);

        prepareListData();
        // setting list adapter
        listAdapter = new ExpandableListAdapter(view.getContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFaculdadeInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
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
        public void onFaculdadeInteraction(Uri uri);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Faculdade");
        List<ParseObject> result = new ArrayList<>();
        listDataChild = new HashMap<String, List<String>>();
        try {
            result = query.find();
        }catch (ParseException e){
            Log.e("ERRO", e.getMessage());
        }

        int i = 0;
        for(ParseObject p : result) {
            listDataHeader.add(p.getString("Nome"));
            List<String> faculdade = new ArrayList<String>();
            faculdade.add(p.getString("Campus"));
            faculdade.add(p.getString("Endereco"));

            listDataChild.put(listDataHeader.get(i), faculdade);
            i++;
        }
    }
}
