package com.example.myapplication.denglu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.shouye.ShouYe;
import com.example.myapplication.zhuce.ZhuCe;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class DengLu extends AppCompatActivity {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.denglu)
    Button denglu;
    @BindView(R.id.zhuce)
    Button zhuce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deng_lu);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.denglu, R.id.zhuce})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.denglu:
                DL();
                break;
            case R.id.zhuce:
                Intent intent = new Intent(this, ZhuCe.class);
                startActivity(intent);
                break;
        }
    }

    private void DL() {
        Retrofit build = new Retrofit.Builder()
                .baseUrl(MyServer.login)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MyServer myServer = build.create(MyServer.class);
        Map<String, Object> map = new HashMap<>();
        map.put("username", name.getText().toString());
        map.put("password", password.getText().toString());
        Observable<Demo_DnegLu> data = myServer.getData(map);
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Demo_DnegLu>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Demo_DnegLu value) {
                        int code = value.getCode();
                        String ret = value.getRet();
                        if (code == 200) {
                            Toast.makeText(DengLu.this, ret, Toast.LENGTH_SHORT).show();
                            List<Demo_DnegLu.DataBean> data1 = value.getData();
                            for (int i = 0; i < data1.size(); i++) {
                                String s = name.getText().toString();
                                EventBus.getDefault().postSticky(s);
                                Intent intent = new Intent(DengLu.this,ShouYe.class);
                                intent.putExtra("dengluname", s);
                                startActivity(intent);
                            }
                            finish();
                        } else {
                            Toast.makeText(DengLu.this, ret, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    interface MyServer {
        //登录
        //http://yun918.cn/study/public/index.php/login
        String login = "http://yun918.cn/study/public/index.php/";

        @POST("login")
        @FormUrlEncoded
        Observable<Demo_DnegLu> getData(@FieldMap Map<String, Object> map);

    }
}
