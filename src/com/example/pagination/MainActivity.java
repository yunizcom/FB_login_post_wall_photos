package com.example.pagination;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;

import android.widget.AbsoluteLayout;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

/*import com.addthis.core.AddThis;
import com.addthis.core.Config;
import com.example.pagination.R;
import com.addthis.error.ATDatabaseException;
import com.addthis.error.ATSharerException;
import com.addthis.models.ATShareItem;
import com.addthis.ui.views.ATButton;*/

import com.facebook.*;
import com.facebook.Session.StatusCallback;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.Facebook;
import com.facebook.model.*;


import com.example.pagination.R.string;
import com.example.pagination.helloworld2;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

	public helloworld2 helloworld2;
	public ImageView imageView1;
	
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = false;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sndpage);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
		
		helloworld2 = new helloworld2();
		
		Log.v("DEBUG","---" + helloworld2.bump("sdsd"));
		
		TextView welcome = (TextView) findViewById(R.id.welcome);
		EditText editText1 = (EditText) findViewById(R.id.editText1);
		
		imageView1 = (ImageView) findViewById(R.id.imageView1);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Bombing.ttf");
        
        welcome.setTypeface(tf);
        editText1.setTypeface(tf);

        imageView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                switch (arg1.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                	changeBtnImg(0);
                    break;
                }
                case MotionEvent.ACTION_CANCEL:{
                	changeBtnImg(1);
                    break;
                }
                case MotionEvent.ACTION_UP:{
                	changeBtnImg(1);
                    break;
                }
                }
                return true;
            }
        });
        
		
		Session.openActiveSession(this, true, new Session.StatusCallback() {
	
		    // callback when session changes state
		    @SuppressWarnings("deprecation")
			@Override
		    public void call(Session session, SessionState state, Exception exception) {
Log.v("DEBUG","FB session : " + session);	
		    	if (session.isOpened()) {
		    		
		    		// make request to the /me API
		    		Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

		    		  // callback after Graph API response with user object
		    		  @Override
		    		  public void onCompleted(GraphUser user, Response response) {
		    			  
		    			  if (user != null) {
		    				  //TextView welcome = (TextView) findViewById(R.id.welcome);
Log.v("DEBUG","LOGIN FB : " + user + " | " + response);
		    				}
		    			  
		    		  }
		    		});
		    		
		    	}
		    	
		    }
		  });
	}

	public void changeBtnImg(int statusId){
		
		InputStream ims1;
	    Drawable d1;
		
		switch(statusId) {
			case 0: {
	        	
				try 
				{
				    ims1 = getAssets().open("minion_2.png");
				    d1 = Drawable.createFromStream(ims1, null);
				    imageView1.setImageDrawable(d1);
				}
				catch(IOException ex) 
				{
				    return;
				}
				
				
	            break;
	        }
	        case 1:{
	        	
	        	try 
				{
				    ims1 = getAssets().open("minion_1.png");
				    d1 = Drawable.createFromStream(ims1, null);
				    imageView1.setImageDrawable(d1);
				}
				catch(IOException ex) 
				{
				    return;
				}
	        	
	            break;
	        }
		}
			
	}
	
	@Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	      super.onActivityResult(requestCode, resultCode, data);
	      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	  }
	
	public static byte[] loadBitmapFromView_ARRAYBYTES(View v) {
	    Bitmap bitmap;
		View v1 = v.getRootView();
		v1.setDrawingCacheEnabled(true);
		bitmap = Bitmap.createBitmap(v1.getDrawingCache());
Log.v("DEBUG",(int)(bitmap.getWidth() * 0.3) + " - " + (int)(bitmap.getHeight() * 0.3));
		bitmap=Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth() * 0.3), (int)(bitmap.getHeight() * 0.3), true);
		v1.setDrawingCacheEnabled(false);
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
		byte[] byteArray = stream.toByteArray();
		
		 return byteArray;
	}
	
	public static Bitmap loadBitmapFromView_BITMAP(View v) {
	    Bitmap bitmap;
		View v1 = v.getRootView();
		v1.setDrawingCacheEnabled(true);
		bitmap = Bitmap.createBitmap(v1.getDrawingCache());
Log.v("DEBUG",(int)(bitmap.getWidth() * 1) + " - " + (int)(bitmap.getHeight() * 1));
		bitmap=Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth() * 1), (int)(bitmap.getHeight() * 1), true);
		v1.setDrawingCacheEnabled(false);
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
		
		 return bitmap;
	}
	
	public void publishPhoto(Bitmap imgData, String message) {

		Session session = Session.getActiveSession();
		
        Request request = Request.newUploadPhotoRequest(session, imgData, new Request.Callback()
        {
            @Override
            public void onCompleted(Response response)
            {
            	Log.v("DEBUG","DONE UPLOAD : " + response);
            }
        });
        Bundle postParams = request.getParameters(); // <-- THIS IS IMPORTANT
        postParams.putString("name", message + " install at https://www.yuniz.com/");
        request.setParameters(postParams);
        request.executeAsync();
		
	}

	private void publishStory() {
	    Session session = Session.getActiveSession();

	    if (session != null){

	        // Check for publish permissions    
	        List<String> permissions = session.getPermissions();
	        if (!isSubsetOf(PERMISSIONS, permissions)) {
	            pendingPublishReauthorization = true;
	            Session.NewPermissionsRequest newPermissionsRequest = new Session
	                    .NewPermissionsRequest(this, PERMISSIONS);
	        session.requestNewPublishPermissions(newPermissionsRequest);
	            return;
	        }

	        Bundle postParams = new Bundle();
	        postParams.putString("name", "Facebook SDK for Android");
	        postParams.putString("caption", "Build great social apps and get more installs.");
	        postParams.putString("description", "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
	        postParams.putString("link", "https://developers.facebook.com/android");
	        postParams.putString("picture", "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

	        Request.Callback callback= new Request.Callback() {
	            public void onCompleted(Response response) {
	            	Log.v("DEBUG","DONE UPLOAD : " + response);
	            }
	        };

	        Request request = new Request(session, "me/feed", postParams, 
	                              HttpMethod.POST, callback);

	        RequestAsyncTask task = new RequestAsyncTask(request);
	        task.execute();
	    }

	}
	
	private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
	    for (String string : subset) {
	        if (!superset.contains(string)) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public void oneBtn(View v) {
		AbsoluteLayout myview = (AbsoluteLayout) findViewById(R.id.myview);

		publishPhoto(loadBitmapFromView_BITMAP(myview),"test2");
		
		//publishStory();
		
	}
	
	public void twoBtn(View v) {
		setContentView(R.layout.sndpage);
	}
	
}
