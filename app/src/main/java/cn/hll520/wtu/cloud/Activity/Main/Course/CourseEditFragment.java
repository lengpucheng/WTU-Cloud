package cn.hll520.wtu.cloud.Activity.Main.Course;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.databinding.CourseEditFragmentBinding;

public class CourseEditFragment extends Fragment {

    private CourseEditViewModel mViewModel;
    private CourseEditFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding=CourseEditFragmentBinding.bind(inflater.inflate(R.layout.course_edit_fragment, container, false));
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel=new ViewModelProvider(this).get(CourseEditViewModel.class);

    }

}
