package com.mind.memory;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;

import com.mind.memory.Model.Responsable;
import com.mind.memory.ViewHolder.ResponsableHelper;
import com.mind.memory.ViewHolder.ResponsableViewHolder;

import java.util.List;

public class ResponsableActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsable);

        recyclerView = findViewById(R.id.responsable_recycle);
        new ResponsableHelper().readResponsable(new ResponsableHelper.DataStatus() {
            @Override
            public void DataIsLoad(List<Responsable> rest, List<String> keys) {
                new ResponsableViewHolder().setConfig(recyclerView,ResponsableActivity.this,
                        rest,keys);
            }
        });


         }


}
