package com.example.avnish.whatsapp_clone.UserListRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.avnish.whatsapp_clone.Basic_Activity.ProfileActivity;
import com.example.avnish.whatsapp_clone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class userlist_Adapter extends RecyclerView.Adapter<userlist_Adapter.myViewHolder> {

    ArrayList<UserList_Databook> arrayList;
    Integer flag=0;
    Context context,con;

    public userlist_Adapter(ArrayList<UserList_Databook> arrayList, Integer flag, Context context) {
        this.arrayList = arrayList;
        this.flag = flag;
        this.context = context;
    }

    @NonNull
    @Override
    public userlist_Adapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist_recyclerview,viewGroup,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userlist_Adapter.myViewHolder myViewHolder, final int i) {
        UserList_Databook databook= arrayList.get(i);
        myViewHolder.listusername.setText(databook.Name);
        myViewHolder.listuserstatus.setText(databook.Status);
        Picasso.get().load(databook.Image).into(myViewHolder.listface);


       if(flag==1){
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userId=arrayList.get(i).Uid;
                    Intent i= new Intent(v.getContext(),ProfileActivity.class);
                    i.putExtra("Tag",userId);
                    v.getContext().startActivity(i);


                }
            });
        }


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
