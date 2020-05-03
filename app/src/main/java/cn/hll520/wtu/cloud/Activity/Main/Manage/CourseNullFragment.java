package cn.hll520.wtu.cloud.Activity.Main.Manage;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.databinding.CourseFragmentBinding;

public class CourseNullFragment extends Fragment {

    private CourseNullViewModel mViewModel;
    private CourseFragmentBinding binding;

    public static CourseNullFragment newInstance() {
        return new CourseNullFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.course_null_fragment, container, false);
        binding=CourseFragmentBinding.bind(view);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel=new ViewModelProvider(this).get(CourseNullViewModel.class);
        //初始化
        intiDate();
    }


    //初始化当前时间
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void intiDate() {
        //显示周数
        binding.courseWeekNums.setText(mViewModel.week + "周");
        //显示日期
        binding.courseWeek1.append("\n" + mViewModel.getDate(1));
        binding.courseWeek2.append("\n" + mViewModel.getDate(2));
        binding.courseWeek3.append("\n" + mViewModel.getDate(3));
        binding.courseWeek4.append("\n" + mViewModel.getDate(4));
        binding.courseWeek5.append("\n" + mViewModel.getDate(5));
        binding.courseWeek6.append("\n" + mViewModel.getDate(6));
        binding.courseWeek7.append("\n" + mViewModel.getDate(7));
    }

}
