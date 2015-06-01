package es.usa.isa.cgis.proyect.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

import es.us.isa.cgis.proyect.server.entities.UserEntity;
import es.us.isa.cgis.proyect.views.ViewEdit;
import es.us.isa.cgis.proyect.views.ViewList;
import es.us.isa.cgis.proyect.views.ViewLogin;




/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GGIS_Proyect_Paciente implements EntryPoint {
	
	
	public static final String LIST = "list";
	public static final String INIT = "init";
	public static final String CREATE = "create";
	public static final String UPDATE = "update";
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);
	
	
	
	@Override
	public void onModuleLoad() {
		 String sessionID = Cookies.getCookie("sid");
		    if (sessionID == null) {
		    	go(INIT, new HashMap<String,String>());
		    } else  {
		        checkWithServerIfSessionIdIsStillLegal(sessionID);
		    }
	}
	
	private void checkWithServerIfSessionIdIsStillLegal(String sessionID)
	{
	    loginService.loginFromSessionServer(new AsyncCallback<UserEntity>()
	    {
	        @Override
	        public void onFailure(Throwable caught)
	        {
	    		go(INIT, new HashMap<String,String>());
	        }
	 
	        @Override
	        public void onSuccess(UserEntity result)
	        {
	            if (result == null)
	            {
	        		go(INIT, new HashMap<String,String>());
	            } else
	            {
            		go(LIST, new HashMap<String,String>());

	            }
	        }
	 
	    });
	}
	
	
	public static void go(String token){
		GGIS_Proyect_Paciente.go(token, new HashMap<String,String>());
	}
	
	// FLOW MANAGEMENT
	public static void go(String token, Map<String,String> params){
		Panel p = RootPanel.get();
		if (token == INIT){
			p.clear();
			p.add(new ViewLogin());
		}
		if (token==LIST){
			p.clear();
			p.add(new ViewList());
		}else if (token==CREATE ){
			//NEW WINDOW: p.clear();
			p.add(new ViewEdit(params));
		}else if (token==UPDATE){
			//NEW WINDOW: p.clear();
			p.add(new ViewEdit(params));
		}
	}
}
