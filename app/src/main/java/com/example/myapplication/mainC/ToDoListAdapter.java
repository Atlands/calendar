package com.example.myapplication.mainC;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.ToDoListContent.ToDoListContnet;

import java.util.List;
import java.util.logging.Handler;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {
    private Context context;
    private List<ToDoListItem> toDoListItems;

    static class ViewHolder extends RecyclerView.ViewHolder {
        //View tdview;
        CardView cardView;
        ImageView toDoListImageView;
        TextView toDoListTitle, toDoListRemark;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            toDoListImageView = view.findViewById(R.id.to_do_list_image);
            toDoListTitle = view.findViewById(R.id.to_do_list_title);
            toDoListRemark = view.findViewById(R.id.to_do_list_remark);
        }
    }

    public ToDoListAdapter(List<ToDoListItem> toDoListItems) {
        this.toDoListItems = toDoListItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.today_to_do_list, parent, false);
        ViewHolder holder= new ViewHolder(view);
        final ToDoListItem toDoListItem = toDoListItems.get(viewType);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(parent.getContext(),ToDoListContnet.class);
                intent.putExtra("id",toDoListItem.getsId());
                parent.getContext().startActivity(intent);
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDoListItem toDoListItem = toDoListItems.get(position);
        int id=toDoListItem.getsId();
        holder.toDoListImageView.setImageResource(toDoListItem.getImageId());
        holder.toDoListTitle.setText(toDoListItem.getTitle());
        holder.toDoListRemark.setText(toDoListItem.getRemark());
    }

    @Override
    public int getItemCount() {
        return toDoListItems.size();
    }
}
