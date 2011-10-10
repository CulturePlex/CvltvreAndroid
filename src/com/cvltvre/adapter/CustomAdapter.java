/**
 * 
 */
package com.cvltvre.adapter;

import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.cvltvre.R;
import com.cvltvre.utils.ImageThreadLoader;
import com.cvltvre.utils.ImageThreadLoader.ImageLoadedListener;
import com.cvltvre.utils.SensorHandler;
import com.cvltvre.view.LoadingActivity;
import com.cvltvre.vo.MuseumVO;

/**
 * @author Pencerval
 *
 */
public class CustomAdapter extends ArrayAdapter<MuseumVO> {
    public static boolean filtered;
	public static boolean filteredString;
	public static List<MuseumVO> filteredMuseums=new LinkedList<MuseumVO>();

	private LayoutInflater mInflater;
   // private static int size=0;
    //private Bitmap mIcon;
    
    private ImageThreadLoader imageLoader;
    //private static SortedSet museumVOs;
    
    
    public CustomAdapter(Context context,int id,Handler handler) {
    	super(context, id);
        // Cache the LayoutInflate to avoid asking for a new one each time.
        mInflater = LayoutInflater.from(context);
        imageLoader= new ImageThreadLoader(handler);
    }



	/**
     * The number of items in the list is determined by the number of speeches
     * in our array.
     *
     * @see android.widget.ListAdapter#getCount()
     */
    public int getCount() {
    	/*if(LoadingActivity.museumVOsTemporal!=null && LoadingActivity.museumVOs!=null && LoadingActivity.museumVOsTemporal.size()!=LoadingActivity.museumVOs.size() || LoadingActivity.museumVOs.size()==0){
        	for(MuseumVO museumVO:LoadingActivity.museumVOsTemporal){
        		boolean exist=false;
        		for(MuseumVO museumVO2:LoadingActivity.museumVOs){
            		if(museumVO.getId().equals(museumVO2.getId())){
            			exist=true;
            			break;
            		}
            	}
        		if(!exist){
        			LoadingActivity.museumVOs.add(museumVO);
        			notifyDataSetChanged();
        		}
        	}
        }*/
    	if(filtered){
    		return filteredMuseums.size();
    	}else{
    		return LoadingActivity.museumVOs.size();	
    	}
        
    }

    /**
     * Since the data comes from an array, just returning the index is
     * sufficent to get at the data. If we were using a more complex data
     * structure, we would return whatever object represents one row in the
     * list.
     *
     * @see android.widget.ListAdapter#getItem(int)
     */
    public MuseumVO getItem(int position) {
    	if(filtered){
    		return filteredMuseums.get(position);
    	}else{
    		return (MuseumVO) LoadingActivity.museumVOs.toArray()[position];	
    	}
        
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
            holder.compass=(ImageView)convertView.findViewById(R.id.compass);
            convertView.setTag(holder);
            //holder.compass=new Compass(LoadingActivity.loadingActivity.getApplicationContext());
            //holder.compass.invalidate();
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }
        //if(LoadingActivity.museumVOs.size()!=size){
        //	museumVOs=entriesSortedByValues(LoadingActivity.museumVOs);
        //	size=museumVOs.size();
        //}
        MuseumVO museumVO;
        if(filtered){
        	museumVO=filteredMuseums.get(position);
        }else{
        	museumVO=(MuseumVO) LoadingActivity.museumVOs.toArray()[position];	
        }
        
        //MuseumVO museumVO=LoadingActivity.museumVOs.get(position);
        
        // Bind the data efficiently with the holder.
        holder.name.setText(museumVO.getTitle());
        //holder.name.setTextColor()
        //holder.arrow.setImageBitmap(mIcon);
        String distance=museumVO.getDistance().substring(0,museumVO.getDistance().indexOf(".")+2);
        holder.distance.setText(distance+" Km");
        
