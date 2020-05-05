package cn.hll520.wtu.cloud.cloud;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import cn.hll520.wtu.cloud.link.DataLinkUser;
import cn.hll520.wtu.cloud.model.User;

public class CloudUser {
    //登录结果集
    public static class ResultLogin{
        public boolean isSuccess=false;
        public User user;
        public String time;
        public String MSG="";//错误信息
    }
    //登录结果
    private MutableLiveData<ResultLogin> _resultLogin=new MutableLiveData<>();

    //获取结果
    public LiveData<ResultLogin> getResultLogin(){return _resultLogin;}


    /*
     * ——————————————————————————————————封装的对我登口——————————————————————————
     * */
    //登录
    public void doLogin(User user){new login_mpl(user).execute();}


    /*
    * ——————————————————————————————————封装的实现方法——————————————————————————
    * */
    //登录
    @SuppressLint("StaticFieldLeak")
    private class login_mpl extends AsyncTask<Void,Void,Void>{
        private User user;
        login_mpl(User user){this.user=user;}
        @Override
        protected Void doInBackground(Void... voids) {
            DataLinkUser link=new DataLinkUser();
            ResultLogin result= new ResultLogin();
            if(user.getUID()==0)
                result.isSuccess=link.login(user.getUname(),user.getPassword());
            else
                result.isSuccess=link.login(user.getUID(),user.getPassword());
            result.user=link.getUser();
            result.time=link.getTime();
            result.MSG=link.getMsg();
            _resultLogin.postValue(result);
            return null;
        }
    }

}
