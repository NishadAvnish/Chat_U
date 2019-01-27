package com.example.avnish.whatsapp_clone.REcyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.avnish.whatsapp_clone.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;

public class adapter extends RecyclerView.Adapter<adapter.myViewHolder> {
    ArrayList<databook> arraylist;
    String username;

    public adapter(ArrayList<databook> arraylist, String username) {
        this.arraylist = arraylist;
        this.username = username;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview,viewGroup,false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {
        databook databook= arraylist.get(i);
      if((databook.user).equals((FirebaseAuth.getInstance().getCurrentUser().getUid()))){
          myViewHolder.ownll.setVisibility(View.VISIBLE);
          myViewHolder.otherll.setVisibility(View.INVISIBLE);
          myViewHolder.ownName.setText(databook.Name);
          myViewHolder.ownDate.setText(databook.Date);
          myViewHolder.ownMsg.setText(databook.Msg);
          myViewHolder.ownTime.setText(databook.Time);

          myViewHolder.ownName.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
      }
      else{
          myViewHolder.otherll.setVisibility(View.VISIBLE);
          myViewHolder.ownll.setVisibility(View.INVISIBLE);
          myViewHolder.othername.setText(databook.Name);
          myViewHolder.otherdate.setText(databook.Date);
          myViewHolder.othermsg.setText(databook.Msg);
          myViewHolder.othertime.setText(databook .Time);
      }

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    protected class myViewHolder extends RecyclerView.ViewHolder{
        public TextView othername,otherdate,othertime,othermsg,ownName,ownMsg,ownTime,ownDate;
        public LinearLayout otherll,ownll;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            othername=(TextView) itemView.findViewById(R.id.othername);
            otherdate=(TextView) itemView.findViewById(R.id.otherdate);
            othermsg=(TextView)itemView.findViewById(R.id.othermsg);
            othertime=(TextView) itemView.findViewById(R.id.othertime);
            otherll=(LinearLayout)itemView.findViewById(R.id.othersll);


            ownName=(TextView) itemView.findViewById(R.id.ownname);
            ownDate=(TextView) itemView.findViewById(R.id.owndate);
            ownMsg=(TextView)itemView.findViewById(R.id.ownmsg);
            ownTime=(TextView) itemView.findViewById(R.id.owntime);
            ownll=(LinearLayout)itemView.findViewById(R.id.ownll);


        }
    }
}
