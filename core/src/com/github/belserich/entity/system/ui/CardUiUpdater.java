package com.github.belserich.entity.system.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.Services;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntityActor;
import com.github.belserich.ui.core.CardUpdater;
import com.github.belserich.ui.core.UiService;

public class CardUiUpdater extends EntityActor {
	
	private static UiService service = Services.getUiService();
	
	@Override
	public Family actors() {
		return Family.all(
				CardId.class,
				CardType.class
		).exclude(
				Dead.class
		).get();
	}
	
	@Override
	public void entityUpdate(Entity actor) {
		
		CardId hc = actor.getComponent(CardId.class);
		CardType tc = actor.getComponent(CardType.class);
		
		CardUpdater up = service.getCardUpdater(hc.val);
		up.setType(tc.type);
		
		Name nc = actor.getComponent(Name.class);
		up.setTitle(nc != null ? nc.name : "???");
		
		Covered cc = actor.getComponent(Covered.class);
		up.setCovered(cc != null);
		
		switch (tc.type) {
			
			case DEFAULT:
				
				Lp lc = actor.getComponent(Lp.class);
				Ap ac = actor.getComponent(Ap.class);
				Sp sc = actor.getComponent(Sp.class);
				
				up.setLp(lc != null ? lc.pts : 0);
				up.setAp(ac != null ? ac.pts : 0);
				up.setSp(sc != null ? sc.pts : 0);
				
				break;
			
			case STRATEGY:
				
				Effect ec = actor.getComponent(Effect.class);
				up.setEffect(ec != null ? ec.text : "");
				
				break;
			
			default:
				
				GameClient.error(this, "* Card act. Unknown card type: %s", tc.type);
				break;
		}
		
		up.update();
	}
}
