package com.example.myapplication.shouye;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.bean.MyApp;
import com.example.myapplication.bean.MyDao;
import com.example.myapplication.bean.Student;
import com.example.myapplication.shoucang.ShouCang;
import com.example.myapplication.xiangqing.XiangQIng;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class BlankFragment extends Fragment implements XRecyclerView.LoadingListener {

    @BindView(R.id.xrlv)
    XRecyclerView xrlv;
    Unbinder unbinder;
    private String type;
    private XRecyclerView_Adapter adapter;
    private int page = 1;
    private List<Demo1.ResultBean.DataBean> data1 = new ArrayList<>();
    private Button btn;

    public BlankFragment() {

    }
    public BlankFragment(String channel) {
        this.type = channel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank2, container, false);
        unbinder = ButterKnife.bind(this, view);
        inView(view);
        return view;
    }

    private void inView(View view) {
        WL();
        xrlv.setLoadingListener(this);
    }

    private void WL() {
        Retrofit build = new Retrofit.Builder()
                .baseUrl(Myserver.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Myserver myServer = build.create(Myserver.class);
        Map<String, Object> map = new HashMap<>();
        map.put("type",type);
        Call<Demo1> data = myServer.getData(type);
        data.enqueue(new Callback<Demo1>() {
            @Override
            public void onResponse(final Call<Demo1> call, Response<Demo1> response) {

                data1 = response.body().getResult().getData();
                xrlv.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new XRecyclerView_Adapter(getContext(), data1);
                xrlv.setAdapter(adapter);
                adapter.setOnClick(new XRecyclerView_Adapter.OnClick() {
                    @Override
                    public void OnClickListener(int position) {
                        Demo1.ResultBean.DataBean dataBean = data1.get(position);
                        Intent intent = new Intent(getContext(), XiangQIng.class);
                        EventBus.getDefault().postSticky(dataBean);
                        startActivity(intent);
                        xrlv.loadMoreComplete();
                    }

                    @Override
                    public void OnClickLongListener(int position) {

                    }

                    @Override
                    public void BBBBBBBB(View view, int position) {
                        Button btn = view.findViewById(R.id.btn);
                        btn.setVisibility(View.GONE);

                        MyDao.getInstance().insert2(new Student(null,data1.get(position).getTitle(),
                                data1.get(position).getThumbnail_pic_s02(),
                                data1.get(position).getDate()));

                    }
                });
            }

            @Override
            public void onFailure(Call<Demo1> call, Throwable t) {
                Log.e("CW", "onFailure: "+t.getMessage() );
            }
        });

    }

    @Override
    public void onRefresh() {
        page = 1;
        xrlv.refreshComplete();
    }

    @Override
    public void onLoadMore() {
        page++;
        WL();
//        xrlv.loadMoreComplete();
    }

    interface Myserver{
//http://toutiao-ali.juheapi.com/toutiao/index?type=top?Authorization:APPCODE db33b75c89524a56ac94d6519e106a59
        String url = "http://toutiao-ali.juheapi.com/toutiao/";
        @GET("index")
        @Headers("Authorization:APPCODE db33b75c89524a56ac94d6519e106a59")
        Call<Demo1>getData(@Query("type")String type);
    }

    public static BlankFragment newInstance() {
        Bundle args = new Bundle();
        BlankFragment fragment = new BlankFragment();
        fragment.setArguments(args);
        return fragment;
    }

//    https://www.jianshu.com/p/2216475cddfe

}
