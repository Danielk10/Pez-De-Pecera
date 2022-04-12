package com.diamon.pruebas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.diamon.nucleo.Juego;
import com.diamon.nucleo.Personaje;

public class Pruebas extends Game {

	public static final float ANCHO_PANTALLA = 640;

	public static final float ALTO_PANTALLA = 480;

	protected World mundoVirtual;

	protected static final float STEP_TIME = 1f / 60f;

	protected static final int VELOCITY_ITERATIONS = 6;

	protected static final int POSITION_ITERATIONS = 2;

	private float accumulator = 0;

	protected Stage nivel;

	protected OrthographicCamera camara;

	protected Box2DDebugRenderer debugRenderer;

	protected SpriteBatch pincel;
	Body cuerpo;
	Sprite actor;

	public Pruebas() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void create() {

		Box2D.init();

		mundoVirtual = new World(new Vector2(0, Juego.GRAVEDAD), true);

		nivel = new Stage(new StretchViewport(Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA));

		((OrthographicCamera) nivel.getCamera()).setToOrtho(false, Juego.ANCHO_PANTALLA, Juego.ALTO_PANTALLA);

		camara = new OrthographicCamera();

		camara.setToOrtho(false, Juego.ANCHO_PANTALLA / 100f, Juego.ALTO_PANTALLA / 100f);

		camara.update();

		debugRenderer = new Box2DDebugRenderer();

		pincel = new SpriteBatch();

		actor = new Sprite(new TextureRegion(new Texture(Gdx.files.internal("texturas/bomba.png"))));

		actor.setSize(64f / 100f, 64f / 100f);

		actor.setPosition(310f / 100f, 300f / 100f);

		BodyDef bodyDef = new BodyDef();

		bodyDef.type = BodyDef.BodyType.KinematicBody;

		FixtureDef fixtureDef = new FixtureDef();

		PolygonShape shape = new PolygonShape();

		shape.setAsBox((64f / 2f) / 100f, (64f / 2f) / 100f);

		fixtureDef.shape = shape;
		
		fixtureDef.density = 200;
		
		fixtureDef.friction = 0.7f;
		
		fixtureDef.restitution = 0.7f;

		cuerpo = mundoVirtual.createBody(bodyDef);

		cuerpo.setTransform(actor.getX() + actor.getWidth() / 2f, actor.getY() + actor.getHeight() / 2f, 0);

		cuerpo.setUserData(this);

		cuerpo.createFixture(fixtureDef);
		
		cuerpo.setAngularVelocity(MathUtils.degreesToRadians * 360f);

		shape.dispose();

		BodyDef bodyDef1 = new BodyDef();

		bodyDef1.type = BodyDef.BodyType.StaticBody;

		FixtureDef fixtureDef1 = new FixtureDef();

		EdgeShape shape1 = new EdgeShape();

		shape1.set(0, 0, 6.4f, 0);

		fixtureDef1.shape = shape1;

		Body cuerpo1 = mundoVirtual.createBody(bodyDef1);

		cuerpo1.createFixture(fixtureDef1);

		shape.dispose();

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();

		pincel.dispose();

		Gdx.input.setInputProcessor(null);

		nivel.dispose();

		mundoVirtual.dispose();

		debugRenderer.dispose();

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		super.render();

		ScreenUtils.clear(0.0F, 0.0F, 1.0F, 1.0f, true);

		float delta = Gdx.graphics.getDeltaTime();

		accumulator += Math.min(delta, 0.25f);

		if (accumulator >= STEP_TIME) {

			accumulator -= STEP_TIME;

			mundoVirtual.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

		}

		// mundoVirtual.step(delta, 8, 6);

		pincel.setProjectionMatrix(camara.combined);

		camara.update();

		///////////////////

		actor.setPosition(cuerpo.getPosition().x - actor.getWidth() / 2f,
				cuerpo.getPosition().y - actor.getHeight() / 2f);
		
		actor.setOriginCenter();
		
		actor.setRotation(cuerpo.getAngle()*MathUtils.radiansToDegrees);
		
		pincel.begin();

		actor.draw((Batch) pincel);
		
	

		pincel.end();

		//////////////////////

		nivel.draw();

		nivel.act();

		debugRenderer.render(mundoVirtual, camara.combined);
	}

}
