package cn.hll520.wtu.cloud.Activity.Main.Course;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.databinding.CourseFragmentBinding;
import cn.hll520.wtu.cloud.model.Course;

public class CourseFragment extends Fragment {

    private CourseViewModel mViewModel;
    private CourseFragmentBinding binding;
    private ImageView menuBar;
    private NavController controller;
    private List<Course> courses_temp;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //获取视图
        View view=inflater.inflate(R.layout.course_fragment, container, false);
        binding=CourseFragmentBinding.bind(view);//绑定
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //获取导航控制器，参数为当前页面
        controller = Navigation.findNavController(requireView());
        mViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        TextView Tile=requireActivity().findViewById(R.id.MainTile);
        Tile.setText("课表");
        //初始化当前时间
        intiDate();
        //添加菜单
        menuBar =requireActivity().findViewById(R.id.mainAdd);
        //点击显示菜单
        menuBar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                showMenu(menuBar);
            }
        });
        //点击设置周数
        binding.CourseSetWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWeek();
            }
        });
        //监听课表
        mViewModel.getWhoCourse().observe(getViewLifecycleOwner(), new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                //缓存
                courses_temp=courses;
                //如果课程过时提示导入课程
                if(courses.size()<2){
                    AlertDialog.Builder builder=new AlertDialog.Builder(requireContext()).setTitle("提示")
                            .setIcon(R.mipmap.ic_launcher).setMessage("当前课程较少，请导入或手动添加课程")
                            .setPositiveButton("导入课程", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    controller.navigate(R.id.action_courseFragment_to_courseLoginFragment);
                                }
                            })
                            .setNegativeButton("知道了",null);
                    builder.show();
                }
                showCourse(courses);
            }
        });
    }

    //显示课程
    private void showCourse(List<Course> courses) {
        //移除原有全部
        binding.courseClass.removeAllViews();
        showNowDay();
        for(final Course course:courses){
            //新建一个文本用来显示课程
            TextView theClass=new TextView(requireContext());
            //文本内的字符串
            String val="";
            //获取当前课的周期
            int wMIN=course.getWmin();
            int wMax=course.getWmax();
            //如果非本周
            if(wMIN>mViewModel.week||wMax<mViewModel.week){
                val+="[非本周]";
                //设置为灰色
                theClass.setBackgroundColor(0xcccccccc);
            }
            //拼接信息
            val+=course.getName();
            val+="@"+course.getRoom();
            val+="#"+course.getTeacher()+course.getJob();
            val+="|"+course.getTest();
            //横跨几节
            int high=course.getTmax()-course.getTmin()+1;
            //设置大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mViewModel.avg_width, dip2px(high * 65));
            //设置位置
            params.setMargins(mViewModel.getLeft(course.getWeek()),//设置左边距，即周几
                    dip2px((course.getTmin()-1) * 65),//设置上边距，即第节开始
                    0, 0);
            //应用设置
            theClass.setLayoutParams(params);
            //设置字体大小和颜色
            theClass.setTextSize(11);
            theClass.setTextColor(Color.WHITE);
            //结尾设置省略号
            theClass.setEllipsize(TextUtils.TruncateAt.END);
            //添加内容到文本
            theClass.setText(val);
            //添加到页面
            binding.courseClass.addView(theClass);
            //添加点击事件
            theClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCourseInfo(course);
                }
            });
        }
    }


    //显示详细信息
    private void showCourseInfo(final Course course) {
        AlertDialog.Builder builder=new AlertDialog.Builder(requireContext()).setTitle(course.getName())
                .setMessage(course.showInfo())
                .setPositiveButton("知道了",null)
                .setNegativeButton("编辑", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        controller.navigate(R.id.action_courseFragment_to_courseEditFragment);
                    }
                }).setNeutralButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delClass(course);
                    }
                });
        builder.show();
    }

    private void delClass(final Course course) {
        AlertDialog.Builder check = new AlertDialog.Builder(requireContext()).setTitle("确定删除Σ(ﾟдﾟ;)？")
                .setPositiveButton("我点错了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(requireContext(), "哈哈！每个人都有手滑的时候\n已经帮你取消删除啦ε=ε=(ノ≧∇≦)ノ", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("确定删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mViewModel.delCourse(course);
                        Toast.makeText(requireContext(), "删除成功（￣▽￣）", Toast.LENGTH_SHORT).show();
                    }
                });
        check.create().show();
    }

    //初始化当前时间
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void intiDate() {
        //显示周数
        binding.courseWeekNums.setText(mViewModel.week +"周");
        //显示日期
        binding.courseWeek1.append("\n"+mViewModel.getDate(1));
        binding.courseWeek2.append("\n"+mViewModel.getDate(2));
        binding.courseWeek3.append("\n"+mViewModel.getDate(3));
        binding.courseWeek4.append("\n"+mViewModel.getDate(4));
        binding.courseWeek5.append("\n"+mViewModel.getDate(5));
        binding.courseWeek6.append("\n"+mViewModel.getDate(6));
        binding.courseWeek7.append("\n"+mViewModel.getDate(7));
    }

    private void showNowDay() {
        //新建一个空文本框作为背景
        TextView dayNow = new TextView(requireContext());
        //设置大小
        LinearLayout.LayoutParams paramNow = new LinearLayout.LayoutParams(mViewModel.avg_width, dip2px(14 * 65));
        //设置边距
        paramNow.setMargins(mViewModel.getLeft(mViewModel.weekDay), 0, 0, 0);
        //将样式运用到文本框
        dayNow.setLayoutParams(paramNow);
        //设置背景
        dayNow.setBackgroundColor(0x40E91E63);
        //添加到界面
        binding.courseClass.addView(dayNow);
    }


    //显示菜单
    private void showMenu(final View view) {
        PopupMenu popupMenu=new PopupMenu(getContext(),view);
        popupMenu.getMenuInflater().inflate(R.menu.course_menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.course_menu_impro:
                        //设置动作
                        controller.navigate(R.id.action_courseFragment_to_courseLoginFragment);
                        break;
                    case R.id.course_menu_add:
                        //设置动作
                        controller.navigate(R.id.action_courseFragment_to_courseEditFragment);
                        break;
                    case  R.id.course_menu_setweek:
                        setWeek();
                        break;
                    case R.id.course_menu_uploda:

                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void setWeek() {
        final Spinner spinner = new Spinner(requireContext());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item);
        // 添加到数组
        for (int i = 1; i < 32; i++)
        {
            dataAdapter.add("第" + i + "周");
        }
        //添加适配器
        spinner.setAdapter(dataAdapter);
        //默认选第一个
        spinner.setSelection(0, true);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(requireContext()).setTitle("设置当前周数").setView(spinner).setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int week = Integer.parseInt(spinner.getSelectedItem().toString().replaceAll("[^\\d]", ""));
                SharedPreferences.Editor editor = mViewModel.getPreferences().edit();
                editor.putBoolean("setWeek", true);
                editor.putInt("week", week);
                editor.putInt("year", Calendar.getInstance().get(Calendar.YEAR));
                editor.putInt("moth", Calendar.getInstance().get(Calendar.MONTH));
                editor.putInt("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                editor.apply();
                Toast.makeText(requireContext(), "设置成功", Toast.LENGTH_SHORT).show();
                    showCourse(courses_temp);
                binding.courseWeekNums.setText(week+"周");
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(requireContext(), "取消设置", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }


    //dp转px
    private int dip2px(float dpVal) {
        final float scale = requireContext().getResources().getDisplayMetrics().density;
        return (int) (dpVal * scale + 0.5f);
    }

}
