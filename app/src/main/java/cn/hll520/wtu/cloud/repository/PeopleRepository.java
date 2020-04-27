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
        new DeleteAsyncTask(peopleDao).execute(people);
    }

    //更新
    public void updatePeo(People... people) {
        new UpdateAsyncTask(peopleDao).execute(people);
    }

    //查询唯一 _id
    public People getPeoForID(int _ID) { return peopleDao.getPeoForID(_ID); }

    //查询根据UID
    public People getPeoForUID(int UID){return  peopleDao.getPeoForUID(UID);}

    //插入，存在则跟新
    public void addPeo(People... people) {
        new AddAsyncTask(peopleDao).execute(people);
    }


    //登出清空
    public void out_login(){peopleDao.out_login();}
    /*——————————————————————————封装的AsynTask线程————————————————————————
     *————————————————一个异步线程  三个参数  对象，进度，结果————————————————
     */
    //添加去重
    public static class AddAsyncTask extends AsyncTask<People, Void, Void> {
        private PeopleDao peoDao;

        //构造线程
        AddAsyncTask(PeopleDao peoDao) {
            this.peoDao = peoDao;
        }

        @Override
        protected Void doInBackground(People... people) {
            for (People peo : people) {
                if (peoDao.getPeoForID(peo.get_ID()) == null)
                    peoDao.insertPeo(peo);
                else
                    peoDao.updatePeo(peo);
            }

            return null;
        }
    }

    //更新数据
    static class UpdateAsyncTask extends AsyncTask<People, Void, Void> {
        private PeopleDao peoDao;

        UpdateAsyncTask(PeopleDao peoDao) {
            this.peoDao = peoDao;
        }

        @Override
        protected Void doInBackground(People... peoples) {
            peoDao.updatePeo(peoples);
            return null;
        }
    }

    //删除数据
    static class DeleteAsyncTask extends AsyncTask<People, Void, Void> {
        private PeopleDao peoDao;

        DeleteAsyncTask(PeopleDao peoDao) {
            this.peoDao = peoDao;
        }

        @Override
        protected Void doInBackground(People... peoples) {
            peoDao.deletePeo(peoples);
            return null;
        }
    }


}
