package cn.hll520.wtu.cloud.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import cn.hll520.wtu.cloud.Activity.MainActivi.PeopleViewModel;
import cn.hll520.wtu.cloud.R;
import cn.hll520.wtu.cloud.model.People;

public class PeoAdapter extends ListAdapter<People, PeoAdapter.PeoViewHodel> {

    public PeoAdapter() {
        super(new DiffUtil.ItemCallback<People>() {
            @Override
            public boolean areItemsTheSame(@NonNull People oldItem, @NonNull People newItem) {
                return oldItem.getUID()==newItem.getUID();
            }

            @Override
            public boolean areContentsTheSame(@NonNull People oldItem, @NonNull People newItem) {
                return false;
            }
        });
    }

    //构造组件
    @NonNull
    @Override
    public PeoViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //定义一个构造
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        itemView = layoutInflater.inflate(R.layout.card_people,parent,false);
        //返回组件卡片
        final PeoViewHodel holder = new PeoViewHodel(itemView);
        //添加点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.baidu.com/s?wd=" + holder.name.getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PeoViewHodel holder, int position) {
        People people=getItem(position);
        holder.avatar.setImageResource(R.drawable.avatar_default);
        holder.name.setText(people.getName());
        holder.ment.setText(people.getMainMent());
        holder.bottom.setText(people.getPhone());
    }

    class PeoViewHodel extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name,ment,bottom;
        public PeoViewHodel(@NonNull View itemView) {
            super(itemView);
            avatar=itemView.findViewById(R.id.card_people_avatar);
            name=itemView.findViewById(R.id.card_people_name);
            ment=itemView.findViewById(R.id.card_people_ment);
            bottom=itemView.findViewById(R.id.card_people_bottom);
        }
    }


}
