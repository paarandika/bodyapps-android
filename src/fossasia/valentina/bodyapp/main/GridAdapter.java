package fossasia.valentina.bodyapp.main;

import android.content.Context;
import android.content.res.Configuration;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
 
public class GridAdapter extends BaseAdapter {
    private Context mContext;
 
    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.pic_01, R.drawable.pic_02,
            R.drawable.pic_03,
            R.drawable.pic_05, R.drawable.pic_06,
            R.drawable.pic_07, R.drawable.pic_08,
            R.drawable.pic_09, R.drawable.pic_10,
            R.drawable.pic_11, 
    		R.drawable.pic_12,
            R.drawable.pic_04
    };
 
    // Constructor
    public GridAdapter(Context c){
        mContext = c;
    }
 
    @Override
    public int getCount() {
        return mThumbIds.length;
    }
 
    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	DisplayMetrics metrics = new DisplayMetrics();
    	((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
    	float density  = mContext.getResources().getDisplayMetrics().density;
    	float width = metrics.widthPixels;
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        float num;
        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
        	num= ((width/2)-50*density)/3;
        }else{
        	num= (width-50*density)/3;
        }
        
//        System.out.println(num+" "+width+" "+(width/150)+" "+density);
        imageView.setLayoutParams(new GridView.LayoutParams((int)(num),(int)(num)));
        return imageView;
       
    }
 
}
