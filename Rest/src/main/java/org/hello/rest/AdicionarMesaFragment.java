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
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.Parse;
import com.parse.ParseObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AdicionarMesaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AdicionarMesaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdicionarMesaFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public AdicionarMesaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AdicionarMesaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdicionarMesaFragment newInstance() {
        AdicionarMesaFragment fragment = new AdicionarMesaFragment();
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
        View view = inflater.inflate(R.layout.fragment_adicionar_mesa, container, false);
        // Inflate the layout for this fragment
        final EditText txtNome = (EditText)view.findViewById(R.id.txtMesaNome);
        final Spinner curso = (Spinner)view.findViewById(R.id.spinnerCurso);
        final EditText funcao = (EditText)view.findViewById(R.id.txtMesaFuncao);
        final EditText matricula = (EditText)view.findViewById(R.id.txtMesaMatricula);

        Button btn = (Button)view.findViewById(R.id.btnNovoMesa);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject mesa = new ParseObject("Mesa");
                mesa.put("Nome", txtNome.getText().toString());
                mesa.put("Curso", curso.getSelectedItem().toString());
                mesa.put("Funcao", funcao.getText().toString());
                mesa.put("Matricula", matricula.getText().toString());
                try {
                    mesa.save();
                    FragmentManager manager = getFragmentManager();
                    manager.beginTransaction().replace(R.id.container,MesaFragment.newInstance()).commit();
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
