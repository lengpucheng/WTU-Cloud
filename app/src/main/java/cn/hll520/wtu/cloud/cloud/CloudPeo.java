package cn.hll520.wtu.cloud.cloud;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Collections;
import java.util.List;

import cn.hll520.wtu.cloud.link.DataLinkPeo;
import cn.hll520.wtu.cloud.model.People;

public class CloudPeo {
    public static class ResultPeo{
        public boolean isOk=false;
        public List<People> peoples;
        public String MSG="";
    }
    private MutableLiveData<ResultPeo> _resultPeo=new MutableLiveData<>();




    public CloudPeo(){}
    /*
    * ————————————————————获取结果集——————————————————————————————
    * */
    //联系人列表
    public LiveData<ResultPeo> getResultPeo(){return _resultPeo;}


    /*
     * ——————————————————————————————————封装的对外登口——————————————————————————
     * */
    //获取全部联系人列表
    public void getPeoAll(int UID){new getPeo_mpl(UID).execute();}


    /*
     * ——————————————————————————————————封装的实现方法——————————————————————————
     * */
    @SuppressLint("StaticFieldLeak")
    private class getPeo_mpl extends AsyncTask<Void,Void,Void>{
        private int UID;
        getPeo_mpl(int UID){this.UID=UID;}
        @Override
        protected Void doInBackground(Void... voids) {
            DataLinkPeo link=new DataLinkPeo();
            ResultPeo result=new ResultPeo();
            result.peoples= Collections.unmodifiableList(link.getPeoples(UID));
            result.isOk=true;
            result.MSG=link.getMSG();
            _resultPeo.postValue(result);
            return null;
        }
    }



}
