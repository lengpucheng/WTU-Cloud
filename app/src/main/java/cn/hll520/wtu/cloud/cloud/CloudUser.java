package cn.hll520.wtu.cloud.cloud;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

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
    //获取组织结果
    public static class ResultOrg{
        public boolean isOk=false;     //获取成功否
        public List<ContentValues> org;
        public String MSG="";
    }
    //获取部门结果
    public static class ResultMent{
        public boolean isOK=false;     //获取成功否
        public List<ContentValues> ment;
        public String MSG="";
    }


    //登录结果
    private MutableLiveData<ResultLogin> _resultLogin=new MutableLiveData<>();
    public LiveData<ResultLogin> getResultLogin(){return _resultLogin;}    //获取结果

    //组织结果
    private MutableLiveData<ResultOrg> _resultOrg=new MutableLiveData<>();
    public LiveData<ResultOrg> getResultOrg(){return _resultOrg;}

    //部门结果
    private MutableLiveData<ResultMent> _resultMent=new MutableLiveData<>();
    public LiveData<ResultMent> getResultMent(){return _resultMent;}
    /*
     * ——————————————————————————————————封装的对外接口——————————————————————————
     * */
    //登录
    public void doLogin(User user){new login_mpl(user).execute();}

    //获取组织名单
    public void setOrg(int UID){new setOrg_mpl(UID).execute();}
    //获取部门名单
    public void setMent(int OID){new setMent_mpl(OID).execute();}



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

    //获取组织信息
    @SuppressLint("StaticFieldLeak")
    private class setOrg_mpl extends AsyncTask<Void,Void,Void>{
        private int UID;
        setOrg_mpl(int UID){this.UID=UID;}
        @Override
        protected Void doInBackground(Void... voids) {
            DataLinkUser link=new DataLinkUser();
            List<ContentValues> organAndName = link.getOrganAndName(UID);
            //获取结果
            ResultOrg result=new ResultOrg();
            result.isOk =organAndName!=null;
            result.org =organAndName;
            result.MSG=link.getMsg();
            _resultOrg.postValue(result);
            return null;
        }
    }

    //获取部门信息
    @SuppressLint("StaticFieldLeak")
    private class setMent_mpl extends AsyncTask<Void,Void,Void>{
        private int OID;
        setMent_mpl(int OID){this.OID=OID;}
        @Override
        protected Void doInBackground(Void... voids) {
            DataLinkUser link=new DataLinkUser();
            List<ContentValues> values = link.getMentAndName(OID);
            //获取结果
            ResultMent result=new ResultMent();
            result.isOK=values!=null;
            result.ment =values;
            result.MSG=link.getMsg();
            _resultMent.postValue(result);
            return null;
        }
    }
}
