package com.diamon.pez;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class PezDePecera extends ApplicationAdapter {

	SpriteBatch batch;

	Texture img;

	TextureRegion region;

	Sprite es;
	
	Sprite es1;


	protected OrthographicCamera camara;

	protected Stage nivel;
	
	
	
	float x =0;

	@Override
	public void create() {

		nivel = new Stage(new StretchViewport(640, 480));

		((OrthographicCamera) nivel.getCamera()).setToOrtho(false, 640, 480);

		camara = new OrthographicCamera();

		camara.setToOrtho(false, 640, 480);

		camara.update();

		batch = new SpriteBatch();

		img = new Texture("texturas/fondo4.png");

		region = new TextureRegion(img);

		es = new Sprite(region);

		es.setSize(640, 480);
		
		es1 = new Sprite(region);

		es1.setSize(640, 480);
	}

	@Override
	public void render() {

		ScreenUtils.clear(0.0F, 0.0F, 1.0F, 1.0F, true);

		batch.setProjectionMatrix(camara.combined);
		
		
		x-=1;
		
		es.setPosition(x, 0);
		
		es1.setPosition(x+640, 0);
		 
		
		if(x<=-640)
		{
			x =0;
			
			
		}

		batch.begin();

		es.draw(batch);
		
		es1.draw(batch);

		batch.end();

		nivel.draw();

		nivel.act();
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}
}
