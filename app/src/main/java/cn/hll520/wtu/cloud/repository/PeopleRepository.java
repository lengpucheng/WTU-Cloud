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
    public LiveData<List<People>> getAllPeos() { return allPeos; }
    //插入
    public void insertPeo(People... people) {
        new InsertAsyncTask(peopleDao).execute(people);
    }
    //删除
    public void delPeo(People... people) {
        new DeleteAsyncTask(peopleDao).execute(people);
    }
    //更新
    public void updatePeo(People... people) {
        new UpdateAsyncTask(peopleDao).execute(people);
    }





    /*——————————————————————————封装的AsynTask线程————————————————————————
    *————————————————一个异步线程  三个参数  对象，进度，结果————————————————
    */

    //插入数据
    public static class InsertAsyncTask extends AsyncTask<People, Void, Void> {
        private PeopleDao peoDao;
        //构造线程
        InsertAsyncTask(PeopleDao peoDao) {
            this.peoDao = peoDao;
        }

        //执行线程
        @Override
        protected Void doInBackground(People... peoples) {
            peoDao.insertPeo(peoples);
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
