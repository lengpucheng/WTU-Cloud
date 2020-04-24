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
import cn.hll520.wtu.cloud.repository.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    private User user=new User();
    private MutableLiveData<CloudUser> _CLOUD=new MutableLiveData<>();
    private UserRepository repository;
    LiveData<CloudUser> getCloudUser(){return _CLOUD;}

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository=new UserRepository(application);
    }

    //设置用户
    void setUser(String username, String password){
        if(username.matches("[0-9]+"))
            user.setUID(Integer.parseInt(username));
        else
            user.setUname(username);
        user.setPassword(password);
        new login().execute();
    }


    //对外接口
    void addUser(User... users){repository.insert(users);}





    /*————————————————————————————————-封装的方法————————————————————————————————-
    * */
    @SuppressLint("StaticFieldLeak")
    class login extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            CloudUser cloudUser = new CloudUser(user);
            _CLOUD.postValue(cloudUser);
            return null;
        }
    }

}
