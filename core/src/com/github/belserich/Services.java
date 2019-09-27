package com.github.belserich;

import com.github.belserich.ui.StdUiService;
import com.github.belserich.ui.core.UiService;

public class Services {
	
	private static UiService uiService;
	private static Renderer renderer;
	
	public static UiService getUiService() {
		if (uiService == null) {
			uiService = new StdUiService();
		}
		return uiService;
	}
	
	public static Renderer getRenderer() {
		return renderer;
	}
	
	public static void setRenderer(Renderer renderer) {
		Services.renderer = renderer;
	}
}
