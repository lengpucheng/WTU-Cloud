package cn.hll520.wtu.cloud.Activity.Main;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.databinding.ManageFragmentBinding;

public class ManageFragment extends Fragment {

    private ManageViewModel mViewModel;
    private ManageFragmentBinding binding;
    private NavController controller;

    public static ManageFragment newInstance() {
        return new ManageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.manage_fragment, container, false);
        binding=ManageFragmentBinding.bind(view);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ManageViewModel.class);
        TextView Tile = requireActivity().findViewById(R.id.MainTile);
        Tile.setText("控制台");
        //空课表
        controller= Navigation.findNavController(requireView());
        binding.manageBtCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.navigate(R.id.action_manageFragment_to_courseNullFragment);
            }
        });

    }

}
