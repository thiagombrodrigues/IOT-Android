package com.example.thiago.casainteligente;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Movimento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimento);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        new ClientSubscribe(Movimento.this, R.id.statusMovimento, R.id.contadorMovimento).Subscribe("casa/movimento/status", "casa/movimento/refresh");
    }
}
