package com.example.technology_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.technology_app.R;
import com.example.technology_app.models.CategoryModel;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    List<CategoryModel.Category> categories;
    Context context;

    public CategoryAdapter(List<CategoryModel.Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder{
        TextView textCateName;
        ImageView imgCate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_category, parent, false);

            viewHolder.textCateName = convertView.findViewById(R.id.cate_name);
            viewHolder.imgCate = convertView.findViewById(R.id.cate_image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textCateName.setText(categories.get(position).getName());
        Glide.with(context).load(categories.get(position).getImage()).into(viewHolder.imgCate);

        return convertView;
    }
}
