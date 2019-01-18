package com.example.myapplication.shoucang;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.bean.Student;
import com.example.myapplication.shouye.XRecyclerView_Adapter;

import java.util.List;

import retrofit2.http.GET;

/**
 * Created by 叟 on 2018/12/11.
 */

public class RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView_Adapter.MyViewHolder> {

    private Context context;
    public List<Student> select;
    OnClick onClick;

    public RecyclerView_Adapter(ShouCang shouCang, List<Student> select) {
        this.context = shouCang;
        this.select = select;
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public RecyclerView_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item1, null, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);

        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView_Adapter.MyViewHolder holder, final int position) {

        holder.textView.setText(select.get(position).getName());
        holder.textview.setText(select.get(position).getPage());
        Glide.with(context).load(select.get(position).getImg()).into(holder.imageView);

//        接口回调的方法
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.OnClickListener(position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onClick.OnClickLongListener(position);
                return false;
            }
        });
        holder.butten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.BBBBBBBB(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (select == null) {
            return 0;
        }
        return select.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;
        private TextView textview;
        private Button butten;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            textview = itemView.findViewById(R.id.publishTime);
            textView = itemView.findViewById(R.id.tv);
            butten = itemView.findViewById(R.id.btn);
        }
    }

    //    接口回调     定义一个接口名
    public interface OnClick {
        //       定义自己要用的接口
        void OnClickListener(int position);

        void OnClickLongListener(int position);

        void BBBBBBBB(View view, int position);

//        打完要上去声明一下
//        然后set一个方法
    }
}