       /* float compassHeading =0;
        if(SensorHandler.updated){
        	String[] coords=museumVO.getMap_options().split(",");
			double latitude = Double.parseDouble(coords[1]);
			double longitude = Double.parseDouble(coords[0]);
        	//compassHeading= (int) Geometry.getLocationDirection((int) (-SensorHandler.mValues[0]-90), CustomLocationListener.location.getLatitude(), CustomLocationListener.location.getLongitude(), latitude, longitude);
			compassHeading= (int) (-SensorHandler.mValues[0]-90);
        }
        */
       /* Matrix matrix = new Matrix();
        matrix.postRotate(compassHeading);
       
        Bitmap resizedBitmap = Bitmap.createBitmap(mIcon, 0, 0, mIcon.getWidth(), mIcon.getHeight(), matrix, true);
        holder.compass.setImageBitmap(resizedBitmap);
        
        */
        //Canvas canvas=new Canvas();
        //canvas.setBitmap(Bitmap.createBitmap(mIcon));
        //canvas.save();
        
        
        
        
        
        String[] coords=museumVO.getMap_options().split(",");
		double latitude = Double.parseDouble(coords[1]);
		double longitude = Double.parseDouble(coords[0]);
		
		
        holder.compass.setImageBitmap(SensorHandler.getCompassImage(latitude,longitude));
        holder.compass.setScaleType(ScaleType.FIT_XY);
        
        
        
        
        
        
        //holder.compass.setBackgroundColor(Color.TRANSPARENT);
        
        
        Bitmap cachedImage = null;
        if(museumVO.getImage()==null){
        	cachedImage=BitmapFactory.decodeResource(parent.getResources(), com.cvltvre.R.drawable.museum);
        }else{
        	if(museumVO.getBitmap()==null){
        		try {
        	        cachedImage = imageLoader.loadImage(museumVO.getId(),"http://www.cvltvre.com/"+museumVO.getImage(), new ImageLoadedListener() {
                    public void imageLoaded(String id,Bitmap imageBitmap) {
                    	//LoadingActivity.museumVOs.get(id).setBitmap(imageBitmap);
                    	//notifyDataSetChanged();
                    	for(MuseumVO museumVO:LoadingActivity.museumVOs){
							if(museumVO.getId().equals(id)){
								museumVO.setBitmap(imageBitmap.copy(imageBitmap.getConfig(), false));
								//imageBitmap.recycle();
								notifyDataSetChanged();                
								break;
							}
						}
                    	
                      //LoadingActivity.museumVOs..setBitmap(imageBitmap);
                  	  //holder.thumb.setImageBitmap(imageBitmap);
                  	  
                    }});
                    //if(cachedImage!=null){
                    //	museumVO.setBitmap(cachedImage);
                    //}
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
        	holder.thumb.setImageBitmap(BitmapFactory.decodeResource(parent.getResources(), com.cvltvre.R.drawable.museum));
        }
        holder.thumb.setScaleType(ScaleType.FIT_XY);
        //holder.thumb.setImageBitmap(LoadingActivity.museumVOs.get(position).getBitmap());
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView distance;
        ImageView arrow;
        ImageView thumb;
        ImageView compass;
    }
    
    /*@Override
    public void sort(Comparator<? super MuseumVO> comparator) {
    	// TODO Auto-generated method stub
    	super.sort(new Comparator<MuseumVO>() {
			public int compare(MuseumVO object1, MuseumVO object2) {
				Float distance1 = Float.parseFloat(object1.getDistance());
				Float distance2 = Float.parseFloat(object2.getDistance());
				return distance1.compareTo(distance2);
			}
		});
    }*/
    
    static <K,V extends Comparable<? super V>>
    	SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
            new Comparator<Map.Entry<K,V>>() {
                @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                    //return e1.getValue().compareTo(e2.getValue());
                    Float distance1 = Float.parseFloat(((MuseumVO)e1.getValue()).getDistance());
					Float distance2 = Float.parseFloat(((MuseumVO)e2.getValue()).getDistance());
					return distance1.compareTo(distance2);
                }
            }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }
    
}
