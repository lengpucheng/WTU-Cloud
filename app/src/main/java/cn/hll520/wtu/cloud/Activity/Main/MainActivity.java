package cn.hll520.wtu.cloud.Activity.Main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView menu=findViewById(R.id.mainMenubottom);
        //获取导航控制
        NavController controller= Navigation.findNavController(this,R.id.mainfragment);
        //绑定底部菜单与导航
        NavigationUI.setupWithNavController(menu,controller);
        //配置底部导航   用导航组来构建一个工具条
//        AppBarConfiguration configuration=new AppBarConfiguration.Builder(menu.getMenu()).build();
        ImageView imageView=findViewById(R.id.mainUserIMG);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MycenterActivity.class);
                startActivity(intent);
            }
        });
        controller.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

            }
        });
    }
}
