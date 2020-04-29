package cn.hll520.wtu.cloud.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import cn.hll520.wtu.cloud.db.UserDatabase;
import cn.hll520.wtu.cloud.model.Course;
import cn.hll520.wtu.cloud.model.dao.CourseDao;

public class CourseRepository {
    private CourseDao courseDao;
    //构造函数
    public CourseRepository(Context context) {
        UserDatabase Database = UserDatabase.getDatabase(context.getApplicationContext());
        courseDao=Database.getCourseDao();
    }

    //————————————————————————————————接口——————————————————————————

    //获取全部课程
    public LiveData<List<Course>> getAllCourse(){return courseDao.getCourseAll();}

    //获取根据用户名
    public LiveData<List<Course>> getWhoCourse(int who){return courseDao.getCourseWho(who);}
    //删除全部
    public void delWhoCourse(int who){courseDao.deleAllWho(who);}
    //添加
    public void addCourse(Course... courses){
        new AddCourse(courseDao).execute(courses);}
    //更新
    public void updataCourse(Course... courses){
        new UpdataCourse(courseDao).execute(courses);}
    //删除
    public void delCourse(Course... courses){
        new DelCourse(courseDao).execute(courses);}




    /*
    * ——————————————————————————————封装的实现——————————————————————————————
    * */

    //添加
    static class AddCourse extends AsyncTask<Course,Void,Void>{
        CourseDao courseDao;

        AddCourse(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.insertCourse(courses);
            return null;
        }
    }

    //删除
    static class DelCourse extends AsyncTask<Course,Void,Void>{
        CourseDao courseDao;
        DelCourse(CourseDao courseDao){this.courseDao=courseDao;}
        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.deleteCourse(courses);
            return null;
        }
    }

    //更新
    static class UpdataCourse extends AsyncTask<Course,Void,Void>{
        CourseDao courseDao;
        UpdataCourse(CourseDao courseDao){this.courseDao=courseDao;}
        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.updateCourse(courses);
            return null;
        }
    }

}
