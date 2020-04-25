package cn.hll520.wtu.cloud.Activity.Main;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

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
    private PeopleRepository peorepository;
    private UserRepository userRepository;
    private String TAG="PEO_MAIN";

    //构造函数
    public PeopleViewModel(@NonNull Application application) {
        super(application);
        //得到一个People使用接口
        peorepository = new PeopleRepository(application);
        userRepository=new UserRepository(application);
    }

    //初始化
    void getPEO(){ new getAllPEO().execute(); }


    //————————————————————————对外提供的管理数据
    LiveData<List<People>> getAllPeos() {
        return peorepository.getAllPeos();
    }


    private void addPeo(People... people){
        peorepository.addPeo(people);}

    private void delPeo(People... people) {
        peorepository.delPeo(people);
    }

    private People getPeo_id(int _id){return peorepository.getPeoForID(_id);}

    private User getNowUser(){return userRepository.getUser_login();}


    //——————————————————————————————封装的方法——————————————————————————————

    @SuppressLint("StaticFieldLeak")
    class getAllPEO extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            CloudPeo cloudPeo=new CloudPeo(getNowUser().getUID());
            //存在就更新
            for(People peo:cloudPeo.getPeos()){
                addPeo(peo);
            }
            return null;
        }
    }
}
