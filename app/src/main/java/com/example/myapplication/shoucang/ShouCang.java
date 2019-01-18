package com.example.myapplication.shoucang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.bean.MyDao;
import com.example.myapplication.bean.Student;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShouCang extends AppCompatActivity {

    @BindView(R.id.toob)
    Toolbar toob;
    @BindView(R.id.rlv)
    RecyclerView rlv;
    private List<Student> select;
    private RecyclerView_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_cang);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        toob.setTitle("");
        setSupportActionBar(toob);
        toob.setNavigationIcon(R.drawable.fanhui);
        toob.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rlv.setLayoutManager(new LinearLayoutManager(this));
        select = MyDao.getInstance().select();
        adapter = new RecyclerView_Adapter(this,select);
        rlv.setAdapter(adapter);
        adapter.setOnClick(new RecyclerView_Adapter.OnClick() {
            @Override
            public void OnClickListener(int position) {

            }

            @Override
            public void OnClickLongListener(int position) {

            }
            @Override
            public void BBBBBBBB(View view, int position) {

                Student student = adapter.select.get(position);
                adapter.select.remove(student);
                adapter.notifyDataSetChanged();
                MyDao.getInstance().delete2(student);

            }
        });

    }
}
