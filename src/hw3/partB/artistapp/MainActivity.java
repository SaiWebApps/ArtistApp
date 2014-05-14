package hw3.partB.artistapp;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.os.Build;

/**
 * Artist App - Displays information about an artist, plays an album, and
 * shows pictures.
 * @author Sairam Krishnan (sbkrishn)
 */
public class MainActivity extends ActionBarActivity {
	//Views
	private VideoView vw;
	private EditText emailView;
	private Button submitButton;
	private boolean resume;
	private final String EMAIL = "sairambkrishnan@gmail.com";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	/**
	 * Initialize views.
	 */
	private void initializeFields() {
		emailView = (EditText) findViewById(R.id.userEmail);
		submitButton = (Button) findViewById(R.id.submitButton);
		vw = (VideoView) findViewById(R.id.videoView);
		String fileName = "android.resource://" + getPackageName() + "/" + R.raw.radioactive;
		vw.setVideoURI(Uri.parse(fileName));
	}
	
	/**
	 * Initialize video. Add listener that will allow user to pause and resume video
	 * through tap/touch alone.
	 */
	private void setVideoListener() {	
		vw.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (!resume) {
					vw.pause();
					resume = true;
				}
				else {
					vw.start();
					resume = false;
				}
				return true;
			}
		});
		vw.start();
	}

	/**
	 * Register onclick button listener to submit button.
	 */
	private void setButtonListener() {
		submitButton.setOnClickListener(new View.OnClickListener() {
			
			//On click, pull up a email screen to allow user to request
			//membership in mailing list.
			@Override
			public void onClick(View v) {
				String userEmail = emailView.getText().toString();
				if (userEmail.trim().isEmpty()) {
					return;
				}
				
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("text/plain");
				i.putExtra(Intent.EXTRA_EMAIL  , new String[]{EMAIL, userEmail});
				i.putExtra(Intent.EXTRA_SUBJECT, "Request to join mailing list");
				i.putExtra(Intent.EXTRA_TEXT   , "Hi! I would like to join your mailing list.");
				try {
				    startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(MainActivity.this, 
				    		"There are no email clients installed.", 
				    		Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		initializeFields();
		setVideoListener();
		setButtonListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
}