/**
 * 
 */
package org.cvltvre.adapter;

import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.List;

import org.cvltvre.R;
import org.cvltvre.utils.ImageThreadLoader;
import org.cvltvre.utils.ImageThreadLoader.ImageLoadedListener;
import org.cvltvre.view.LoadingActivity;
import org.cvltvre.view.MainListActivity;
import org.cvltvre.vo.MuseumVO;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Pencerval
 *
 */
public class CustomAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Bitmap mIcon;
    
    private ImageThreadLoader imageLoader = new ImageThreadLoader();
    
    public CustomAdapter(Context context) {
        // Cache the LayoutInflate to avoid asking for a new one each time.
        mInflater = LayoutInflater.from(context);
        // Icons bound to the rows.
        mIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow);
    }

    /**
     * The number of items in the list is determined by the number of speeches
     * in our array.
     *
     * @see android.widget.ListAdapter#getCount()
     */
    public int getCount() {
        return LoadingActivity.museumVOs.size();
    }

    /**
     * Since the data comes from an array, just returning the index is
     * sufficent to get at the data. If we were using a more complex data
     * structure, we would return whatever object represents one row in the
     * list.
     *
     * @see android.widget.ListAdapter#getItem(int)
     */
    public Object getItem(int position) {
        return position;
    }

    /**
     * Use the array index as a unique id.
     *
     * @see android.widget.ListAdapter#getItemId(int)
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * Make a view to hold each row.
     *
     * @see android.widget.ListAdapter#getView(int, android.view.View,
     *      android.view.ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        // A ViewHolder keeps references to children views to avoid unneccessary calls
        // to findViewById() on each row.
        ViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitemmuseum, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.museumname);
            holder.distance = (TextView) convertView.findViewById(R.id.museumdistance);
            holder.distance.setTextColor(R.color.grey);
            holder.arrow = (ImageView) convertView.findViewById(R.id.compass);
            holder.thumb=(ImageView) convertView.findViewById(R.id.thumb);
            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }
        MuseumVO museumVO=(MuseumVO) LoadingActivity.museumVOs.toArray()[position];
        //MuseumVO museumVO=LoadingActivity.museumVOs.get(position);
        
        // Bind the data efficiently with the holder.
        holder.name.setText(museumVO.getTitle());
        //holder.name.setTextColor()
        holder.arrow.setImageBitmap(mIcon);
        String distance=museumVO.getDistance().substring(0,museumVO.getDistance().indexOf(".")+2);
        holder.distance.setText(distance+" Km");
        Bitmap cachedImage = null;
        if(museumVO.getImage()==null){
        	cachedImage=BitmapFactory.decodeResource(parent.getResources(), org.cvltvre.R.drawable.museum);
        }else{
        	if(museumVO.getBitmap()==null){
        		try {
                    cachedImage = imageLoader.loadImage("http://www.cvltvre.com/"+museumVO.getImage(), new ImageLoadedListener() {
                    public void imageLoaded(Bitmap imageBitmap) {
                      //museumVO.setBitmap(imageBitmap);
                  	  //holder.thumb.setImageBitmap(imageBitmap);
                  	  notifyDataSetChanged();                }
                    });
                    if(cachedImage!=null){
                    	museumVO.setBitmap(cachedImage);
                    }
                  } catch (MalformedURLException e) {
                  	Log.e(CustomAdapter.class.getName(), "Bad remote image URL: " + "http://www.cvltvre.com/"+((MuseumVO)LoadingActivity.museumVOs.toArray()[position]).getImage(), e);
                  }
        	}else{
        		cachedImage=museumVO.getBitmap();
        	}
        	
        }
        if(cachedImage!=null){  
        	holder.thumb.setImageBitmap(cachedImage);
        }else{
        	holder.thumb.setImageBitmap(BitmapFactory.decodeResource(parent.getResources(), org.cvltvre.R.drawable.museum));
        }
        
        //holder.thumb.setImageBitmap(LoadingActivity.museumVOs.get(position).getBitmap());
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView distance;
        ImageView arrow;
        ImageView thumb;
        
    }

}
