package Android.Zone.Abstract_Class.Adapter.core;

import java.util.List;

import Android.Zone.Constant;
import Android.Zone.Abstract_Class.Adapter.Adapter_MultiLayout_Zone;
import Android.Zone.Log.Logger_Zone;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BaseAdapter_Zone<T> extends BaseAdapter{
	public List<T> data;
	public LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局
	public Logger_Zone logger;
	/**
	 * @param context
	 * @param data
	 * @param layout_id
	 */
	public BaseAdapter_Zone(Context context, List<T> data) {
		this.data = data;
		mInflater = LayoutInflater.from(context);
		logger= new  Logger_Zone(Adapter_MultiLayout_Zone.class,Constant.Logger_Config);
		logger.closeLog();
	}
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int index) {
		return data.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder_Zone holder;
		if (convertView == null) {
			if (getConverView(position)==null) {
				//convertView 会重复利用  arg0则不会 所以数据 每次都加载就好了 所以  有关convertView的都是初始化用
				convertView = mInflater.inflate(getLayoutID(position), null);
			}else
				convertView=getConverView(position);
			holder = new ViewHolder_Zone(convertView);
			//给View添加监听 
			convertView.setTag(holder);
			logger.log("初始化：position:"+position);
		} else {
			holder = ((ViewHolder_Zone) convertView.getTag());
			logger.log("复用 position:"+position);
		}
		//布局都已经弄好了  就是往view里 填数据   holder里有view 有index 有该组的数据
		T dataIndex = data.get(position);
		setData(holder,dataIndex,position);
		return convertView;
	}
	/**
	 * holder.findviewByid可以找到控件
	 * @param holder   findviewByid可以找到控件
	 * @param data     此itemIndex的数据集合 中的item...
	 * @param position 
	 */
	public abstract  void setData(ViewHolder_Zone holder, T data, int position); //注意这里，只声明了这个方法，但没有具体实现。
	public abstract int getLayoutID(int position);
	public View getConverView(int position){
		return null;
	}
}
