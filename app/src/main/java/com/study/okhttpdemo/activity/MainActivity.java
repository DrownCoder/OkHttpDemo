package com.study.okhttpdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.study.okhttpdemo.R;
import com.study.okhttpdemo.bean.user;
import com.study.okhttpdemo.callback.GsonCallback;
import com.study.okhttpdemo.callback.UserCallBack;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Button mBtnGet;
    private Button mBtnPost;
    private Button mBtnUtilGet;
    private Button mBtnUtilPost;
    private TextView mTvResult;
    private View.OnClickListener mOnClickListener;
    private OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initViews();
        initEvents();
    }

    private void init() {
        mOkHttpClient = new OkHttpClient();
    }

    private void initPostRequest() {
        FormBody body = new FormBody.Builder()
                .add("uid", "123456")
                .add("name", "姓名")
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.88.158:8089/posttest")
                .post(body)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvResult.setText(str);
                    }
                });
            }
        });
    }

    /**
     * Get请求
     */
    private void initGetRequest() {
        Request request = new Request.Builder()
                .url("http://192.168.88.158:8089/add")
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvResult.setText(str);
                    }
                });
            }
        });
    }

    private void initEvents() {
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_get:
                        initGetRequest();
                        break;
                    case R.id.btn_post:
                        initPostRequest();
                        break;
                    case R.id.btn_util_get:
                        initGetUtilRequest();
                        break;
                    case R.id.btn_util_post:
                        initPostUtilRequest();
                        break;
                }
            }
        };
        mBtnGet.setOnClickListener(mOnClickListener);
        mBtnPost.setOnClickListener(mOnClickListener);
        mBtnUtilGet.setOnClickListener(mOnClickListener);
        mBtnUtilPost.setOnClickListener(mOnClickListener);
    }

    private void initPostUtilRequest() {
        OkHttpUtils
                .post()
                .url("http://192.168.88.158:8089/posttest")
                .addParams("username", "hyman")
                .addParams("password", "123")
                .build()
                .execute(new UserCallBack(){
                    @Override
                    public void onResponse(user response, int id) {
                        mTvResult.setText(response.getId());
                    }
                });
    }

    private void initGetUtilRequest() {
        OkHttpUtils
                .get()
                .url("http://192.168.88.158:8089/add")
                .build()
                .execute(new GsonCallback(user.class){
                    @Override
                    public void onResponse(Object response, int id) {
                        user item= (user) response;
                        mTvResult.setText(item.getId());
                    }
                });

    }

    private void initViews() {
        mBtnGet = (Button) findViewById(R.id.btn_get);
        mBtnPost = (Button) findViewById(R.id.btn_post);
        mBtnUtilGet = (Button) findViewById(R.id.btn_util_get);
        mBtnUtilPost = (Button) findViewById(R.id.btn_util_post);
        mTvResult = (TextView) findViewById(R.id.tv_result);
    }
}
