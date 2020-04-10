package com.example.fetchpost;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MemberRecyclerAdapter extends RecyclerView.Adapter<MemberRecyclerAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private static final String TAG = "MemberRecyclerAdapter";
    private List<Member> memberList;


    //TODO: implement cursor to the adapter


    public MemberRecyclerAdapter(Context context, List<Member> memberList) {
       this.mContext = context;
       mLayoutInflater = LayoutInflater.from(mContext);
        Log.d(TAG, "MemberRecyclerAdapter: Constructor");

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Create");
        View itemView = mLayoutInflater.inflate(R.layout.item_member, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //set values within view holder
        Member member = memberList.get(position);
        holder.mTextUsername.setText(member.getUsername());
    }


    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView mTextUsername;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder: ViewHolder");
            mTextUsername = (TextView) itemView.findViewById(R.id.username);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"Clicked",Toast.LENGTH_LONG).show();

                }
            });
        }
    }
}
