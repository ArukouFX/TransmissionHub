package com.example.transmissionhub;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private final MutableLiveData<String> resultadoLiveData = new MutableLiveData<>();

    public LiveData<String> getResultadoLiveData() {
        return resultadoLiveData;
    }

    public void enviarTransmision() {
        resultadoLiveData.setValue("Transmisión con LiveData recibida");
    }
}
