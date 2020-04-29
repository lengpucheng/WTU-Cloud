package cn.hll520.wtu.cloud.Activity.Main.Course;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.databinding.CourseLoginFragmentBinding;

public class CourseLoginFragment extends Fragment {

    private CourseLoginViewModel mViewModel;
    private CourseLoginFragmentBinding binding;
    public static CourseLoginFragment newInstance() {
        return new CourseLoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding=CourseLoginFragmentBinding.bind(inflater.inflate(R.layout.course_login_fragment, container, false));

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CourseLoginViewModel.class);
        // TODO: Use the ViewModel
    }

}
