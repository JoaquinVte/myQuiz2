package com.jovialsa.myquiz2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FourthFragment extends Fragment {

    private static final String TOTAL = "TOTAL";
    private static final String ACTUAL = "ACTUAL";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fourth, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button_fourth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = getArguments();

                int preguntaNumero = bundle.getInt(ACTUAL);
                int preguntasTotales = bundle.getInt(TOTAL);

                if(preguntaNumero<preguntasTotales){

                    bundle.putInt(ACTUAL,preguntaNumero+1);

                    NavHostFragment.findNavController(FourthFragment.this)
                            .navigate(R.id.action_FourthFragment_to_ThirdFragment,bundle);
                }else{
                    NavHostFragment.findNavController(FourthFragment.this)
                            .navigate(R.id.action_FourthFragment_to_FinishFragment, bundle);
                }

            }
        });
    }
}
