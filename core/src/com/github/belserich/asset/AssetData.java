package com.github.belserich.asset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;
import com.github.belserich.GameClient;

public class AssetData {
	
	private static final AssetData global = new AssetData();
	
	private AssetManager manager;
	private IntMap<Object> assets;
	
	private AssetData() {
		manager = new AssetManager();
		assets = new IntMap<Object>();
	}
	
	public static AssetData global() {
		return global;
	}
	
	private void registerTextureRegion(Assets asset, TextureRegion tex) {
		checkRegistered(asset);
		assets.put(asset.ordinal(), tex);
	}
	
	public void checkRegistered(Assets label) {
		if (isRegistered(label)) {
			GameClient.log(this, "Overwriting " + label.name() + " asset data!");
		}
	}
	
	public boolean isRegistered(Assets label) {
		return assets.containsKey(label.ordinal());
	}
	
	public void registerAssets() {
	
	}
	
	public void dispose() {
		manager.clear();
		assets.clear();
	}
}
