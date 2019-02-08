package com.example.avnish.whatsapp_clone.UserListRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avnish.whatsapp_clone.Basic_Activity.ProfileActivity;
import com.example.avnish.whatsapp_clone.R;
import com.example.avnish.whatsapp_clone.chat_Activities.Group_Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
        if(flag==1 || flag==3){
        view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist_recyclerview,viewGroup,false);}
        else{ view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.acceptrequest,viewGroup,false);}

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final userlist_Adapter.myViewHolder myViewHolder, final int i) {
        final UserList_Databook databook= arrayList.get(i);

       if(flag==1||flag==3) {
           myViewHolder.listusername.setText(databook.Name);
           myViewHolder.listuserstatus.setText(databook.Status);
           Picasso.get().load(databook.Image).into(myViewHolder.listface);
       }

       else{
           myViewHolder.acceptName.setText(databook.Name);
           Picasso.get().load(databook.Image).into(myViewHolder.acceptImage);
       }

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

        else if (flag==3){
           myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  Intent intent= new Intent(v.getContext(),Group_Chat.class);
                  intent.putExtra("CHATACTIVITY",3);
                  String userId=(arrayList.get(i).Uid).toString();
                  intent.putExtra("Tag",userId);
                  v.getContext().startActivity(intent);

               }
           });

       }

       else{
                  myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                      @Override
                      public void onClick(View v) {
                          Toast.makeText(context,"Accept",Toast.LENGTH_SHORT).show();
                          myViewHolder.acceptAcceptButton.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                  Toast.makeText(context,"Accept",Toast.LENGTH_SHORT).show();
                                  FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Friend").child(arrayList.get(i).Uid).setValue("yes");
                                  FirebaseDatabase.getInstance().getReference().child("User").child(arrayList.get(i).Uid).child("Friend").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("yes");


                                  //-------------------------TO remove friend request section---------------------------//
                                  FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("FriendRequest").addListenerForSingleValueEvent(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                          dataSnapshot.getRef().removeValue();
                                      }

                                      @Override
                                      public void onCancelled(@NonNull DatabaseError databaseError) {

                                      }
                                  });
                                  //---------------------------To remove send friend request section from sender database--------------------------//

                                  FirebaseDatabase.getInstance().getReference().child("User").child(arrayList.get(i).Uid).child("SendRequest").addValueEventListener(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                          dataSnapshot.getRef().removeValue();
                                      }

                                      @Override
                                      public void onCancelled(@NonNull DatabaseError databaseError) {

                                      }
                                  });

                              }
                          });



                          myViewHolder.acceptDeclineButton.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                  Toast.makeText(context,"DECLINE",Toast.LENGTH_SHORT).show();
                                  //-------------------------TO remove friend request section---------------------------//
                                  FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("FriendRequest").addListenerForSingleValueEvent(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                          dataSnapshot.getRef().removeValue();
                                      }

                                      @Override
                                      public void onCancelled(@NonNull DatabaseError databaseError) {

                                      }
                                  });
                                  //---------------------------To remove send friend request section from sender database--------------------------//

                                  FirebaseDatabase.getInstance().getReference().child("User").child(arrayList.get(i).Uid).child("SendRequest").addValueEventListener(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                          dataSnapshot.getRef().removeValue();
                                      }

                                      @Override
                                      public void onCancelled(@NonNull DatabaseError databaseError) {

                                      }
                                  });

                              }
                          });

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
}
