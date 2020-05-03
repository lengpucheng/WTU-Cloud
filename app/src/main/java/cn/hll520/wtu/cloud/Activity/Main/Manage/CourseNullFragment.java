package cn.hll520.wtu.cloud.Activity.Main.Manage;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.cloud.CloudCourse;
import cn.hll520.wtu.cloud.databinding.CourseFragmentBinding;
import cn.hll520.wtu.cloud.databinding.CourseNullFragmentBinding;
import cn.hll520.wtu.cloud.model.Course;
import cn.hll520.wtu.cloud.model.UNCourse;

public class CourseNullFragment extends Fragment {

    private CourseNullViewModel mViewModel;
    private CourseNullFragmentBinding binding;

    public static CourseNullFragment newInstance() {
        return new CourseNullFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.course_null_fragment, container, false);
        binding=CourseNullFragmentBinding.bind(view);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel=new ViewModelProvider(this).get(CourseNullViewModel.class);
        //初始化
        intiDate();

        //获取空课表
        mViewModel.getUNCourse().observe(getViewLifecycleOwner(), new Observer<CloudCourse.ResultDown>() {
            @Override
            public void onChanged(CloudCourse.ResultDown resultDown) {
                if(!resultDown.isOk){
                    Toast.makeText(requireContext(), "获取空课表失败！"+resultDown.MSG, Toast.LENGTH_SHORT).show();
                    return;
                }
                //显示空课表
                showUNCourse(resultDown.UNCourses);
            }
        });


    }

    private void showUNCourse(List<UNCourse> unCourses) {
        //生成课表占位符
        TextView[][] textViews = new TextView[7][5];
        //初始化位置
        for (int i = 0; i < 7; i++) {
            for(int j=0;j<5;j++){
                textViews[i][j]=new TextView(requireContext());
                int drawable;
                switch ((i+j)%4){
                    case 0:drawable=R.drawable.course_bk_1_ripple;break;
                    case 1:drawable=R.drawable.course_bk_2_ripple;break;
                    case 2:drawable=R.drawable.course_bk_3_ripple;break;
                    case 3:drawable=R.drawable.course_bk_4_ripple;break;
                    default:drawable=R.drawable.course_bk_noweek_ripple;break;
                }
                //设置背景
                textViews[i][j].setBackgroundResource(drawable);
                //设置字体大小
                textViews[i][j].setTextSize(10);
                //设置高度
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mViewModel.avg_width, dip2px(150));
                //定位位置
                params.setMargins(i * mViewModel.avg_width,dip2px(150*j) , 0, 0);
                //应用
                textViews[i][j].setLayoutParams(params);
                //添加
                binding.courseNull.addView(textViews[i][j]);
            }
        }

        //添加人员
        for(UNCourse uncourse:unCourses){
            addPeople(uncourse,textViews[uncourse.getWeek()-1]);
        }
    }


    private void addPeople(UNCourse unCourse, TextView[] textView) {
        //根据周数，将人员添加到其中
        for (int i = 0; i < 5; i++)
            if (mViewModel.week <unCourse.getMax_Min(i)[0] ||mViewModel.week > unCourse.getMax_Min(i)[1])
                textView[i].append(unCourse.getName() + "\n");
    }

    //初始化当前时间
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void intiDate() {
        //显示周数
        binding.courseNullWeekNums.setText(mViewModel.week + "周");
        //显示日期
        binding.courseWeek1.append("\n" + mViewModel.getDate(1));
        binding.courseWeek2.append("\n" + mViewModel.getDate(2));
        binding.courseWeek3.append("\n" + mViewModel.getDate(3));
        binding.courseWeek4.append("\n" + mViewModel.getDate(4));
        binding.courseWeek5.append("\n" + mViewModel.getDate(5));
        binding.courseWeek6.append("\n" + mViewModel.getDate(6));
        binding.courseWeek7.append("\n" + mViewModel.getDate(7));
    }


    //dp转px
    private int dip2px(float dpValue) {
        Context context = requireContext();
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
