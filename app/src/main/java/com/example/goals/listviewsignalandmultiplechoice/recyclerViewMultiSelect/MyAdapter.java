package com.example.goals.listviewsignalandmultiplechoice.recyclerViewMultiSelect;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.goals.listviewsignalandmultiplechoice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2018/12/10.
 * Description:
 *
 * @author huyongqiang
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private List<MyData> list;
    private List<Integer> selectedIds = new ArrayList<>();

    public MyAdapter(Context context, List<MyData> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * 实例化item布局
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        int id = list.get(position).getId();

        if (selectedIds.contains(id)) {
            //if item is selected then,set foreground color of FrameLayout.
            holder.rootView.setForeground(new ColorDrawable(ContextCompat.getColor(context, R.color.red)));
        } else {
            //else remove selected item color.
            holder.rootView.setForeground(new ColorDrawable(ContextCompat.getColor(context, android.R.color.transparent)));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public MyData getItem(int position) {
        return list.get(position);
    }

    public void setSelectedIds(List<Integer> selectedIds) {
        this.selectedIds = selectedIds;
        notifyDataSetChanged();
    }

    /**
     * viewHolder
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        FrameLayout rootView;

        MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            rootView = itemView.findViewById(R.id.root_view);
        }
    }
}
