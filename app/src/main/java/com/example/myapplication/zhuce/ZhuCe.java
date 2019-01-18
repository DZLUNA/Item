package com.example.myapplication.zhuce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.denglu.DengLu;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class ZhuCe extends AppCompatActivity {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.shoujihao)
    EditText shoujihao;
    @BindView(R.id.yanzhengma)
    EditText yanzhengma;
    @BindView(R.id.YZM)
    TextView mYZM;
    @BindView(R.id.zhuce)
    Button zhuce;
    @BindView(R.id.toob)
    Toolbar toob;
    private int pahe = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ce);
        ButterKnife.bind(this);
        toob.setTitle("");
        setSupportActionBar(toob);
        toob.setNavigationIcon(R.drawable.fanhui);
        toob.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        YZM();
    }

    private void YZM() {
        Retrofit build = new Retrofit.Builder()
                .baseUrl(MyServer.yanzheng)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyServer myServer = build.create(MyServer.class);
        Call<Demo_YZM> text = myServer.getText();
        text.enqueue(new Callback<Demo_YZM>() {
            @Override
            public void onResponse(Call<Demo_YZM> call, Response<Demo_YZM> response) {
                int code = response.body().getCode();
                String data = response.body().getData();
                String ret = response.body().getRet();
                if (code == 200) {
                    mYZM.setText(data);
                } else {
                    Toast.makeText(ZhuCe.this, ret, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Demo_YZM> call, Throwable t) {

            }
        });

    }

    private void ZC() {
        Retrofit build = new Retrofit.Builder().baseUrl(MyServer.url).addConverterFactory(GsonConverterFactory.create()).build();
        MyServer myServer = build.create(MyServer.class);
        Map<String, Object> map = new HashMap<>();
        map.put("username", name.getText().toString());
        map.put("password", password.getText().toString());
        map.put("phone", shoujihao.getText().toString());
        map.put("verify", yanzhengma.getText().toString());

        Call<Demo_ZC> data = myServer.getData(map);
        data.enqueue(new Callback<Demo_ZC>() {
            @Override
            public void onResponse(Call<Demo_ZC> call, Response<Demo_ZC> response) {
                int code = response.body().getCode();
                String data1 = response.body().getData();
                String ret = response.body().getRet();
                if (ret.equals("注册成功")) {
                    Toast.makeText(ZhuCe.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ZhuCe.this, DengLu.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ZhuCe.this, "注册失败，" + ret, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Demo_ZC> call, Throwable t) {
//                Log.e("注册失败：：", "onFailure: "+t.getMessage() );
                Toast.makeText(ZhuCe.this, "注册后台有问题", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.zhuce)
    public void onViewClicked() {
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZC();
            }
        });
    }

    /**
     * 验证码 get 无参数
     * http://yun918.cn/study/public/index.php/verify
     * <p>
     * 注册接口 post  参数(String username,String password,String phone,String verify)
     * http://yun918.cn/study/public/register
     */
    interface MyServer {
        //  注册
        String url = "http://yun918.cn/study/public/";

        @POST("register")
        @FormUrlEncoded
        Call<Demo_ZC> getData(@FieldMap Map<String, Object> map);


        //验证码
        String yanzheng = "http://yun918.cn/study/public/index.php/";

        @GET("verify")
        Call<Demo_YZM> getText();
    }
}
