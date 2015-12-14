package org.hello.rest;

import android.app.Activity;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parse.Parse;
import com.parse.ParseObject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AdicionarDAFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AdicionarDAFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdicionarDAFragment extends Fragment {
    View view;

    DA da;
    private OnFragmentInteractionListener mListener;

    public AdicionarDAFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AdicionarDAFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdicionarDAFragment newInstance() {
        AdicionarDAFragment fragment = new AdicionarDAFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_adicionar_da, container, false);
        // Inflate the layout for this fragment
        // Enable Local Datastore.
        final EditText txtNome = (EditText)view.findViewById(R.id.txtDANome);
        final Spinner spinnerCurso = (Spinner)view.findViewById(R.id.spinnerCurso);
        final EditText txtEmail = (EditText)view.findViewById(R.id.txtDAEmail);
        final Spinner spinnerPredio = (Spinner)view.findViewById(R.id.spinnerPredio);
        final EditText txtTelefone = (EditText)view.findViewById(R.id.txtDATelefone);
        Parse.enableLocalDatastore(this.getActivity());

        Button btnAdicionar = (Button)view.findViewById(R.id.btnNovoDA);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseObject gameScore = new ParseObject("DA");
                gameScore.put("Nome", txtNome.getText().toString());
                gameScore.put("Curso", spinnerCurso.getSelectedItem().toString());
                gameScore.put("Email", txtEmail.getText().toString());
                gameScore.put("Predio", Integer.parseInt(spinnerPredio.getSelectedItem().toString()));
                gameScore.put("Situacao", true);
                gameScore.put("Telefone", Integer.parseInt(txtTelefone.getText().toString()));
                try {
                    gameScore.save();
                    FragmentManager manager = getFragmentManager();
                    manager.beginTransaction().replace(R.id.container,DaFragment.newInstance()).commit();
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    private class PostMessageTask extends AsyncTask<Void, Void, String> {
        DA da;

        @Override
        protected void onPreExecute() {
            da = new DA();
            EditText txtNome = (EditText)view.findViewById(R.id.txtDANome);
            Spinner spinnerCurso = (Spinner)view.findViewById(R.id.spinnerCurso);
            EditText txtEmail = (EditText)view.findViewById(R.id.txtDAEmail);
            Spinner spinnerPredio = (Spinner)view.findViewById(R.id.spinnerPredio);
            EditText txtTelefone = (EditText)view.findViewById(R.id.txtDATelefone);
            da.setNome(txtNome.getText().toString());
            da.setCurso(spinnerCurso.getSelectedItem().toString());
            da.setEmail(txtEmail.getText().toString());
            da.setPredio(Integer.parseInt(spinnerPredio.getSelectedItem().toString()));
            da.setSituacao(true);
            da.setTelefone(Integer.parseInt(txtTelefone.getText().toString()));
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                final String url = "http://192.168.0.42:8080/da";
                HttpHeaders requestHeaders = new HttpHeaders();

                // Sending multipart/form-data
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);

                // Populate the MultiValueMap being serialized and headers in an HttpEntity object to use for the request
                HttpEntity<?> requestEntity = new HttpEntity<Object>(da, requestHeaders);

                // Create a new RestTemplate instance
                RestTemplate restTemplate = new RestTemplate(true);
                List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
                messageConverters.add(new FormHttpMessageConverter());
                messageConverters.add(new StringHttpMessageConverter());
                messageConverters.add(new MappingJacksonHttpMessageConverter());
                restTemplate.setMessageConverters(messageConverters);

                // Make the network request, posting the message, expecting a String in response from the server
//                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

                DA response  = restTemplate.postForObject(url, requestEntity, DA.class);
                // Return the response body to display to the user
                return "Adicionado Com Sucesso";
            } catch (Exception e) {
                Log.e("Principal", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(view.getContext(),s,Toast.LENGTH_SHORT).show();
        }

    }
}
