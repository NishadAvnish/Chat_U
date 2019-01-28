package com.example.avnish.whatsapp_clone.UserListRecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.avnish.whatsapp_clone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class userlist_Adapter extends RecyclerView.Adapter<userlist_Adapter.myViewHolder> {

    ArrayList<UserList_Databook> arrayList;

    public userlist_Adapter(ArrayList<UserList_Databook> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public userlist_Adapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist_recyclerview,viewGroup,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userlist_Adapter.myViewHolder myViewHolder, int i) {
        UserList_Databook databook= arrayList.get(i);
        myViewHolder.listusername.setText(databook.name);
        myViewHolder.listuserstatus.setText(databook.status);
        Picasso.get().load(databook.image).into(myViewHolder.listface);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView listusername,listuserstatus;
        ImageView listface;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            listusername=(TextView)itemView.findViewById(R.id.userlistname);
            listuserstatus=(TextView)itemView.findViewById(R.id.userliststatus);
            listface=(ImageView)itemView.findViewById(R.id.userlistface);
        }
    }
}
