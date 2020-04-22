package cn.hll520.wtu.cloud.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import cn.hll520.wtu.cloud.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView menu=findViewById(R.id.mainMenubottom);
        //获取导航控制
        NavController controller= Navigation.findNavController(this,R.id.mainfragment);
        //配置底部导航   用导航组来构建一个工具条
        AppBarConfiguration configuration=new AppBarConfiguration.Builder(menu.getMenu()).build();

        //为导航设置工具条  参数 ——  活动，导航控制器，工具条
//        NavigationUI.setupActionBarWithNavController(this,controller,configuration);
        //绑定底部菜单与导航
        NavigationUI.setupWithNavController(menu,controller);
    }
}
