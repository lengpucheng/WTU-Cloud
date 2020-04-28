package cn.hll520.wtu.cloud.Activity.Main;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import cn.hll520.wtu.cloud.model.People;
import cn.hll520.wtu.cloud.model.User;
import cn.hll520.wtu.cloud.repository.PeopleRepository;
import cn.hll520.wtu.cloud.repository.UserRepository;


public class MycenterViewModel extends AndroidViewModel {
    private PeopleRepository peopleRepository;
    private UserRepository userRepository;
    private MutableLiveData<User> _user=new MutableLiveData<>();
    private MutableLiveData<People> _peo=new MutableLiveData<>();

    public MycenterViewModel(@NonNull Application application) {
        super(application);
        peopleRepository=new PeopleRepository(application);
        userRepository=new UserRepository(application);
    }
    //获取当前用户

    void out_login(){new OUTUSER().execute();}

    LiveData<User> getUser(){ return _user;}

    LiveData<People> getInfo(){return _peo;}

    void iniData(){new GETUSER().execute();}
    /*
    * ____________封装的类————————————————————————
    * */
    //获取登录用户
    @SuppressLint("StaticFieldLeak")
    class GETUSER extends  AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            User user=userRepository.getUser_login();
            _user.postValue(user);
            People people=peopleRepository.getPeoForUID(user.getUID());
            _peo.postValue(people);
            return null;
        }
    }


    //退出
    @SuppressLint("StaticFieldLeak")
    class OUTUSER extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            User user=userRepository.getUser_login();
            user.setLogin(false);
            userRepository.upUser(user);
            peopleRepository.out_login();
            return null;
        }
    }



}
