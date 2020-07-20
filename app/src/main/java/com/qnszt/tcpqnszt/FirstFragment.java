package com.qnszt.tcpqnszt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_startMeasurement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean nameFilled = MainActivity.mainActivity.findViewById(R.id.txt_measurementName) != null && ((TextView)MainActivity.mainActivity.findViewById(R.id.txt_measurementName)).getText().length() > 0;
                Boolean durationFilled = MainActivity.mainActivity.findViewById(R.id.txt_measurementFrequency) != null && ((TextView)MainActivity.mainActivity.findViewById(R.id.txt_measurementFrequency)).getText().length() > 0;
                Boolean frequencyFilled = MainActivity.mainActivity.findViewById(R.id.txt_measurementFrequency) != null && ((TextView)MainActivity.mainActivity.findViewById(R.id.txt_measurementFrequency)).getText().length() > 0;
                if( nameFilled && durationFilled ) {
                    MainActivity.mainActivity.startMeasurementClicked();
                }else{
                    Snackbar snackbar = Snackbar
                            .make(MainActivity.mainActivity.findViewById(R.id.layout), "Mérés neve vagy ideje nincs kitöltve!", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.colorAlert));
                    snackbar.show();
                }
            }
        });

        view.findViewById(R.id.chk_testerLed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClientWorker.broadcast(String.format("led%s", ((CheckBox) view.findViewById(R.id.chk_testerLed)).isChecked() ? "1" : "0"));
            }
        });
    }
}