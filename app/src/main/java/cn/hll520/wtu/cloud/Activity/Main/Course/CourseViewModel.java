package cn.hll520.wtu.cloud.Activity.Main.Course;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import cn.hll520.wtu.cloud.model.Course;
import cn.hll520.wtu.cloud.model.User;
import cn.hll520.wtu.cloud.model.dao.CourseDao;
import cn.hll520.wtu.cloud.model.dao.UserDao;
import cn.hll520.wtu.cloud.repository.CourseRepository;
import cn.hll520.wtu.cloud.repository.UserRepository;

//安卓ViewModel可以自动管理生命周期
public class CourseViewModel extends AndroidViewModel {

    int year;//年
    int month;//月
    int week;//周
    int weekDay;//星期
    int avg_width;//屏幕平均宽度
    private UserRepository userRepository;
    private CourseRepository courseRepository;
    private SharedPreferences preferences;
    private static final String SHP_COURSE = "Course";//shp文件名


    //构造函数
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CourseViewModel(@NonNull Application application) {
        super(application);
        userRepository=new UserRepository(application);
        courseRepository=new CourseRepository(application);
        //设置一个文件保存用户偏好————设置的周数
        preferences=application.getSharedPreferences(SHP_COURSE, Context.MODE_PRIVATE);
        //获取屏幕宽度，平均为8
        avg_width = application.getResources().getDisplayMetrics().widthPixels/ 8;
        //获取当前周数
        week= Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        //获取当前星期
        weekDay= Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        //判断用户是否设置了周数
        if(preferences.getBoolean("setWeek",false)){
            //如果设置了就获取当时的周数和时间
            int week_u= preferences.getInt("week",1);
            int year_u=preferences.getInt("year",2020);
            int month_u=preferences.getInt("moth",6);
            int day_u=preferences.getInt("day",6);
            //读取当时是第几周数
            Calendar calendar=new GregorianCalendar(year_u,month_u,day_u);
            int week_the=calendar.get(Calendar.WEEK_OF_YEAR);
            //实际周数为当前-第几周+那时周
            week=week-week_the+week_u;
        }
    }


    //获取编辑shp对象
    SharedPreferences getPreferences(){return preferences;}
    //获取全部课程
    LiveData<List<Course>> getWhoCourse(int UID){return courseRepository.getWhoCourse(UID);}
    //删除课程
    void delCourse(Course course){courseRepository.delCourse(course);}
    //获取当前的边距
    int getLeft(int i){return (i-1)*avg_width;}


    //获取星期几的日期
    @RequiresApi(api = Build.VERSION_CODES.N)
    String getDate(int ofDay) {
        ofDay=ofDay-weekDay;
        //取当前时间
        Date date = new Date();
        //生成日期对象
        Calendar calendar = new GregorianCalendar();
        //日期设置为当前时间
        calendar.setTime(date);
        //往后推一天.整数往后推,负数往前移动
        calendar.add(Calendar.DATE, ofDay);
        //这个时间就是日期往后推一天的结果
        date = calendar.getTime();
        //设置日期格式
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd");
        //返回当前日期
        return formatter.format(date);
    }

}
