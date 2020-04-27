package cn.hll520.wtu.cloud.Activity.Main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.adapter.PeoAdapter;
import cn.hll520.wtu.cloud.model.People;

public class PeopleFragment extends Fragment {

    private PeopleViewModel mViewModel;
    private RecyclerView recyclerView;
    private PeoAdapter adapter;
    private LiveData<List<People>> peos;
    public static PeopleFragment newInstance() {
        return new PeopleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle   savedInstanceState) {
        return inflater.inflate(R.layout.people_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView Tile=getActivity().findViewById(R.id.MainTile);
        Tile.setText("联系人");
        mViewModel = ViewModelProviders.of(this).get(PeopleViewModel.class);

        //在Fragment中使用requireActivity()获取当前活动
        recyclerView=requireActivity().findViewById(R.id.people_RecyclerView);
        //设置组件维度
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        //初始化适配器
        adapter=new PeoAdapter();
        //添加适配器
        recyclerView.setAdapter(adapter);
        if(peos==null)
            mViewModel.getPEO();
        peos=mViewModel.getAllPeos();
        //添加监听   参数生命周期，监听者
        peos.observe(getViewLifecycleOwner(), new Observer<List<People>>() {
            @Override
            public void onChanged(List<People> people) {
                //更新视图
                adapter.submitList(people);
            }
        });
    }

}
