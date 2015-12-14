package org.hello.rest;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View view;

    ExpandableListView expandableListView;
    ArrayList<String> group;
    ArrayList<Object> data;
    MyExpandableAdapter adapter;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment DaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DaFragment newInstance() {
        DaFragment fragment = new DaFragment();
        return fragment;
    }

    public DaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        new HttpRequestTask().execute();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_da, container, false);
        // Inflate the layout for this fragment

        ImageButton btnNovo = (ImageButton)view.findViewById(R.id.newDa);
        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Click","Entrou");
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.container,AdicionarDAFragment.newInstance()).commit();
            }
        });
        expandableListView = (ExpandableListView)view.findViewById(R.id.listDAs);
        List<DA> das = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("DA");
        List<ParseObject> results = null;
        try {
            results = query.find();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        for (ParseObject obj : results) {
            das.add(new DA(obj.getObjectId(), obj.getString("Nome"),obj.getString("Curso"),obj.getString("Email"),obj.getInt("Predio"),obj.getBoolean("Situacao"), obj.getInt("Telefone")));
        }

        data = new ArrayList<>();
        group = new ArrayList<>();

        for(DA d : das){
            group.add(d.getNome());
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add("Curso " + d.getCurso() + " - Predio " + String.valueOf(d.getPredio()));
            tmp.add("Email " + d.getEmail());
            tmp.add("Telefone " + String.valueOf(d.getTelefone()));
            data.add(tmp);
        }
        if(view != null) {
            adapter = new MyExpandableAdapter(group, data);
            adapter.setInflater((LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE), DaFragment.this);
            expandableListView.setAdapter(adapter);
        }else{
            Log.d(getTag(),"View is null");
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
        public void onFragmentInteraction(Uri uri);
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, DA[]> {
        @Override
        protected DA[] doInBackground(Void... params) {
            try {
                final String url = "http://192.168.0.42:8080/da";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<DA[]> responseEntity = restTemplate.getForEntity(url,DA[].class);
                DA[] das = responseEntity.getBody();
                if(das != null) {
                    Log.d("Principal", "Funcionou:" + das.length);
                }
                return das;
            } catch (Exception e) {
                Log.e("Principal", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(DA[] das) {
            data = new ArrayList<>();
            group = new ArrayList<>();

            for(DA d : das){
                group.add(d.getNome());
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("Curso " + d.getCurso() + " - Predio " + String.valueOf(d.getPredio()));
                tmp.add("Email " + d.getEmail());
                tmp.add("Telefone " + String.valueOf(d.getTelefone()));
                data.add(tmp);
            }
            if(view != null) {
                adapter = new MyExpandableAdapter(group, data);
                adapter.setInflater((LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE), DaFragment.this);
                expandableListView.setAdapter(adapter);
            }else{
                Log.d(getTag(),"View is null");
            }
        }

    }
}
