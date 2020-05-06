package cn.hll520.wtu.cloud.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import cn.hll520.wtu.cloud.db.UserDatabase;
import cn.hll520.wtu.cloud.model.People;
import cn.hll520.wtu.cloud.model.dao.PeopleDao;

public class PeopleRepository {
    private LiveData<List<People>> allPeos;
    private PeopleDao peopleDao;

    //构造函数
    public PeopleRepository(Context context) {
        UserDatabase peopleDatabase = UserDatabase.getDatabase(context.getApplicationContext());
        peopleDao = peopleDatabase.getPeoDao();
        allPeos = peopleDao.getPeoAll();
    }

    /*——————————————————————————封装的对外的使用接口——————————————————————————————
     *————————————————一个异步线程  三个参数  对象，进度，结果————————————————
     */
    //查询全部——————插入LiveData 自带了AsyncTask，不需要在定义线程实现
    public LiveData<List<People>> getAllPeos() {
        return allPeos;
    }

    //删除
    public void delPeo(People... people) {
        new delete_mpl(peopleDao).execute(people);
    }

    //更新
    public void updatePeo(People... people) {
        new update_mpl(peopleDao).execute(people);
    }

    //查询唯一 _id
    public LiveData<People> getInfo_ID(int _ID){return peopleDao.getInfoForID(_ID);}

    //查询根据UID
    public LiveData<People> getInfo_UID(int UID){return peopleDao.getInfoForUID(UID);}

    //插入，存在则跟新
    public void addPeo(List<People> people) { new addPeo_mpl(peopleDao,people).execute(); }

    //登出清空
    public void outLogin(){new outLogin_mpl(peopleDao).execute();}


    /*——————————————————————————封装的AsynTask线程————————————————————————
     *————————————————一个异步线程  三个参数  对象，进度，结果————————————————
     */
    //添加去重
    static class addPeo_mpl extends AsyncTask<Void,Void,Void>{
        private List<People> peoples;
        private PeopleDao peoDao;
        addPeo_mpl(PeopleDao dao,List<People> peoples){
            this.peoDao=dao;
            this.peoples=peoples;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            for(People people:peoples){
                if (peoDao.getPeoForID(people.get_ID()) == null)
                    peoDao.insertPeo(people);
                else
                    peoDao.updatePeo(people);
            }
            return null;
        }
    }



    //更新数据
    static class update_mpl extends AsyncTask<People, Void, Void> {
        private PeopleDao peoDao;
        update_mpl(PeopleDao peoDao) {
            this.peoDao = peoDao;
        }
        @Override
        protected Void doInBackground(People... peoples) {
            peoDao.updatePeo(peoples);
            return null;
        }
    }

    //删除数据
    static class delete_mpl extends AsyncTask<People, Void, Void> {
        private PeopleDao peoDao;

        delete_mpl(PeopleDao peoDao) {
            this.peoDao = peoDao;
        }

        @Override
        protected Void doInBackground(People... peoples) {
            peoDao.deletePeo(peoples);
            return null;
        }
    }

    //清空
    static class outLogin_mpl extends AsyncTask<Void,Void,Void>{
        private PeopleDao peoDao;

        outLogin_mpl(PeopleDao peoDao) {
            this.peoDao = peoDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            peoDao.out_login();
            return null;
        }
    }


}
