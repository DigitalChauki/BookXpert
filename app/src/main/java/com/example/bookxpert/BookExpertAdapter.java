package com.example.bookxpert;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookExpertAdapter extends RecyclerView.Adapter<BookExpertAdapter.ViewHolder> {
    Context context;
    List<BookXpertModelData> bookXpertModelDataList;


    public BookExpertAdapter(Context context , List<BookXpertModelData> bookXpertModelDataList ) {
        this.context = context;
        this.bookXpertModelDataList = bookXpertModelDataList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recylerview_table_row ,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(bookXpertModelDataList != null && bookXpertModelDataList.size() >0){
            BookXpertModelData  modelData = bookXpertModelDataList.get(position);
            holder.act_ID.setText(modelData.getActId());
            holder.act_name.setText(modelData.getActName());
            holder.act_amount.setText( modelData.getActAmount());
            holder.get_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context ,DetailsActivity.class );
                    intent.putExtra("actID" ,modelData.getActId());
                    intent.putExtra("actName" ,modelData.getActName());
                    intent.putExtra("actAmount" ,modelData.getActAmount());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);



                }
            });


        }

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(holder.getLayoutPosition());
            }
        });

    }

    @Override
    public int getItemCount() {

        return bookXpertModelDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView act_ID ,act_name,act_amount , get_details;
        Button delete_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            act_ID = itemView.findViewById(R.id.act_ID);
            act_name = itemView.findViewById(R.id.act_name);
            act_amount = itemView.findViewById(R.id.act_amount);
            delete_btn = itemView.findViewById(R.id.delete_btn);
            get_details = itemView.findViewById(R.id.get_details);


        }
    }

    public void removeAt(int position) {
        bookXpertModelDataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, bookXpertModelDataList.size());
    }
}
