package cn.hll520.wtu.cloud.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import cn.hll520.wtu.cloud.db.UserDatabase;
import cn.hll520.wtu.cloud.model.User;
import cn.hll520.wtu.cloud.model.dao.UserDao;



public class UserRepository {
    private UserDao userDao;

    //构造函数
    public UserRepository(Context context) {
        UserDatabase Database = UserDatabase.getDatabase(context.getApplicationContext());
        userDao=Database.getUserDao();
    }


    /**
     *
     */
    /*——————————————————————————封装的对外的使用接口——————————————————————————————
     *————————————————一个异步线程  三个参数  对象，进度，结果————————————————
     */
    //获取全部
    public LiveData<List<User>> getAllUser(){return userDao.getUserAll();}

    //获取根据登录状态
    public User getUser_login(){ return userDao.getUser_login(1); }
    public LiveData<User> getLogin_User(){return userDao.getLoginUser();}

    //获取用户从UID
    public User getUser_UID(int UID){return userDao.getUser_UID(UID);}

    //插入如果有重复就跟新
    public void insert(User... users){new InsertAsyncTask(userDao).execute(users);}

    //更新
    public void upUser(User... users){new UpdateAsyncTask(userDao).execute(users);}

    /**
     * 删除
     * @param users 可以使用其删除指定的USER
     */
    public void delUser(User... users){new DeleteAsyncTask(userDao).execute(users);}





    /*——————————————————————————封装的AsynTask线程————————————————————————
     *————————————————一个异步线程  三个参数  对象，进度，结果————————————————
     */
    //插入数据
    public static class InsertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;
        //构造线程
        InsertAsyncTask(UserDao userDao) {
            this.userDao=userDao;
        }

        //执行线程
        @Override
        protected Void doInBackground(User... users) {
         for(User user:users){
             if(userDao.getUser_UID(user.getUID())==null)
                 userDao.insertUser(user);
             else
                 userDao.updataUser(user);
         }
            return null;
        }
    }

    //更新数据
    static class UpdateAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        UpdateAsyncTask(UserDao userDao) {
            this.userDao=userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.updataUser(users);
            return null;
        }
    }

    //删除线程
    static class DeleteAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        DeleteAsyncTask(UserDao userDao) {
            this.userDao=userDao;
        }

        @Override
        protected Void doInBackground(User... users)  {
            userDao.deleteUser(users);
            return null;
        }
    }


}
