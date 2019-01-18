package com.example.myapplication.xiangqing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.example.myapplication.R;
import com.example.myapplication.shouye.Demo1;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XiangQIng extends AppCompatActivity {

    @BindView(R.id.toob)
    Toolbar toob;
    @BindView(R.id.web)
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiang_qing);
        ButterKnife.bind(this);
        intView();
    }

    private void intView() {
        toob.setTitle("");
        setSupportActionBar(toob);
        toob.setNavigationIcon(R.drawable.fanhui);
        toob.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        EventBus.getDefault().register(this);

    }

//    EventBus.getDefault().register(this);
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getMessage(Demo1.ResultBean.DataBean dataBean){
        web.loadUrl(dataBean.getUrl());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
