package com.example.avnish.whatsapp_clone.UserListRecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avnish.whatsapp_clone.Basic_Activity.ProfileActivity;
import com.example.avnish.whatsapp_clone.CHAT_RecyclerView.ChatActivity;
import com.example.avnish.whatsapp_clone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class userlist_Adapter extends RecyclerView.Adapter<userlist_Adapter.myViewHolder>{

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
        View view;

        //3==chat fragment and 1== findfriend
        if(flag==1 || flag==3){
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist_recyclerview,viewGroup,false);}


        else{ view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.accept_del_requestfragt,viewGroup,false);}

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final userlist_Adapter.myViewHolder myViewHolder, final int i) {
        final UserList_Databook databook= arrayList.get(i);


        //-------------------------------FOR  CHAT FRAGEMNT AND FIND FRIEND-------------------------------------//
        if(flag==1||flag==3) {
            myViewHolder.listusername.setText(databook.Name);
            if(databook.Status != "")
            {myViewHolder.listuserstatus.setText(databook.Status);}

            Picasso.get().load(databook.Image).placeholder(R.drawable.face).into(myViewHolder.listface);
        }


        //------------------------------FOR REQUEST FRAGMENT---------------------------------------------------//
        else{
            myViewHolder.acceptName.setText(databook.Name);
            Picasso.get().load(databook.Image).placeholder(R.drawable.face).into(myViewHolder.acceptImage);
        }

        //---------------------------------------------ONCLICK LISTENER-----------------------------------------------//
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

        if (flag==3){
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(v.getContext(),ChatActivity.class);
                    intent.putExtra("CHATACTIVITY",3);
                    String userId=(arrayList.get(i).Uid).toString();
                    intent.putExtra("Tag",userId);
                    v.getContext().startActivity(intent);

                }
            });

            myViewHolder.listface.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openImageDialog(i,context);
                }
            });

        }


        if(flag==2){
            myViewHolder.acceptAcceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Accept", Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Friend")
                            .child(arrayList.get(i).Uid).setValue("yes");
                    FirebaseDatabase.getInstance().getReference().child("User").child(arrayList.get(i).Uid).child("Friend")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("yes");


                    //-------------------------TO remove friend request section---------------------------//
                    FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("FriendRequest").setValue(null);


                    //---------------------------To remove send friend request section from sender database--------------------------//

                    FirebaseDatabase.getInstance().getReference().child("User").child(arrayList.get(i).Uid)
                            .child("SendRequest").setValue(null);

                    // remove the item from recyclerview
                    arrayList.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i,arrayList.size());
                    notifyDataSetChanged();

                }
            });


            // for decline button
            myViewHolder.acceptDeclineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "DECLINE", Toast.LENGTH_SHORT).show();

                    //-------------------------TO remove friend request section---------------------------//
                    FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child("FriendRequest").setValue(null);


                    //---------------------------To remove send friend request section from sender database--------------------------//

                    FirebaseDatabase.getInstance().getReference().child("User").child(arrayList.get(i).Uid)
                            .child("SendRequest").setValue(null);

                    // remove the item from recyclerview
                    arrayList.remove(i);
                    notifyItemRemoved(i);
                    notifyItemRangeChanged(i,arrayList.size());
                    notifyDataSetChanged();
                }
            });

        myViewHolder.acceptImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openImageDialog(i,context);
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

        TextView acceptName;
        ImageView acceptImage;
        Button acceptAcceptButton,acceptDeclineButton;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            listusername=(TextView)itemView.findViewById(R.id.userlistname);
            listuserstatus=(TextView)itemView.findViewById(R.id.userliststatus);
            listface=(ImageView)itemView.findViewById(R.id.userlistface);


            acceptName=(TextView)itemView.findViewById(R.id.acceptName);
            acceptImage=(ImageView)itemView.findViewById(R.id.acceptImage);
            acceptAcceptButton=(Button)itemView.findViewById(R.id.acceptAcceptButton);
            acceptDeclineButton=(Button)itemView.findViewById(R.id.acceptDeclineButton);




        }

    }


    protected void openImageDialog(int i,Context context){
        final Dialog dialog= new Dialog(context);
        dialog.setContentView(R.layout.openimagedialog);

        TextView Name = (TextView) dialog.findViewById(R.id.dialogText);
        Name.setText(arrayList.get(i).Name);
        ImageView image = (ImageView) dialog.findViewById(R.id.dialogImage);


        Picasso.get().load(arrayList.get(i).Image).placeholder(R.drawable.face).fit().into(image);


        ImageView delete = (ImageView) dialog.findViewById(R.id.dialogCancelbtn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Point size = new Point();
        dialog.getWindow().getWindowManager().getDefaultDisplay().getSize(size);
        int width=size.x;
        int height=size.y;

        dialog.getWindow().setLayout((int) (width * .75), (int) (height * .60));

        dialog.show();
    }
}
