package com.example.myapplication.zhanghu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;

import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZhangHuXIangQing extends AppCompatActivity {

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.toob)
    Toolbar toob;
    @BindView(R.id.btn)
    Button btn;


    private Uri mData1;
    private Bitmap mBitmap;
    private int c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhang_hu_xiang_qing);
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

        initView();

    }

    private void initView() {
        //注册上下文菜单
        registerForContextMenu(img);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1, 1, 0, "打开相机");
        menu.add(1, 2, 0, "进入相册");
        menu.add(1, 3, 0, "取消");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                DaKaiXiangJi();   //打开相机
                break;
            case 2:
                JinRuXiangCe();   //进入相册
                break;
            case 3:
                showToast("取消");
                break;
        }
        return super.onContextItemSelected(item);
    }

    //打开相机
    private void DaKaiXiangJi() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        startActivityForResult(intent, 1);
    }

    //进入相册
    private void JinRuXiangCe() {
        Intent intent1 = new Intent("android.intent.action.GET_CONTENT");
        intent1.setType("image/*");
        startActivityForResult(intent1, 10086);
    }

    //吐司
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    //==========================================================================================================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (10086 == requestCode && resultCode == RESULT_OK) {
            if (data != null) {
                //获取数据
                //获取内容解析者对象
                try {
                    mData1 = data.getData();
                    String path = mData1.getPath();
                    Log.d("path", path);
                    mBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                    if (mBitmap != null) {
                        img.setImageBitmap(mBitmap);
                        c = 1;
                    } else {
                        Toast.makeText(this, "没数据", Toast.LENGTH_LONG).show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras(); // 从data中取出传递回来缩略图的信息，图片质量差，适合传递小图片
            // 将data中的信息流解析为Bitmap类型
            mBitmap = (Bitmap) bundle.get("data");
            img.setImageBitmap(mBitmap);// 显示图
            c = 2;
//            Glide.with(this).load(head).apply(options).into(imgHead);
//            加载一张圆形图片//
//            RequestOptions options = RequestOptions.circleCropTransform()
//                    .placeholder(R.mipmap.ic_launcher)
//                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                    .skipMemoryCache(true);
//            Glide.with(this).load(img_url).apply(options).into(img);
        }
    }

    public void returnMa1() {
        if (c == 1) {
            Intent intent2 = getIntent();
            intent2.putExtra("bitmap2", mData1);
            setResult(24, intent2);
            finish();
        } else if (c == 2) {
            Intent intent = getIntent();
            intent.putExtra("bitmap", mBitmap);
            setResult(3, intent);
            finish();
        }
    }
// ================================================================================================================W
    @OnClick(R.id.btn)
    public void onViewClicked() {

    }
}
