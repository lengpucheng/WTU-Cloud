package cn.hll520.wtu.cloud.cloud;

import android.annotation.SuppressLint;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cn.hll520.wtu.cloud.link.DataLinkCourse;
import cn.hll520.wtu.cloud.model.UNCourse;

public class CloudCourse {
    public static class Result{
        public boolean isOK=false;
        public String MSG="";
    }
    //结果
    private MutableLiveData<Result> _restult=new MutableLiveData<>();

    public CloudCourse(){}

    //上传课表
    public LiveData<Result> upload(List<UNCourse> courses){new UPLoadCourse(courses).execute();return _restult;}


    /*
    * ——————————————————————————封装好的接口
    * */
    @SuppressLint("StaticFieldLeak")
    class UPLoadCourse extends AsyncTask<Void,Void,Void>{
        private List<UNCourse> list;
        UPLoadCourse(List<UNCourse> unCourses){this.list=unCourses;}

        @Override
        protected Void doInBackground(Void... Void) {
            DataLinkCourse link=new DataLinkCourse();
            Result result= new Result();
            result.isOK= link.uploadCourse(list);
            result.MSG=link.getMSG();
            _restult.postValue(result);
            return null;
        }
    }

}
