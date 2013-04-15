package br.eng.mosaic.pigeon.client.gameplay.cast;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import android.util.Log;

public abstract class Ave extends AnimatedSprite {
	
	protected final PhysicsHandler mPhysicsHandler;
	//private final TiledTextureRegion pTextureRegion;
	
	protected boolean alive = true;
	
	private int life;
	
	/** Milliseconds by frame of the texture of the Ave*/
	protected int millisecondsByFrame = 150;

	public Ave(final float pX, final float pY, final TiledTextureRegion pTextureRegion, int life) {
		super(pX, pY, pTextureRegion);
		this.life = life;
		//this.pTextureRegion = pTextureRegion;
		
		this.setScale(2);
		this.animate(new long[]{millisecondsByFrame, millisecondsByFrame, millisecondsByFrame}, 3, 5, true);

		this.mPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(this.mPhysicsHandler);
	}
	
	/**
	 * Quando a ave sofrer dano, esse m�todo ser� chamado.
	 * @return O personagem morreu?
	 */
	public boolean sufferDamage() {
		Log.d("Pigeon", "sofreu dano");
		return (--life == 0);
	}
	
	/**
	 * Quando a ave encontra algum item que aumente q quantidade de danos que ela pode sofrer
	 */
	protected void incLife() {
		life++;
	}
	
	public int getLife() {
		return life;
	}
	

	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	/**
	 * Recupera a velocidade atual da Ave
	 * @return Velocidade da Ave
	 */
	public abstract float getVelocity();
	

	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}