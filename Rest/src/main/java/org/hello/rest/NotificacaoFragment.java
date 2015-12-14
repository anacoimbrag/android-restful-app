package org.hello.rest;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotificacaoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificacaoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificacaoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NotificacaoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NotificacaoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificacaoFragment newInstance() {
        NotificacaoFragment fragment = new NotificacaoFragment();
        Bundle args = new Bundle();
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
        final View view = inflater.inflate(R.layout.fragment_notificacao, container, false);
        Button btnAdicionar = (Button)view.findViewById(R.id.btnEnviar);
//        final EditText assunto = (EditText)view.findViewById(R.id.txtAssunto);
        final EditText msg = (EditText)view.findViewById(R.id.txtmsg);
        final CheckBox membros = (CheckBox)view.findViewById(R.id.membros);
        final CheckBox mesa = (CheckBox)view.findViewById(R.id.mesa);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                try {
                    ParsePush push = new ParsePush();
                    LinkedList<String> channels = new LinkedList<String>();
                    channels.add("Users");

                    if (membros.isChecked()) {
                        channels.add("Membros");
                    }
                    if (mesa.isChecked()) {
                        channels.add("Mesa");
                    }

                    push.setMessage(msg.getText().toString());
                    try {
                        push.send();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(view.getContext(), "Sua mensagem será enviada em breve.", Toast.LENGTH_LONG).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, Principal.PlaceholderFragment.newInstance(1))
                            .commit();
                }catch (Exception e){
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
