package com.example.thiago.casainteligente;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.UnsupportedEncodingException;


public class ClientSubscribe extends Activity implements MqttCallback {

    private static final String TAG = "MAIN";
    public static final String TEXTO_POSITIVO = "DETECTADO";
    public static final String TEXTO_NEGATIVO = "NORMAL";
    public static final int STATUS_POSITIVO = 1;
    String broker = "tcp://m13.cloudmqtt.com:18768";
    String clientId = "aplicativo";
    String username = "mdzjtvif";
    String password = "snX5gG5TJs8P";
    MemoryPersistence persistence = new MemoryPersistence();
    Context context;

    int idSituacao;
    int idContador;

    public ClientSubscribe(Context context, int idSituacao, int idContador)
    {
        this.context = context;
        this.idSituacao = idSituacao;
        this.idContador = idContador;
    }

    public void Subscribe(String status, String refresh)
    {
        try
        {
            int qos = 1;
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());
            connOpts.setCleanSession(true);
            sampleClient.connect(connOpts);
            sampleClient.setCallback(this);
            sampleClient.subscribe(status, qos);

            // Solicita verificacao do status ao broker
            MqttMessage message = new MqttMessage("1".getBytes("UTF-8"));
            sampleClient.publish(refresh, message);
            Log.d(TAG, "enviado ");
        }
        catch (MqttException e)
        {
            Log.d(TAG, "excep "  + e);
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        Log.d(TAG, "Conexão perdida.");
    }

    @Override
    public void messageArrived(String topic, final MqttMessage message) throws Exception {

        Log.d(TAG, message.toString());
        System.out.print("Topico: ");
        System.out.println(topic);
        System.out.print("Recebido: ");
        System.out.println(message.toString());

        runOnUiThread(new Runnable() {

            public void run() {
                TextView situacao = (TextView) ((Activity) context).findViewById(idSituacao);
                TextView contador = (TextView) ((Activity) context).findViewById(idContador);
                String[] parts = message.toString().split("/");
                String status = parts[0];
                String count = parts[1];
                if (Integer.parseInt(status) == STATUS_POSITIVO)
                {
                    situacao.setText(TEXTO_POSITIVO);
                    situacao.setTextColor(Color.RED);
                    contador.setText(count);
                }
                else
                {
                    situacao.setText(TEXTO_NEGATIVO);
                    situacao.setTextColor(Color.GREEN);
                    contador.setText(count);
                }
            }
        });
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
