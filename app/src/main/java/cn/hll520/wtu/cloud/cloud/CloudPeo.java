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
    //联系人列表
    public static class ResultPeos {
        public boolean isOk=false;
        public List<People> peoples;
        public String MSG="";
    }
    private MutableLiveData<ResultPeos> _resultPeos=new MutableLiveData<>();

    //联系人
    public  static class ResultPeo{
        boolean isOK=false;
        public People people;
        public String MSG="";
    }
    private MutableLiveData<ResultPeo> _resultPeo=new MutableLiveData<>();

    //修改资料
    public  static class Result{
        public boolean isOk=false;
        public boolean result;
        public String MSG="";
    }
    private MutableLiveData<Result> _result=new MutableLiveData<>();

    public CloudPeo(){}
    /*
    * ————————————————————获取结果集——————————————————————————————
    * */
    //联系人列表
    public LiveData<ResultPeos> getResultPeos(){return _resultPeos;}
    //单个联系人
    public LiveData<ResultPeo> getResultPeo(){return _resultPeo;}
    //操作结果
    public LiveData<Result> getResult(){return _result;}

    /*
     * ——————————————————————————————————封装的对外登口——————————————————————————
     * */
    //获取全部联系人列表
    public void getPeoAll(int UID){new getPeoAll_mpl(UID).execute();}

    //获取单个联系人
    public void getPeo(int UID){new getPeo_mpl(UID).execute();}

    //修改资料
    public void updataPeo(People people){new updataPeo_mpl(people).execute();}


    /*
     * ——————————————————————————————————封装的实现方法——————————————————————————
     * */
    //获取全部联系人
    @SuppressLint("StaticFieldLeak")
    private class getPeoAll_mpl extends AsyncTask<Void,Void,Void>{
        private int UID;
        getPeoAll_mpl(int UID){this.UID=UID;}
        @Override
        protected Void doInBackground(Void... voids) {
            DataLinkPeo link=new DataLinkPeo();
            ResultPeos result=new ResultPeos();
            result.isOk= true;
            result.peoples= Collections.unmodifiableList(link.getPeoples(UID));
            result.MSG=link.getMSG();
            _resultPeos.postValue(result);
            return null;
        }
    }

    //获取单个联系人
    @SuppressLint("StaticFieldLeak")
    private class getPeo_mpl extends AsyncTask<Void, Void,Void>{
        private int UID;
        getPeo_mpl(int UID){this.UID=UID;}
        @Override
        protected Void doInBackground(Void... voids) {
            DataLinkPeo link=new DataLinkPeo();
            ResultPeo result=new ResultPeo();
            result.isOK=true;
            result.people=link.getPeo(UID);
            result.MSG=link.getMSG();
            _resultPeo.postValue(result);
            return null;
        }
    }

    //修改资料
    @SuppressLint("StaticFieldLeak")
    private class updataPeo_mpl extends AsyncTask<Void, Void,Void>{
        private People people;
        updataPeo_mpl(People people){this.people=people;}
        @Override
        protected Void doInBackground(Void... voids) {
            DataLinkPeo link=new DataLinkPeo();
            Result result=new Result();
            result.isOk=true;
            result.result=link.updataPeo(people);
            result.MSG=link.getMSG();
            _result.postValue(result);
            return null;
        }
    }

}
