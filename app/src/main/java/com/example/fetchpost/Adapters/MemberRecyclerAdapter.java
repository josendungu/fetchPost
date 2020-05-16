package com.example.fetchpost.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchpost.Classes.Member;
import com.example.fetchpost.Activities.MemberViewActivity;
import com.example.fetchpost.R;

import java.util.List;

public class MemberRecyclerAdapter extends RecyclerView.Adapter<MemberRecyclerAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<Member> memberList;


    //TODO: implement cursor to the adapter


    public MemberRecyclerAdapter(Context context, List<Member> memberList) {
       this.mContext = context;
       mLayoutInflater = LayoutInflater.from(mContext);
       this.memberList = memberList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_member, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //set values within view holder
        Member member = memberList.get(position);
        holder.mTextUsername.setText(member.getUsername().toUpperCase());
        holder.mTextMemberId.setText(member.getMember_id());
        String name = member.getFName() + " " + member.getLName();
        holder.mTextName.setText(name);
        holder.mMemberUsername = member.getUsername();

    }


    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView mTextUsername, mTextName, mTextMemberId;
        public String mMemberUsername;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextUsername = (TextView) itemView.findViewById(R.id.username);
            mTextMemberId = (TextView) itemView.findViewById(R.id.member_id);
            mTextName = (TextView) itemView.findViewById(R.id.name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, MemberViewActivity.class);
                    intent.putExtra(MemberViewActivity.MEMBER_USERNAME, mMemberUsername);
                    mContext.startActivity(intent);

                }
            });
        }
    }
}
