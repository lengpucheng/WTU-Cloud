package cn.hll520.wtu.cloud.Activity.MainActivi;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.hll520.wtu.cloud.R;

public class CourseFragment extends Fragment {

    private CourseViewModel mViewModel;

    public static CourseFragment newInstance() {
        return new CourseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.course_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        // TODO: Use the ViewModel
    }

}
