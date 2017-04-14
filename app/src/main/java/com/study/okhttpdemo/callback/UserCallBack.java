package com.study.okhttpdemo.callback;

import com.google.gson.Gson;
import com.study.okhttpdemo.bean.user;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dengzhaoxuan on 2017/4/14.
 */

public class UserCallBack extends Callback<user>{
    @Override
    public user parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        user user = new Gson().fromJson(string, user.class);
        return user;
    }

    @Override
    public void onError(Call call, Exception e, int id) {

    }

    @Override
    public void onResponse(user response, int id) {

    }
}
