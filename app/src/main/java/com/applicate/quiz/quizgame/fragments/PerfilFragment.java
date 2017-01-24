package com.applicate.quiz.quizgame.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applicate.quiz.quizgame.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

//Esto sería un fragmento limpio, si necesitamos añadirle callbacks y demás lo haremos a mano.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false);
        //Si queremos usar la View para inflar ciertos elementos usaremos:
        //View v = inflater.inflate(R.layout.fragment_perfil, container, false);
        //return v;
        //En vez de retornar solo el inflater.

    }

}
