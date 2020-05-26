package com.jovialsa.myquiz2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class FinishFragment extends Fragment {

    private static final String ACIERTOS = "ACIERTOS";
    private static final String TOTAL = "TOTAL";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_finish, container, false);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textViewResults = (TextView) view.findViewById(R.id.resultadoFinal);

        Bundle bundle = getArguments();

        textViewResults.setText("Has acertado " + bundle.getInt(ACIERTOS) + "/" + bundle.getInt(TOTAL) + " preguntas.");

        view.findViewById(R.id.button_fifth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FinishFragment.this)
                        .navigate(R.id.action_FinishFragment_to_ThirdFragment);
            }
        });
    }
}
