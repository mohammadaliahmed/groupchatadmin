package com.appsinventiv.corperstripadmin;

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

import com.appsinventiv.corperstripadmin.Activities.EditGroup;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AliAh on 25/07/2018.
 */

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {
    Context context;
    ArrayList<GroupModel> itemList;

    public GroupsAdapter(Context context, ArrayList<GroupModel> itemList){
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_item_layout,parent,false);
        GroupsAdapter.ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GroupModel model = itemList.get(position);
        holder.groupName.setText(model.getName());
        if(!model.getPicUrl().equals("")){

            Glide.with(context).load(model.getPicUrl()).into(holder.img);

        }else if (model.getPicUrl().equals("")){
            holder.img.setImageResource(R.drawable.ic_group);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, EditGroup.class);
                i.putExtra("groupId",model.getId());
                context.startActivity(i);

            }
        });

        if(model.isActive()){
            holder.status.setText("Stauts: Active");

        }else if (!model.isActive()){
            holder.status.setText("Status: Inactive");

        }


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView groupName,status;
        CircleImageView img;

        public ViewHolder(View itemView) {

            super(itemView);
            groupName = itemView.findViewById(R.id.groupName);
            img = itemView.findViewById(R.id.img);
            status = itemView.findViewById(R.id.status);


        }
    }

}
