package cn.hll520.wtu.cloud.Activity.Login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import cn.hll520.wtu.cloud.cloud.CloudUser;
import cn.hll520.wtu.cloud.model.User;
import cn.hll520.wtu.cloud.repository.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    private User user=new User();
    private UserRepository repository;
    private CloudUser cloud=new CloudUser();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository=new UserRepository(application);
    }
    //获取结果集
    LiveData<CloudUser.ResultLogin> getResultLogin(){return cloud.getResultLogin();}
    //登录
    void doLogin(String username, String password){
        if(username.matches("[0-9]+"))
            user.setUID(Integer.parseInt(username));
        else
            user.setUname(username);
        user.setPassword(password);
        cloud.doLogin(user);
    }
    //对外接口 插入用户
    void addUser(User... users){repository.insert(users);}
}
