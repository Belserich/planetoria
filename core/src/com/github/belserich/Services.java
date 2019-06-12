package com.github.belserich;

import com.github.belserich.ui.UiService;
import com.github.belserich.ui.UiServiceImpl;

public class Services {
	
	private static UiService uiService;
	
	public static UiService getUiService() {
		if (uiService == null) {
			uiService = new UiServiceImpl();
		}
		return uiService;
	}
}
