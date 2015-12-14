package org.hello.rest;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MembroFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MembroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MembroFragment extends Fragment {
    View view;

    ExpandableListView expandableListView;
    ArrayList<String> group;
    ArrayList<Object> data;
    MyExpandableAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public MembroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MembroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MembroFragment newInstance() {
        MembroFragment fragment = new MembroFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        new HttpRequestTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_membro, container, false);
        // Inflate the layout for this fragment
        ImageButton btnNovo = (ImageButton)view.findViewById(R.id.newDa);
        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Click", "Entrou");
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.container,AdicionarMembroFragment.newInstance()).commit();
            }
        });
        expandableListView = (ExpandableListView)view.findViewById(R.id.listMembro);

        List<Membro> das = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Membro");
        List<ParseObject> results = null;
        try {
            results = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (ParseObject obj : results) {
            das.add(new Membro(obj.getObjectId(), obj.getNumber("Matricula").toString(), obj.getString("Nome"), obj.getString("DA"), obj.getString("Funcao")));
        }

        data = new ArrayList<>();
        group = new ArrayList<>();

        for(Membro d : das){
            group.add(d.getNome());
            ArrayList<String> tmp = new ArrayList<>();
            tmp.add("DA " + d.getDA() + " - Função " + d.getFuncao());
            tmp.add("Matricula " + d.getMatricula());
            data.add(tmp);
        }
        if(view != null) {
            adapter = new MyExpandableAdapter(group, data);
            adapter.setInflater((LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE), MembroFragment.this);
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

    private class HttpRequestTask extends AsyncTask<Void, Void, Membro[]> {
        @Override
        protected Membro[] doInBackground(Void... params) {
            try {
                final String url = "http://192.168.0.42:8080/membro";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<Membro[]> responseEntity = restTemplate.getForEntity(url,Membro[].class);
                Membro[] das = responseEntity.getBody();
                if(das != null) {
                    Log.d("Principal", "Funcionou:" + das.length);
                }else{
                    Log.e("Principal", "DAs is null");
                }
                return das;
            } catch (Exception e) {
                Log.e("Principal", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Membro[] das) {
            data = new ArrayList<>();
            group = new ArrayList<>();

            for(Membro d : das){
                group.add(d.getNome());
                ArrayList<String> tmp = new ArrayList<>();
                tmp.add("DA " + d.getDA() + " - Função " + d.getFuncao());
                tmp.add("Matricula " + d.getMatricula());
                data.add(tmp);
            }
            if(view != null) {
                adapter = new MyExpandableAdapter(group, data);
                adapter.setInflater((LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE), MembroFragment.this);
                expandableListView.setAdapter(adapter);
            }else{
                Log.d(getTag(),"View is null");
            }
        }

    }
}
