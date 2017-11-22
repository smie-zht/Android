package com.james.pro;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

/**
 * Created by qqq on 2017/11/10.
 */

class GeneralAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<People_info> generals;
    private List<General> backData;//用来备份原始数据
//    MyFilter mFilter ;
//    public void remove(int position){
//        generals.remove(position);
//        this.notifyDataSetChanged();
//    }
//    public void update(int position,General general){
//        generals.set(position, general);
//        this.notifyDataSetChanged();
//    }
//    public void add(General general){
//        generals.add(general);
//        this.notifyDataSetChanged();
//    }
    @Override
    public int getCount() {
// TODO Auto-generated method stub
        return generals.size();
    }

    @Override
    public Object getItem(int position) {
// TODO Auto-generated method stub
        return generals.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;// TODO Auto-generated method stub
    }
    public GeneralAdapter(Context context, ArrayList<People_info> generals) {
        super();
        this.context = context;
        this.generals = generals;
    }
//    AssetManager mgr=context.getAssets();//得到AssetManager
//    Typeface tf=Typeface.createFromAsset(mgr,"fonts/myfont.ttf");//根据路径得到Typeface
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
        Viewholder viewholder = null;
        if(convertView == null){
            viewholder = new Viewholder();
            convertView = View.inflate(context, R.layout.general, null);
            viewholder.ivThumb = (ImageView) convertView.findViewById(R.id.ivThumb);
            viewholder.tvName = (TextView) convertView.findViewById(R.id.tvName);
//            viewholder.tvName.setTypeface(tf);
            convertView.setTag(viewholder);
        }else{
            viewholder = (Viewholder) convertView.getTag();
        }
        try{
            People_info general = this.generals.get(position);
            viewholder.ivThumb.setImageResource(general.getImageSrc());
            viewholder.tvName.setText(general.getName());
        }catch(Exception e){
            Log.i("main", e.getMessage());
        }
        return convertView;
    }

    class Viewholder{
        ImageView ivThumb;
        TextView tvName;
    }

//    //当ListView调用setTextFilter()方法的时候，便会调用该方法
//    @Override
//    public Filter getFilter() {
//        if (mFilter ==null){
//            mFilter = new MyFilter();
//        }
//        return mFilter;
//    }
//
//    //我们需要定义一个过滤器的类来定义过滤规则
//    class MyFilter extends Filter {
//        //我们在performFiltering(CharSequence charSequence)这个方法中定义过滤规则
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//            FilterResults result = new FilterResults();
//            List<General> list ;
//            if (TextUtils.isEmpty(charSequence)){//当过滤的关键字为空的时候，我们则显示所有的数据
//                list  = backData;
//            }else {//否则把符合条件的数据对象添加到集合中
//                list = new ArrayList<>();
//                for (General general:backData){
//                    if (General.getTitle().contains(charSequence)){
//                        list.add(general);
//                    }
//
//                }
//            }
//            result.values = list; //将得到的集合保存到FilterResults的value变量中
//            result.count = list.size();//将集合的大小保存到FilterResults的count变量中
//
//            return result;
//        }
//        //在publishResults方法中告诉适配器更新界面
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//            generals = (List<General>)filterResults.values;
//            if (filterResults.count>0){
//                notifyDataSetChanged();//通知数据发生了改变
//            }else {
//                notifyDataSetInvalidated();//通知数据失效
//
//            }
//        }
//
//        @Override
//        public boolean isLoggable(LogRecord record) {
//            return false;
//        }
//    }

}
