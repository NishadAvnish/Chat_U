package com.example.avnish.whatsapp_clone.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.avnish.whatsapp_clone.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.ViewHolder> {
    List<databook> list= Collections.emptyList();
    Context context;
    TextView name;

    public GreenAdapter(List<databook> list, Context context) {
        this.list = list;
        this.context = context;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.listview_recyclerview,viewGroup,false);

        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        name.setText(list.get(i).listtext);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View view) {
            super(view);
            name=(TextView)view.findViewById(R.id.listtext);


        }
    }
}
