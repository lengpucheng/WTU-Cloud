package cn.hll520.wtu.cloud.Activity.Main.Course;

import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.databinding.CourseLoginFragmentBinding;

public class CourseLoginFragment extends Fragment {

    private CourseLoginViewModel mViewModel;
    private CourseLoginFragmentBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding=CourseLoginFragmentBinding.bind(inflater.inflate(R.layout.course_login_fragment, container, false));
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel =new ViewModelProvider(this).get(CourseLoginViewModel.class);
        //加载验证码
        mViewModel.open();
        //显示验证码
        mViewModel.getIMG().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                //如果验证码变化就获取验证码
                binding.courseLoginCheckIMG.setImageBitmap(bitmap);
            }
        });
        //初始化
        init();
        //选择学期
        binding.courseLoginTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonXia:
                        mViewModel.semester = String.valueOf(3);
                        break;
                    case R.id.radioButtonShang:
                        mViewModel.semester = String.valueOf(12);
                        break;
                }
            }
        });
        //刷新验证码
        binding.courseLoginCheckIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.open();
            }
        });
        //登录
        binding.couresLoginOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        //监听提示信息
        mViewModel.getMSG().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //关闭进度条
                binding.courseLoginIng.setVisibility(View.GONE);
                //如果提示信息变化则弹窗
                if(mViewModel.LOGIN_OK.equals(s)){
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    //获取导航控制器，参数为当前页面
                    NavController controller = Navigation.findNavController(requireView());
                    //跳转页面
                    controller.navigate(R.id.action_courseLoginFragment_to_courseFragment);
                }else{
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    mViewModel.open();//刷新验证码
                }
            }
        });

    }


    //登录
    private void login() {
        String sid=binding.courseLoginSid.getText().toString();
        String pass=binding.courseLoginPass.getText().toString();
        String check=binding.courseLoginCheck.getText().toString();
        //建议
        if(sid.isEmpty()){
            binding.courseLoginSid.setError("学号不能为空");
            return;
        }
        mViewModel.sid=sid;
        if(pass.isEmpty()){
            binding.courseLoginPass.setError("密码不能为空");
            return;
        }
        mViewModel.pass=pass;
        //获取验证码
        if(check.isEmpty()){
            binding.courseLoginCheck.setError("验证码不能为空");
            return;
        }
        mViewModel.check=check;
        //获取当前选中的年份
        mViewModel.year =binding.courseLoginSpinner.getSelectedItem().toString();
        //如果是下半年就设定到去年
        if (mViewModel.semester.equals("12"))
            mViewModel.year = String.valueOf(Integer.parseInt(mViewModel.year) - 1);
        //开启进度条
        binding.courseLoginIng.setVisibility(View.VISIBLE);
        //登录
        mViewModel.login();
    }

    //初始化
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init() {
        //获取当前年
        int y = Calendar.getInstance().get(Calendar.YEAR);
        //初始化年份选择
        final int[] items = {y - 3, y - 2, y - 1, y, y + 1, y + 2};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item);
        for (int item : items) {
            dataAdapter.add(String.valueOf(item));
        }
        binding.courseLoginSpinner.setAdapter(dataAdapter);
        //默认选择今年
        binding.courseLoginSpinner.setSelection(3, true);

        //获取月
        y = Calendar.getInstance().get(Calendar.MONTH) + 1;
        //初始化学期选择
        if (y < 7) {
            binding.courseLoginTime.check(R.id.radioButtonShang);
            mViewModel.semester = String.valueOf(12);
        } else {
            binding.courseLoginTime.check(R.id.radioButtonXia);
            mViewModel.semester  = String.valueOf(3);
        }
    }

}
