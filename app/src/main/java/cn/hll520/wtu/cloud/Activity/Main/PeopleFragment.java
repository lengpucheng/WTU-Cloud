package cn.hll520.wtu.cloud.Activity.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.adapter.PeoAdapter;
import cn.hll520.wtu.cloud.cloud.CloudPeo;
import cn.hll520.wtu.cloud.model.People;
import cn.hll520.wtu.cloud.model.User;

public class PeopleFragment extends Fragment {

    private PeopleViewModel mViewModel;
    private PeoAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle   savedInstanceState) {
        return inflater.inflate(R.layout.people_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView Tile=requireActivity().findViewById(R.id.MainTile);
        Tile.setText("联系人");
        mViewModel=new ViewModelProvider(this).get(PeopleViewModel.class);
        //在Fragment中使用requireActivity()获取当前活动
        RecyclerView recyclerView = requireActivity().findViewById(R.id.people_RecyclerView);
        //设置组件维度
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        //初始化适配器
        adapter=new PeoAdapter();
        //添加适配器
        recyclerView.setAdapter(adapter);
        //获取用户
        mViewModel.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                //获取联系人列表
                mViewModel.doGetPeo(user.getUID());
            }
        });
        //获取云端联系人列表
        mViewModel.getResult().observe(getViewLifecycleOwner(), new Observer<CloudPeo.ResultPeo>() {
            @Override
            public void onChanged(CloudPeo.ResultPeo resultPeo) {
                //写入联系人
                for(People people:resultPeo.peoples){
                    mViewModel.addPeo(people);
                }
            }
        });
        //显示联系人列表
        mViewModel.getAllPeos().observe(getViewLifecycleOwner(), new Observer<List<People>>() {
            @Override
            public void onChanged(List<People> people) {
                adapter.submitList(people);
            }
        });
    }

}
