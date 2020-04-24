package cn.hll520.wtu.cloud.Activity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import cn.hll520.wtu.cloud.cloud.CloudUser;
import cn.hll520.wtu.cloud.model.User;

public class LoginViewModel extends AndroidViewModel {
    private User user=new User();
    private CloudUser cloudUser=new CloudUser();
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
        cloudUser.setUser(user);
        _CLOUD.setValue(cloudUser);
    }
}
