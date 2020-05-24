package cn.hll520.wtu.cloud.Activity.Main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import cn.hll520.wtu.cloud.cloud.CloudPeo;
import cn.hll520.wtu.cloud.model.People;
import cn.hll520.wtu.cloud.model.User;
import cn.hll520.wtu.cloud.repository.PeopleRepository;
import cn.hll520.wtu.cloud.repository.UserRepository;

public class PeopleViewModel extends AndroidViewModel {
    private PeopleRepository peoRepository;
    private UserRepository userRepository;
    private CloudPeo cloud=new CloudPeo();

    //构造函数
    public PeopleViewModel(@NonNull Application application) {
        super(application);
        //得到一个People使用接口
        peoRepository = new PeopleRepository(application);
        userRepository=new UserRepository(application);
    }
    /*————————————————————————————初始化接口——————————————————————————
    * */
    //初始化结果集
    LiveData<CloudPeo.ResultPeos> getResult(){return cloud.getResultPeos();}
    //获取联系人列表
    void doGetPeo(int UID){cloud.getPeoAll(UID);}
    //获取当前用户
    LiveData<User> getUser(){return userRepository.getLogin_User();}


    /*———————————————————————————操作数据接口——————————————————————————-
    * */
    LiveData<List<People>> getAllPeos() {
        return peoRepository.getAllPeos();
    }


    void addPeo(List<People> people){ peoRepository.addPeo(people);}

}
