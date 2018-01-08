package com.sanjin.recyclerviewholder.commonadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.sanjin.recyclerviewholder.R;
import com.sanjin.recyclerviewholder.commonadapter.CommonAdapter;
import com.sanjin.recyclerviewholder.commonadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CommonAdapterActivity extends AppCompatActivity {

    private List<String> mDatas = new ArrayList<String>(){{add("1");add("2");}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_adapter);

        // set recyclerview
        RecyclerView recyclerview = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);

        // set adapter for recyclerview by commonadapter
        recyclerview.setAdapter(new CommonAdapter<String>(this, R.layout.item_common_adapter, mDatas) {
            @Override
            public void convert(ViewHolder holder, String s) {
                //TextView textView = holder.getView(R.id.textview);
                //textView.setText(s);
                holder.setText(R.id.textview,s);
            }
        });
    }
}
