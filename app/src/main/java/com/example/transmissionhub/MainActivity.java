package com.example.transmissionhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView resultadoTextView;
    private BroadcastReceiver customBroadcastReceiver;
    private BroadcastReceiver orderedBroadcastReceiver;
    private MyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultadoTextView = findViewById(R.id.label_resultado);

        // Botón 1: enviar transmisión con sendBroadcast
        findViewById(R.id.button_send_broadcast).setOnClickListener(v -> {
            Intent intent = new Intent("com.example.PEDIDO_ACTUALIZADO");
            sendBroadcast(intent);
        });

        // Botón para transmitir de forma ordenada
        findViewById(R.id.button_send_ordered_broadcast).setOnClickListener(v -> {
            Intent intent = new Intent("com.example.ORDERED_BROADCAST");
            sendOrderedBroadcast(intent, null);
        });

        // Botón para enviar y recibir
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        viewModel.getResultadoLiveData().observe(this, resultado -> {
            resultadoTextView.setText(resultado);
        });

        findViewById(R.id.button_send_receive_livedata).setOnClickListener(v -> {
            viewModel.enviarTransmision();
        });

        // Receptor principal
        customBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ("com.example.PERSONALIZADA".equals(intent.getAction())) {
                    resultadoTextView.setText("Recepción de transmisión customizada");
                }
            }
        };

        IntentFilter filter = new IntentFilter("com.example.PERSONALIZADA");
        registerReceiver(customBroadcastReceiver, filter);

        findViewById(R.id.button_send_broadcast).setOnClickListener(v -> {
            Intent intent = new Intent("com.example.PERSONALIZADA");
            sendBroadcast(intent);
        });

        // Receptor ordenado
        orderedBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ("com.example.ORDERED_BROADCAST".equals(intent.getAction())) {
                    resultadoTextView.setText("Recepción de transmisión ordenada");
                }
            }
        };

        IntentFilter filterOrdered = new IntentFilter("com.example.ORDERED_BROADCAST");
        registerReceiver(orderedBroadcastReceiver, filterOrdered);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(customBroadcastReceiver);
        unregisterReceiver(orderedBroadcastReceiver); //Remover segundo receptor
    }
}

