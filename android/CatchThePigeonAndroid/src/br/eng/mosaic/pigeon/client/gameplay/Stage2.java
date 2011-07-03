package br.eng.mosaic.pigeon.client.gameplay;

import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;

import android.content.Intent;
import br.eng.mosaic.pigeon.client.gameplay.cast.BadPigeon;
import br.eng.mosaic.pigeon.client.gameplay.cast.Pigeon;

public class Stage2 extends Stage {
	
	public String select;
	
	@Override
	protected void createCharacters() {
		
		Intent intent = getIntent();
		select = (String) intent.getSerializableExtra("select");
		
		badPigeons.add(new BadPigeon(Stage.playerX + 600, Stage.playerY - 100, Stage.mInvertedEnemyTextureRegion, 1));
		badPigeons.add(new BadPigeon(Stage.playerX + 500, Stage.playerY + 450, Stage.mInvertedEnemyTextureRegion, 1));

		this.setLevel("2");
		
		pigeon.setPosition(playerX/2, playerY);
		scene.getLastChild().attachChild(pigeon);
		
		for (BadPigeon bp: badPigeons) {
			scene.getLastChild().attachChild(bp);
			scene.registerTouchArea(bp);
		}
	}

	@Override
	protected void nextStage() {
		
		super.profile.setScore(1);
		
		String[] person_level = {select,"3"};
		Intent i = new Intent(this,Transition.class);		
		i.putExtra("level", person_level);
		startActivity(i);
		
	}

	//@Override
	//protected void gameOver() {}

	@Override
	protected void setBackgroundParameter() {
		setBackgroundBack("gfx/mosaic_pigeon_ima_stage02_layer01.png");
		setBackgroundFront("gfx/mosaic_pigeon_ima_stage02_layer02.png");
		setBackgroundFront2("gfx/mosaic_pigeon_ima_stage02_layer03.png");
		setBackgroundFront3("gfx/mosaic_pigeon_ima_stage02_layer04.png");	
	}	

	public void createBackgroundTest(String back, String mid, String front, String front2, String front3){
		this.mAutoParallaxBackgroundTexture = new Texture(1024, 1024, TextureOptions.DEFAULT);
		this.mParallaxLayerFront = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this,front, 0, 0);
		this.mParallaxLayerBack = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this,back, 0, 188);
		this.mParallaxLayerFront2 = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this,front2, 0, 690);
		this.mParallaxLayerFront3 = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this,front3, 0, 750);
		this.mEngine.getTextureManager().loadTextures(this.mTexture,this.mAutoParallaxBackgroundTexture);
	}

}