package br.eng.mosaic.pigeon.client.gameplay;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import br.eng.mosaic.pigeon.client.R;
import br.eng.mosaic.pigeon.client.gameplay.cast.BadPigeon;
import br.eng.mosaic.pigeon.communication.StatusNetwork;
import br.eng.mosaic.pigeon.communication.ThreadScoreServer;

public class Transition extends Activity{

	ImageButton next,  back, audio, person;
	int cont;
	TextView textscore, textLevel;

	public static String[]  level;
	public static int lev;
	private StatusNetwork statusNetwork;
	private static SoundManager sm;  
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transition);
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);  
	    sm = SoundManager.getInstance(this);
	
		Intent intent = getIntent();
		level = (String[]) intent.getSerializableExtra("level");
		lev = Integer.parseInt(level[1]);
		
		textLevel = (TextView) findViewById(R.id.transition_level);
		textscore = (TextView) findViewById(R.id.transition_score);
		textscore.setText(level[2].toString()+" pt");
		textLevel.setText(level[1].toString());
		
		statusNetwork = new  StatusNetwork(this.getBaseContext());
		cont=0;
		next  = (ImageButton) findViewById(R.id.next_level);
		back = (ImageButton) findViewById(R.id.back_button_transition);
		audio = (ImageButton) findViewById(R.id.audio_button_transition);
		person = (ImageButton) findViewById(R.id.level_person);
		
		sm.playSound(2);
		
		try {
			if (level[0].equals("figean"))
				person.setBackgroundResource(R.drawable.mosaic_pigeon_ima_layer_figean_noselection);
			else if (level[0].equals("sigeon"))
				person.setBackgroundResource(R.drawable.mosaic_pigeon_ima_layer_sigeon_noselection);
			else if (level[0].equals("figeon"))
				person.setBackgroundResource(R.drawable.mosaic_pigeon_ima_layer_figeon_noselection);
		} catch (NullPointerException np) {
			Log.e("Null", "person button is null. See the names of the IDs in transition.xml");
		}
						
		//Stage.mMainMusic.stop();
		try {
			next.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (lev == 2) {
						sm.stopSounds();
						Intent i = new Intent(getBaseContext(),Stage2.class);
						i.putExtra("select", level[0]);
						startActivity(i);
					}
					if (lev == 3) {
						sm.stopSounds();
						Intent i = new Intent(getBaseContext(),Stage3.class);
						i.putExtra("select", level[0]);
						startActivity(i);					
					}
					if(lev == 4){
						sm.stopSounds();
						Intent i = new Intent(getBaseContext(),Stage4.class);
						i.putExtra("select", level[0]);
						startActivity(i);				
					}
					BadPigeon.velocity *= 1.5;
				}
			});
		} catch (NullPointerException np) {
			Log.e("Null", "next button is null. See the names of the IDs in transition.xml");
		}
		
		try {
			back.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getBaseContext(), SelectPerson.class));
					//Stage.mMainMusic.play();
					sm.stopSounds();
				}
			});
		} catch (NullPointerException np) {
			Log.e("Null", "back button is null. See the names of the IDs in transition.xml");
		}
		
		try {
			audio.setOnClickListener(new OnClickListener() {			
				@Override
				public void onClick(View v) {
					if (cont==0) {
						v.setBackgroundResource(R.drawable.mosaic_pigeon_icon_audio_mute);					
						SelectPerson.mute = true;
						sm.stopSounds();	
						cont++;
					} else {
						v.setBackgroundResource(R.drawable.mosaic_pigeon_icon_audio_icon);					
						SelectPerson.mute = false;
						sm.playSound(2);
						cont=0;
					}
				}
			});
		} catch (NullPointerException np) {
			Log.e("Null", "audio button is null. See the names of the IDs in transition.xml");
		}
		
		if ( statusNetwork.hasNetwork() ) {
			Log.d("rafa", "tem internet");
			ThreadScoreServer thread = new ThreadScoreServer();
			thread.configure(this, getScore());
			thread.start();
		}
	}
	
	private String getScore() {
		if (level == null) return null;
		if ( lev > level.length || lev < 2 ) return null; 
		return level[1];
	}
	
	/**
	 * @author jamilson
	 * @Description Implementation for button  back of Activity
	 * @param Indentification of onclick for mouse
	 * @return value boolean
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	startActivity(new Intent(getBaseContext(), SelectPerson.class));
	    	sm.stopSounds();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		setResult(RESULT_CANCELED);
		finish(); //Close the screen
	}
	
}