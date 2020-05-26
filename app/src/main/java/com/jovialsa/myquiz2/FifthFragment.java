package com.jovialsa.myquiz2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class FifthFragment extends Fragment {

    private static final String TOTAL = "TOTAL";
    private static final String ACTUAL = "ACTUAL";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fifth, container, false);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_fifth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getArguments();

                int preguntaNumero = bundle.getInt(ACTUAL);
                int preguntasTotales = bundle.getInt(TOTAL);

                if(preguntaNumero<preguntasTotales){

                    bundle.putInt(ACTUAL,preguntaNumero+1);

                    NavHostFragment.findNavController(FifthFragment.this)
                            .navigate(R.id.action_FifthFragment_to_ThirdFragment, bundle);
                }else{
                    NavHostFragment.findNavController(FifthFragment.this)
                            .navigate(R.id.action_FifthFragment_to_FinishFragment, bundle);
                }
            }
        });
    }
}
