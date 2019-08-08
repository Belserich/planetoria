package com.github.belserich;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.github.belserich.ui.StdUiService;
import com.github.belserich.ui.core.UiService;

public class Services {
	
	private static UiService uiService;
	
	private static Batch batch;
	
	private static Camera boardCamera;
	private static Camera textCamera;
	
	private static BitmapFont font;
	
	public static UiService getUiService() {
		if (uiService == null) {
			uiService = new StdUiService();
		}
		return uiService;
	}
	
	public static Camera getBoardCamera() {
		return boardCamera;
	}
	
	public static void setBoardCamera(Camera boardCamera) {
		Services.boardCamera = boardCamera;
	}
	
	public static Camera getTextCamera() {
		return textCamera;
	}
	
	public static void setTextCamera(Camera textCamera) {
		Services.textCamera = textCamera;
	}
	
	public static Batch getBatch() {
		return batch;
	}
	
	public static void setBatch(Batch batch) {
		Services.batch = batch;
	}
	
	public static BitmapFont getFont() {
		return font;
	}
	
	public static void setFont(BitmapFont font) {
		Services.font = font;
	}
}
