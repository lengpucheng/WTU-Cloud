package cn.hll520.wtu.cloud.cloud;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import cn.hll520.wtu.cloud.link.DataLinkCourse;
import cn.hll520.wtu.cloud.model.UNCourse;

public class CloudCourse {
    private String MSG="";
    private boolean flag;
    public CloudCourse(){};

    //上传课表
    public boolean upload(List<UNCourse> courses){new UPLoadCourse(courses).execute();return flag;}

    //获取错误信息
    public String getMSG(){return MSG;}


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
            flag = link.uploadCourse(list);
            MSG=link.getMSG();
            return null;
        }
    }

}
