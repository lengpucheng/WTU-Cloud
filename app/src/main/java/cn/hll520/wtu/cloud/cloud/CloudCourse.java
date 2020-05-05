package cn.hll520.wtu.cloud.cloud;

import android.annotation.SuppressLint;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cn.hll520.wtu.cloud.link.DataLinkCourse;
import cn.hll520.wtu.cloud.model.UNCourse;

public class CloudCourse {
    //上传结果集
    public static class ResultLoad {
        public boolean isOK=false;
        public String MSG="";
    }
    //下载结果集
    public static class ResultDown{
        public boolean isOk=false;
        public List<UNCourse> UNCourses=null;
        public String MSG="";
    }

    //上传结果
    private MutableLiveData<ResultLoad> _restultLoad=new MutableLiveData<>();
    //下载结果
    private MutableLiveData<ResultDown> _restultDown=new MutableLiveData<>();

    public CloudCourse(){}

    //上传课表
    public LiveData<ResultLoad> upload(List<UNCourse> courses){new UPLoadCourse(courses).execute();return _restultLoad;}

    //下载课表
    public LiveData<ResultDown> getUNCourse(int OID){new DownCourse(OID).execute();return _restultDown;}

    /*
    * ——————————————————————————封装好的接口——————————————————————
    * */
    //上传
    @SuppressLint("StaticFieldLeak")
   private class UPLoadCourse extends AsyncTask<Void,Void,Void>{
        private List<UNCourse> list;
        UPLoadCourse(List<UNCourse> unCourses){this.list=unCourses;}

        @Override
        protected Void doInBackground(Void... Void) {
            DataLinkCourse link=new DataLinkCourse();
            ResultLoad resultLoad = new ResultLoad();
            resultLoad.isOK= link.uploadCourse(list);
            resultLoad.MSG=link.getMSG();
            _restultLoad.postValue(resultLoad);
            return null;
        }
    }

    //下载
    @SuppressLint("StaticFieldLeak")
    private class DownCourse extends AsyncTask<Void,Void,Void>{
        private int OID;
        DownCourse(int OID){this.OID=OID;}
        @Override
        protected Void doInBackground(Void... voids) {
            DataLinkCourse link=new DataLinkCourse();
            ResultDown resultDown=new ResultDown();
            List<UNCourse> unCourses=link.downCourse(OID);
            resultDown.isOk=unCourses!=null;
            resultDown.UNCourses=unCourses;
            resultDown.MSG=link.getMSG();
            _restultDown.postValue(resultDown);
            return null;
        }
    }

}
