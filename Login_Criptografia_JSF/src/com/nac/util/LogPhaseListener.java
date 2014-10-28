package com.nac.util;


import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;


public class LogPhaseListener implements PhaseListener {

	
	@Override
	public void afterPhase(PhaseEvent event) {
		
		FacesContext context = event.getFacesContext();  
        ExternalContext ext = context.getExternalContext();  
        HttpSession session = (HttpSession) ext.getSession(false);  
        boolean newSession = (session == null) || (session.isNew());  
        boolean desconectado = newSession;  
 
        if (desconectado) {  
        	FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(context, null, "login");
        }  
 }

	@Override
	public void beforePhase(PhaseEvent event) {
		System.out.println("FASE: " + event.getPhaseId());
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
