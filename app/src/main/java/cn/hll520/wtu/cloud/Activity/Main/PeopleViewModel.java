package cn.hll520.wtu.cloud.Activity.Main;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import cn.hll520.wtu.cloud.cloud.CloudPeo;
import cn.hll520.wtu.cloud.cloud.CloudUser;
import cn.hll520.wtu.cloud.model.People;
import cn.hll520.wtu.cloud.model.User;
import cn.hll520.wtu.cloud.repository.PeopleRepository;

public class PeopleViewModel extends AndroidViewModel {
    private PeopleRepository repository;


    //构造函数
    public PeopleViewModel(@NonNull Application application) {
        super(application);
        //得到一个People使用接口
        repository = new PeopleRepository(application);
    }

    void getPEO(){
        new getAllPEO().execute();
    }


    //————————————————————————对外提供的管理数据
    LiveData<List<People>> getAllPeos() {
        return repository.getAllPeos();
    }

    void insertPeo(People... people) {
        repository.insertPeo(people);
    }

    void delPeo(People... people) {
        repository.delPeo(people);
    }

    void updatePeo(People... people) {
        repository.updatePeo(people);
    }


    //——————————————————————————————封装的方法——————————————————————————————

    class getAllPEO extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            CloudPeo cloudPeo=new CloudPeo(1001);
            for(People peo:cloudPeo.getPeos()){
                insertPeo(peo);
            }
            return null;
        }
    }
}
