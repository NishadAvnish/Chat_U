package com.example.avnish.whatsapp_clone.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.avnish.whatsapp_clone.R;
import java.util.ArrayList;
import java.util.Collections;




public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.ViewHolder> {
    ArrayList<databook>list;


    public GreenAdapter(ArrayList<databook> list) {
        this.list = list;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_recyclerview,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.name.setText((list.get(i)).listtext);
        Log.d("TASF", list.get(i).listtext);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ViewHolder(@NonNull View view) {
            super(view);
            name=(TextView)view.findViewById(R.id.listtext);


        }
    }
}
