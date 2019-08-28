package com.mind.memory.Model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class IsConnected extends AppCompatActivity {

    public Boolean hasInternet;
    public Boolean haveInternetConnection() {
        NetworkInfo network =((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (network==null || !network.isConnected())
        {
            hasInternet=false;
        }
        else
        {
            hasInternet=true;
        }
        return hasInternet;
    }

}
