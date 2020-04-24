package cn.hll520.wtu.cloud.Activity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import cn.hll520.wtu.cloud.cloud.CloudUser;
import cn.hll520.wtu.cloud.model.User;

public class LoginViewModel extends AndroidViewModel {
    private User user=new User();
    private CloudUser cloudUser;
    private MutableLiveData<CloudUser> _CLOUD=new MutableLiveData<>();
    LiveData<CloudUser> getCloudUser(){return _CLOUD;}

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    //设置用户
    void setUser(String username, String password){
        if(username.matches("[0-9]+"))
            user.setUID(Integer.parseInt(username));
        else
            user.setUname(username);
        user.setPassword(password);

        new Thread(new Runnable() {
            @Override
            public void run() {
                CloudUser cloudUser2=new CloudUser(user);
                _CLOUD.postValue(cloudUser2);
            }
        }).start();

    }

    void dologin(){
        cloudUser=new CloudUser(user);
        _CLOUD.postValue(cloudUser);
    }

}
