package cn.hll520.wtu.cloud.Activity.Main.Course;

import android.annotation.SuppressLint;
import android.app.Application;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import java.util.List;

import cn.hll520.wtu.cloud.model.Course;
import cn.hll520.wtu.cloud.repository.CourseRepository;
import cn.hll520.wtu.cloud.repository.UserRepository;
import cn.hll520.wtu.cloud.util.JWhelper;

public class CourseLoginViewModel extends AndroidViewModel {
    String year;//年
    String semester;//学期
    String sid;//学号
    String pass;//密码
    String check;//验证码
    final String LOGIN_OK="登录完成";
    private JWhelper helper;
    private MutableLiveData<Bitmap> _IMG=new MutableLiveData<>();
    private MutableLiveData<String> _MSG=new MutableLiveData<>();
    private UserRepository userRepository;
    private CourseRepository courseRepository;

    public CourseLoginViewModel(@NonNull Application application) {
        super(application);
        userRepository=new UserRepository(application);
        courseRepository=new CourseRepository(application);
    }

    //获取验证码
    LiveData<Bitmap> getIMG(){return _IMG;}
    //获取提示信息
    LiveData<String> getMSG(){return _MSG;}
    //登录
    void login(){new loginJWXT().execute();}
    //打开页面
    void open(){helper=new JWhelper();new startJWXT().execute();}

    //启动
    @SuppressLint("StaticFieldLeak")
    class startJWXT extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            //如果获取验证码成功就显示
            if(helper.isCheckIMG())
                _IMG.postValue(helper.getCheckIMG());
            return null;
        }
    }

    //登录
    @SuppressLint("StaticFieldLeak")
    class loginJWXT extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... voids) {
            //获取当前登录用户UID
            int UID=userRepository.getUser_login().getUID();
            //模拟登录，失败则修改提示信息
            if(helper.login(sid, pass, check))
                if(helper.getURI())//获取登录后的页面
                    if(helper.isCourse(year,semester,UID)){//获取课表
                        //清空以及存在的课表
                        courseRepository.delWhoCourse(UID);
                        //得到课表
                        List<Course> courses=helper.getCourses();
                        //写入课表
                        for(Course course:courses)
                            courseRepository.addCourse(course);
                        _MSG.postValue("登录完成");
                        return null;
                    }
            _MSG.postValue(helper.getMSG());
            return null;
        }
    }


}
