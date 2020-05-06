package cn.hll520.wtu.cloud.Activity.Main.Manage;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.cloud.CloudCourse;
import cn.hll520.wtu.cloud.cloud.CloudUser;
import cn.hll520.wtu.cloud.databinding.CourseNullFragmentBinding;
import cn.hll520.wtu.cloud.model.UNCourse;
import cn.hll520.wtu.cloud.model.User;

public class CourseNullFragment extends Fragment {

    private CourseNullViewModel mViewModel;
    private CourseNullFragmentBinding binding;
    private List<UNCourse> unCourses_temp;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_null_fragment, container, false);
        binding = CourseNullFragmentBinding.bind(view);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CourseNullViewModel.class);
        //修改标题
        TextView Tile = requireActivity().findViewById(R.id.MainTile);
        Tile.setText("空课表");
        //初始化界面
        intiDate();
        //初始化选项框
        intiSpinner();
        //监听空课表
        mViewModel.getResult().observe(getViewLifecycleOwner(), new Observer<CloudCourse.ResultDown>() {
            @Override
            public void onChanged(CloudCourse.ResultDown resultDown) {
                if (!resultDown.isOk) {
                    Toast.makeText(requireContext(), "获取空课表失败！" + resultDown.MSG, Toast.LENGTH_SHORT).show();
                    return;
                }
                //缓存课表
                unCourses_temp = resultDown.UNCourses;
                //显示空课表
                showUNCourse(resultDown.UNCourses);
            }
        });
        //监听登录人员
        mViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                //获取组织列表
                mViewModel.loadOrg(user.getUID());
            }
        });
        //设置周数
        binding.CourseNullSetWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWeek();
            }
        });

    }

    //初始化组织部门
    private void intiSpinner() {
        //监听组织的选项
        mViewModel.getOrgan().observe(getViewLifecycleOwner(), new Observer<CloudUser.ResultOrg>() {
            @Override
            public void onChanged(final CloudUser.ResultOrg resultOrg) {
                //判断组织加载是否成功
                if (!resultOrg.isOk) {
                    Toast.makeText(requireContext(), "获取组织列表失败" + resultOrg.MSG, Toast.LENGTH_SHORT).show();
                    binding.courseNullMent.setEnabled(false);
                    return;
                }
                //加载组织
                loadOrg(resultOrg.org);
            }
        });
        //监听部门选项
        mViewModel.getMent().observe(getViewLifecycleOwner(), new Observer<CloudUser.ResultMent>() {
            @Override
            public void onChanged(CloudUser.ResultMent resultMent) {
                //部门加载
                if(!resultMent.isOK){
                    Toast.makeText(requireContext(), "获取部门列表失败" + resultMent.MSG, Toast.LENGTH_SHORT).show();
                    binding.courseNullMent.setEnabled(false);
                    return;
                }
                //加载部门
                loadMent(resultMent.ment);
            }
        });
    }

    //加载部门
    private void loadMent(final List<ContentValues> values) {
        ArrayAdapter<String> mentAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item);
        //默认第一个为全部
        mentAdapter.add("全部");
        if(values.size()>0) {
            for (ContentValues cv : values)
                mentAdapter.add(cv.getAsString("NAME"));
            binding.courseNullMent.setEnabled(true);
        }else
            binding.courseNullMent.setEnabled(false);
        binding.courseNullMent.setAdapter(mentAdapter);
        binding.courseNullMent.setSelection(0);
        binding.courseNullMent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取课表，注意第一个是全部
                if(position==0)
                    mViewModel.downUNCourse(mViewModel.OID);
                else
                    mViewModel.downUNCourse(values.get(position-1).getAsInteger("MID"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //加载选项
    private void loadOrg(final List<ContentValues> values ) {
        //生成应该数组适配器
        ArrayAdapter<String> orgAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item);
        if (values.size() > 0) {
            //加载列表
            for (ContentValues cv : values)
                orgAdapter.add(cv.getAsString("NAME"));
            //启用选项
            binding.courseNullOrg.setEnabled(true);
        }else{
            orgAdapter.add("暂无");
            binding.courseNullOrg.setEnabled(false);
        }
        //设置适配器
        binding.courseNullOrg.setAdapter(orgAdapter);
        //选择的监听
        binding.courseNullOrg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //选择改变
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //保存当前部门号
                mViewModel.OID=values.get(position).getAsInteger("OID");
                //获取部门
                mViewModel.loadMent(mViewModel.OID);

            }
            //没有被选择
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //显示课表
    private void showUNCourse(List<UNCourse> unCourses) {
        //生成课表占位符
        TextView[][] textViews = new TextView[7][5];
        //初始化位置
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                textViews[i][j] = new TextView(requireContext());
                int drawable;
                switch ((i + j) % 4) {
                    case 0:
                        drawable = R.drawable.course_bk_1_ripple;
                        break;
                    case 1:
                        drawable = R.drawable.course_bk_2_ripple;
                        break;
                    case 2:
                        drawable = R.drawable.course_bk_3_ripple;
                        break;
                    case 3:
                        drawable = R.drawable.course_bk_4_ripple;
                        break;
                    default:
                        drawable = R.drawable.course_bk_noweek_ripple;
                        break;
                }
                //设置背景
                textViews[i][j].setBackgroundResource(drawable);
                //设置字体大小
                textViews[i][j].setTextSize(10);
                //设置高度
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mViewModel.avg_width, dip2px(150));
                //定位位置
                params.setMargins(i * mViewModel.avg_width, dip2px(150 * j), 0, 0);
                //应用
                textViews[i][j].setLayoutParams(params);
                //添加
                binding.courseNull.addView(textViews[i][j]);
            }
        }

        //添加人员
        for (UNCourse uncourse : unCourses) {
            addPeople(uncourse, textViews[uncourse.getWeek() - 1]);
        }
    }


    //添加人员到课表
    private void addPeople(UNCourse unCourse, TextView[] textView) {
        //根据周数，将人员添加到其中
        for (int i = 0; i < 5; i++)
            if (mViewModel.week < unCourse.getMax_Min(i)[0] || mViewModel.week > unCourse.getMax_Min(i)[1])
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


    //设置周数
    private void setWeek() {
        final Spinner spinner = new Spinner(requireContext());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item);
        // 添加到数组
        for (int i = 1; i < 32; i++) {
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
                showUNCourse(unCourses_temp);
                binding.courseNullWeekNums.setText(week + "周");
            }
        }).setNegativeButton("取消", null);
        builder.create().show();
    }


    //dp转px
    private int dip2px(float dpValue) {
        Context context = requireContext();
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
