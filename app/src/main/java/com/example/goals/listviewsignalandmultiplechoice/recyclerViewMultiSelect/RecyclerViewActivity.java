package com.example.goals.listviewsignalandmultiplechoice.recyclerViewMultiSelect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.goals.listviewsignalandmultiplechoice.R;
import com.example.goals.listviewsignalandmultiplechoice.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity implements ActionMode.Callback {
    private ActionMode actionMode;
    private boolean isMultiSelect = false;
    //i created List of int type to store id of data, you can create custom class type data according to your need.
    private List<Integer> selectedIds = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        RecyclerView recyclerView = findViewById(R.id.widget_list);
        adapter = new MyAdapter(this, getList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (isMultiSelect) {
                            //if multiple selection is enabled then select item on single click else perform normal click on item.
                            multiSelect(position);
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        if (!isMultiSelect) {
                            selectedIds = new ArrayList<>();
                            isMultiSelect = true;

                            if (actionMode == null) {
                                actionMode = startActionMode(RecyclerViewActivity.this); //show ActionMode.
                            }
                        }

                        multiSelect(position);
                    }
                }));
    }

    private void multiSelect(int position) {
        MyData data = adapter.getItem(position);
        if (data != null) {
            if (actionMode != null) {
                if (selectedIds.contains(data.getId()))
                    selectedIds.remove(Integer.valueOf(data.getId()));
                else
                    selectedIds.add(data.getId());

                if (selectedIds.size() > 0)
                    actionMode.setTitle(String.valueOf(selectedIds.size())); //show selected item count on action mode.
                else {
                    actionMode.setTitle(""); //remove item count from action mode.
                    actionMode.finish(); //hide action mode.
                }
                adapter.setSelectedIds(selectedIds);

            }
        }
    }

    /**
     * @return list
     * @see MyData Create dummy List of type MyData.
     */
    private List<MyData> getList() {
        List<MyData> list = new ArrayList<>();
        list.add(new MyData(1, "GridView"));
        list.add(new MyData(2, "Switch"));
        list.add(new MyData(3, "SeekBar"));
        list.add(new MyData(4, "EditText"));
        list.add(new MyData(5, "ToggleButton"));
        list.add(new MyData(6, "ProgressBar"));
        list.add(new MyData(7, "ListView"));
        list.add(new MyData(8, "RecyclerView"));
        list.add(new MyData(9, "ImageView"));
        list.add(new MyData(10, "TextView"));
        list.add(new MyData(11, "Button"));
        list.add(new MyData(12, "ImageButton"));
        list.add(new MyData(13, "Spinner"));
        list.add(new MyData(14, "CheckBox"));
        list.add(new MyData(15, "RadioButton"));
        return list;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_select, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_delete:
                //just to show selected items.
                StringBuilder stringBuilder = new StringBuilder();
                for (MyData data : getList()) {
                    if (selectedIds.contains(data.getId()))
                        stringBuilder.append("\n").append(data.getTitle());
                }
                Toast.makeText(this, "Selected items are :" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
        isMultiSelect = false;
        selectedIds = new ArrayList<>();
        adapter.setSelectedIds(new ArrayList<Integer>());
    }
}
