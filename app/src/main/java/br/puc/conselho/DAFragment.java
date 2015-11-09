package br.puc.conselho;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DAFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DAFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DAFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment DAFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DAFragment newInstance() {
        DAFragment fragment = new DAFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public DAFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_da, container, false);
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
            mListener.onDAInteraction(uri);
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
        public void onDAInteraction(Uri uri);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DA");
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
            faculdade.add("Curso: " + p.getString("Curso"));
            faculdade.add("Prédio: " + String.valueOf(p.getInt("Predio")));
            faculdade.add("Telefone: " + String.valueOf(p.getInt("Telefone")));

            listDataChild.put(listDataHeader.get(i), faculdade);
            i++;
        }
    }
}
