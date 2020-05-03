package cn.hll520.wtu.cloud.Activity.Main.Course;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.databinding.CourseEditFragmentBinding;
import cn.hll520.wtu.cloud.model.Course;

public class CourseEditFragment extends Fragment {

    private CourseEditViewModel mViewModel;
    private CourseEditFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding=CourseEditFragmentBinding.bind(inflater.inflate(R.layout.course_edit_fragment, container, false));
        mViewModel=new ViewModelProvider(this).get(CourseEditViewModel.class);
        Bundle bundle=getArguments();
        if(bundle!=null){
        mViewModel.setCourse((Course) bundle.getSerializable("COURSE"));
        mViewModel.UID=bundle.getInt("UID");
        }
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //如果是修改
        if(mViewModel.getCourse()!=null){
            mViewModel.setUPData();
            binding.courseEditName.setText(mViewModel.getCourse().getName());
            binding.courseEditRoom.setText(mViewModel.getCourse().getRoom());
            binding.courseEditWeek.setSelection(mViewModel.getCourse().getWeek()-1,true);
            binding.courseEditTmin.setText(String.valueOf(mViewModel.getCourse().getTmin()));
            binding.courseEditTmax.setText(String.valueOf(mViewModel.getCourse().getTmax()));
            binding.courseEditWmin.setText(String.valueOf(mViewModel.getCourse().getWmin()));
            binding.courseEditWmax.setText(String.valueOf(mViewModel.getCourse().getWmax()));
            binding.courseEditTeacher.setText(mViewModel.getCourse().getTeacher());
            binding.courseEditTest.setText(mViewModel.getCourse().getTest());
        }else {
            mViewModel.setCourse(new Course());
        }
        //点击确定
        binding.courseEditOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_course()){
                    mViewModel.saveCourse();
                    Toast.makeText(requireActivity(), "保存成功！", Toast.LENGTH_SHORT).show();
                    NavController controller = Navigation.findNavController(requireView());
                    controller.navigate(R.id.action_courseEditFragment_to_courseFragment);
                }
            }
        });

    }


    //正则检验
    private boolean check_course() {
        String val=binding.courseEditName.getText().toString();
        if(val.isEmpty()){
            binding.courseEditName.setError("课程名不能为空");
            return false;
        }
        mViewModel.getCourse().setName(val);
        val=binding.courseEditRoom.getText().toString();
        if(val.isEmpty()){
            binding.courseEditRoom.setError("上课地点不能为空");
            return false;
        }
        mViewModel.getCourse().setRoom(val);
        val=binding.courseEditTmin.getText().toString();
        String val2=binding.courseEditTmax.getText().toString();
        if(val.isEmpty()||val2.isEmpty()){
            Toast.makeText(requireContext(), "课程节数必须填上哦", Toast.LENGTH_SHORT).show();
        }
        mViewModel.getCourse().setTmin(Integer.parseInt(val));
        mViewModel.getCourse().setTmax(Integer.parseInt(val2));
        val=binding.courseEditWmin.getText().toString();
        val2=binding.courseEditWmax.getText().toString();
        if(val.isEmpty()||val2.isEmpty()){
            Toast.makeText(requireContext(), "课程周数必须填上哦", Toast.LENGTH_SHORT).show();
        }
        mViewModel.getCourse().setWmin(Integer.parseInt(val));
        mViewModel.getCourse().setWmax(Integer.parseInt(val2));
        mViewModel.getCourse().setTeacher(binding.courseEditTeacher.getText().toString());
        mViewModel.getCourse().setTest(binding.courseEditTest.getText().toString());
        mViewModel.getCourse().setWho(mViewModel.UID);
        val=binding.courseEditWeek.getSelectedItem().toString();
        switch (val){
            case "星期一":mViewModel.getCourse().setWeek(1);break;
            case "星期二":mViewModel.getCourse().setWeek(2);break;
            case "星期三":mViewModel.getCourse().setWeek(3);break;
            case "星期四":mViewModel.getCourse().setWeek(4);break;
            case "星期五":mViewModel.getCourse().setWeek(5);break;
            case "星期六":mViewModel.getCourse().setWeek(6);break;
            case "星期日":mViewModel.getCourse().setWeek(7);break;
        }

        return true;
    }

}
