package com.example.thiago.casainteligente;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Garagem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garagem);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        new ClientSubscribe(Garagem.this, R.id.statusDistancia, R.id.contadorDistancia).Subscribe("casa/distancia/status", "casa/distancia/refresh");
    }
}
