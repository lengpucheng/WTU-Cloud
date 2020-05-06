package cn.hll520.wtu.cloud.Activity.Main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import cn.hll520.wtu.cloud.model.People;
import cn.hll520.wtu.cloud.model.User;
import cn.hll520.wtu.cloud.repository.PeopleRepository;
import cn.hll520.wtu.cloud.repository.UserRepository;


public class MycenterViewModel extends AndroidViewModel {
    private PeopleRepository peopleRepository;
    private UserRepository userRepository;
    People people;
    User user;

    public MycenterViewModel(@NonNull Application application) {
        super(application);
        peopleRepository=new PeopleRepository(application);
        userRepository=new UserRepository(application);
    }
    //获取当前用户
    void outLogin(){
        //清空联系人
        peopleRepository.outLogin();
        //修改为下线
        user.setLogin(false);
        userRepository.upUser(user);
    }

    LiveData<User> getUser(){return userRepository.getLogin_User();}

    LiveData<People> getInfo(int UID){return peopleRepository.getInfo_UID(UID);}



}
