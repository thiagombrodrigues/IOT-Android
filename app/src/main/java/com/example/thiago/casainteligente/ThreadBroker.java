package com.example.thiago.casainteligente;

import android.content.Context;

public class ThreadBroker implements Runnable
{
    private Context context;
    private int idSituacao;
    private int idContador;
    private String status;
    private String refresh;

    public ThreadBroker(Context context, int idSituacao, int idContador, String status, String refresh)
    {
        this.context = context;
        this.idSituacao = idSituacao;
        this.idContador = idContador;
        this.status = status;
        this.refresh = refresh;
    }

    public void run ()
    {
        new ClientSubscribe(this.context, this.idSituacao, this.idContador).Subscribe(status, refresh);

    }
}
