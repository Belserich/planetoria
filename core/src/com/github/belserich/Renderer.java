package com.github.belserich;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.belserich.entity.component.Draw;
import com.github.belserich.util.RelGlyphLayout;

public class Renderer {
	
	public static final float WIDTH_UNITS = 4;
	public static final float HEIGHT_UNITS = 3;
	
	private Batch batch;
	private OrthographicCamera gameCam, screenCam;
	private Viewport gameView, screenView;
	
	private BitmapFont font;
	
	private ImmutableArray<Entity> drawables;
	
	public Renderer(Engine engine) {
		
		batch = new SpriteBatch();
		
		final int screenWidth = Gdx.graphics.getWidth();
		final int screenHeight = Gdx.graphics.getHeight();
		
		gameCam = new OrthographicCamera();
		gameView = new ExtendViewport(WIDTH_UNITS, HEIGHT_UNITS, gameCam);
		gameView.update(screenWidth, screenHeight);
		
		screenCam = new OrthographicCamera();
		screenView = new ScreenViewport(screenCam);
		screenView.update(screenWidth, screenHeight, true);
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gugi.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 12;
		font = generator.generateFont(parameter);
		generator.dispose();
		
		drawables = engine.getEntitiesFor(Family.all(Draw.class).get());
	}
	
	public void render() {
		
		gameCam.update();
		screenCam.update();
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gameView.apply();
		batch.setProjectionMatrix(gameCam.combined);
		
		batch.begin();
		drawSprites();
		batch.end();
		
		screenView.apply();
		batch.setProjectionMatrix(screenCam.combined);
		
		batch.begin();
		drawTexts();
		batch.end();
	}
	
	private void drawSprites() {
		
		for (Entity drawable : drawables) {
			
			Draw dc = drawable.getComponent(Draw.class);
			for (Sprite sprite : dc.sprites) {
				sprite.draw(batch);
			}
		}
	}
	
	private void drawTexts() {
		
		for (Entity drawable : drawables) {
			
			Draw dc = drawable.getComponent(Draw.class);
			Vector2 pxCoords;
			
			for (RelGlyphLayout lout : dc.glyphs) {
				
				pxCoords = project(lout.unitsX(), lout.unitsY());
				font.draw(
						batch,
						lout.layout(),
						pxCoords.x,
						pxCoords.y
				);
			}
		}
	}
	
	private Vector2 project(float x, float y) {
		Vector2 vec = new Vector2(x, y);
		vec = gameView.project(vec);
		return screenView.unproject(vec);
	}
	
	public void resize(int width, int height) {
		gameView.update(width, height);
		screenView.update(width, height, true);
	}
	
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
	
	public Viewport gameView() {
		return gameView;
	}
	
	public Viewport screenView() {
		return screenView;
	}
	
	public RelGlyphLayout createRelGlyphLayout(String str, float x, float y) {
		return new RelGlyphLayout(new GlyphLayout(font, str), x, y);
	}
	
	public RelGlyphLayout createRelGlyphLayout(String str, float x, float y, Color color, float targetWidth, int halign, boolean wrap) {
		
		final BitmapFont font = this.font;
		
		x -= targetWidth / 2f;
		targetWidth = 0.3888f * gameView.getScreenWidth() / WIDTH_UNITS;
		
		return new RelGlyphLayout(new GlyphLayout(font, str, color, targetWidth, Align.center, true), x, y);
	}
}
