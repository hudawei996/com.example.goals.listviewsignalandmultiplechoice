package com.example.goals.listviewsignalandmultiplechoice.recyclerViewMultiSelect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.goals.listviewsignalandmultiplechoice.R;

import java.util.ArrayList;

public class MultiSelectRVActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;

    MultiSelectRecyclerViewAdapter multiSelectRecyclerViewAdapter;
    ArrayList<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_select_rv);

        mRecyclerView = findViewById(R.id.recyclerView);

        initData();
        multiSelectRecyclerViewAdapter = new MultiSelectRecyclerViewAdapter(mData, this,
                new MultiSelectRecyclerViewAdapter.ViewHolder.ClickListener() {
                    @Override
                    public void onItemClicked(int position) {
                        multiSelectRecyclerViewAdapter.switchSelectedState(position);

                    }

                    @Override
                    public boolean onItemLongClicked(int position) {
                        Toast.makeText(MultiSelectRVActivity.this, "长按了" + position, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(multiSelectRecyclerViewAdapter);

    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            mData.add(i + "");
        }
    }
}
