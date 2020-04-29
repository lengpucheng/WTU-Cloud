package cn.hll520.wtu.cloud.Activity.Main;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.adapter.PeoAdapter;
import cn.hll520.wtu.cloud.model.People;

public class CourseFragment extends Fragment {

    private CourseViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.course_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        TextView Tile=getActivity().findViewById(R.id.MainTile);
        Tile.setText("课表");

        final ImageView menubar=requireActivity().findViewById(R.id.mainAdd);
        menubar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(menubar);
            }
        });


    }

    private void showMenu(final View view) {
        PopupMenu popupMenu=new PopupMenu(getContext(),view);
        popupMenu.getMenuInflater().inflate(R.menu.course_menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //获取导航控制器，参数为当前页面
                NavController controller = Navigation.findNavController(requireView());
                switch (item.getItemId()){
                    case R.id.course_menu_impro:
                        //设置动作
                        controller.navigate(R.id.action_courseFragment_to_courseLoginFragment);
                        break;
                    case R.id.course_menu_add:
                        //设置动作
                        controller.navigate(R.id.action_courseFragment_to_courseEditFragment);
                        break;
                    case R.id.course_menu_uploda:

                        break;


                }
                return false;
            }
        });
        popupMenu.show();
    }


}
