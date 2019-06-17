package com.github.belserich;

import com.github.belserich.ui.StdUiService;
import com.github.belserich.ui.core.UiService;

public class Services {
	
	private static UiService uiService;
	
	public static UiService getUiService() {
		if (uiService == null) {
			uiService = new StdUiService();
		}
		return uiService;
	}
}
