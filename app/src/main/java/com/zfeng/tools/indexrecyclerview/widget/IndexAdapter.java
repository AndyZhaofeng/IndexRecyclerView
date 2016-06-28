package com.zfeng.tools.indexrecyclerview.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zfeng.tools.indexrecyclerview.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by zhaofeng on 16/6/21.
 */
public class IndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    private ArrayList<String> lists;
    private ChineseUtils chineseUtils;

    public IndexAdapter(Context context,@NonNull ArrayList<String> list)
    {
        this.context=context;
        lists=new ArrayList<>();
        chineseUtils=new ChineseUtils();
        for(int i=0;i<list.size();++i)
        {
            lists.add(list.get(i).toLowerCase());
        }
        Collections.sort(this.lists, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return chineseUtils.getFirstLetter(lhs).compareTo(chineseUtils.getFirstLetter(rhs));
            }
        });
    }

    class IndexViewHolder extends RecyclerView.ViewHolder
    {
        View itemView;
        TextView tv;

        public IndexViewHolder(View itemView)
        {
            super(itemView);
            this.itemView=itemView;
            tv=(TextView)itemView.findViewById(R.id.index_item_tv);
        }
        public void addDetail(int position)
        {
            tv.setText(lists.get(position));
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.index_item,null);
        return new IndexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((IndexViewHolder)holder).addDetail(position);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
