package com.example.goals.listviewsignalandmultiplechoice.recyclerViewMultiSelect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.goals.listviewsignalandmultiplechoice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * User: huyongqiang(goals1989@gmail.com)
 * Date: 2018-12-11
 * Time: 08:49
 */
@SuppressWarnings("unused")
public class MultiSelectRecyclerViewAdapter extends RecyclerView.Adapter {

    private SparseBooleanArray selectedItems;
    private ArrayList<String> mArrayList;
    @SuppressWarnings("FieldCanBeLocal")
    private Context context;
    private ViewHolder.ClickListener clickListener;

    public MultiSelectRecyclerViewAdapter(ArrayList<String> mArrayList, Context context, ViewHolder.ClickListener clickListener) {
        this.mArrayList = mArrayList;
        this.context = context;
        this.clickListener = clickListener;
        this.selectedItems = new SparseBooleanArray();
    }

    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }

    public void switchSelectedState(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    public void clearSelectedState() {
        List<Integer> selection = getSelectedItems();
        selectedItems.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); ++i) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multi_selected, parent, false);
        return new ViewHolder(itemLayoutView, clickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).mLinearLayout.setBackgroundColor(isSelected(position) ? Color.DKGRAY : Color.LTGRAY);

        ((ViewHolder) holder).tvName.setText(mArrayList.get(position));
        ((ViewHolder) holder).tvName.setTextColor(isSelected(position) ? Color.RED : Color.BLUE);

        ((ViewHolder) holder).mCheckBox.setChecked(isSelected(position));
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public LinearLayout mLinearLayout;
        public TextView tvName;
        public CheckBox mCheckBox;

        private ClickListener listener;

        public ViewHolder(View itemLayoutView, ClickListener listener) {
            super(itemLayoutView);
            this.listener = listener;

            mLinearLayout = itemLayoutView.findViewById(R.id.linear_layout);
            tvName = itemLayoutView.findViewById(R.id.tvName);
            mCheckBox = itemLayoutView.findViewById(R.id.checkbox);

            itemLayoutView.setOnClickListener(this);
            itemLayoutView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClicked(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            return listener != null && listener.onItemLongClicked(getAdapterPosition());
        }

        public interface ClickListener {
            void onItemClicked(int position);

            boolean onItemLongClicked(int position);
        }
    }
}
