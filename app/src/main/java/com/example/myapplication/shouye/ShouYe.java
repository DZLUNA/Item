package com.example.myapplication.shouye;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.denglu.DengLu;
import com.example.myapplication.shoucang.ShouCang;
import com.example.myapplication.zhanghu.ZhangHuXIangQing;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public class ShouYe extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.toob)
    Toolbar toob;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp)
    ViewPager vp;

    private List<Demo.ChannelsBean> channels = new ArrayList<>();
    private ArrayList<Fragment> list;
    private Fragment_Adapter adapter;
    private String s ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_ye);
        ButterKnife.bind(this);
        toob.setTitle("");
        Intent intent = getIntent();
        String dengluname = intent.getStringExtra("dengluname");
        if(dengluname!=null&&dengluname.length()!=0){
            textView.setText(dengluname);
        }
        setSupportActionBar(toob);

        WL();



    }

    //  相机：
    //=================================================================================================================================
    //权限
    private void getQuanXian() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //权限发生了改变 true  //  false 小米
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                new AlertDialog.Builder(this).setTitle("title")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 请求授权
                                ActivityCompat.requestPermissions(ShouYe.this, new String[]{Manifest.permission.CAMERA}, 1);
                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
            } else {
                ActivityCompat.requestPermissions(ShouYe.this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            // camear 权限回调
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 表示用户授权
                Toast.makeText(this, " user Permission", Toast.LENGTH_SHORT).show();
            } else {
                //用户拒绝权限
                Toast.makeText(this, " no Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086 && resultCode == 24) {
            Uri bitmap = data.getParcelableExtra("bitmap2");
            try {
                Bitmap originalBitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(bitmap));
                int originalWidth = originalBitmap.getWidth();
                int originalHeight = originalBitmap.getHeight();
                int newWidth = 100;
                int newHeight = 300; // 自定义 高度 暂时没用
                float scale = ((float) newHeight) / originalHeight;
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                Bitmap changedBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalWidth, originalHeight, matrix, true);
                Drawable drawable1 = new BitmapDrawable(changedBitmap);
                imageView.setImageDrawable(drawable1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == 10086 && resultCode == 3) {
            Bitmap bitmap = data.getParcelableExtra("bitmap");
            Drawable drawable = new BitmapDrawable(bitmap);
            imageView.setImageDrawable(drawable);
        }
    }

    //=================================================================================================================================

    //    toob.setTitle("");
//    setSupportActionBar(toob);
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(1, 1, 0, "收藏");
        menu.add(2, 2, 0, "上传");
        menu.add(3, 3, 0, "下载");
        //返回值:是否要显示menu,   true显示,false:不显示   默认值即可
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Intent intent = new Intent(this, ShouCang.class);
                startActivity(intent);
                break;
            case 2:
                Toast.makeText(this, "上传", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "下载", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.imageView, R.id.textView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView:

                Intent intent1 = new Intent(this, ZhangHuXIangQing.class);
                startActivity(intent1);
                break;
            case R.id.textView:
                Intent intent = new Intent(this, DengLu.class);
                startActivity(intent);
                break;
        }
    }
    private void WL() {

        Retrofit build = new Retrofit.Builder().baseUrl(MyServer.url).addConverterFactory(GsonConverterFactory.create()).build();

        MyServer myServer = build.create(MyServer.class);
        Map<String, Object> map = new HashMap<>();
        Call<Demo> data = myServer.getData();
        data.enqueue(new Callback<Demo>() {
            @Override
            public void onResponse(Call<Demo> call, Response<Demo> response) {
                List<Demo.ChannelsBean> channels = response.body().getChannels();
                Log.e("*******************", "onResponse: "+channels.get(0).getName());
                list = new ArrayList<>();
                for (int i = 0; i < channels.size(); i++) {
                    list.add(new BlankFragment(channels.get(i).getChannel()));
//                    tab.addTab(tab.newTab().setText(channels.get(i).getName()));
                }
                adapter = new Fragment_Adapter(getSupportFragmentManager(),list,channels);
                vp.setAdapter(adapter);
                tab.setupWithViewPager(vp);

            }

            @Override
            public void onFailure(Call<Demo> call, Throwable t) {
                Log.e("Aa", "onFailure: "+t.getMessage());
            }
        });

    }
    interface MyServer{
        String url = "http://yun918.cn/study/public/index.php/";
        @GET("newchannel")
        Call<Demo> getData();

    }
//    https://github.com/iceCola7/WanAndroid
}
