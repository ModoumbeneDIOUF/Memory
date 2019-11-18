package com.mind.memory;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.mind.memory.Adapter.GriidViewdapter;
import com.mind.memory.Model.ListNourritureOffert;
import com.mind.memory.Retrof.DataRetrieve;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NourritureOffertActivity extends AppCompatActivity {


    /*adapter*/


    /*http client*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nourriture_offert);

        GridView listView = findViewById(R.id.gridNourritureOffert);

        new DataRetrieve(NourritureOffertActivity.this).retrieve(listView);
    }

}
