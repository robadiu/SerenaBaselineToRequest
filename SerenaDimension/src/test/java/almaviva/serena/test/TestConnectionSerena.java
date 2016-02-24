package almaviva.serena.test;

import java.util.List;

import com.serena.dmclient.api.Baseline;
import com.serena.dmclient.api.DimensionsConnection;
import com.serena.dmclient.api.Request;

import almaviva.serena.business.GestioneSerenaDM;

public class TestConnectionSerena {
	public static void main(String[] args) throws Exception {
		GestioneSerenaDM t = new GestioneSerenaDM();
		
		  DimensionsConnection d =t.getConnection("botjenkins", "jenkinsbot.001", "siancm", "dim9", "172.21.2.10");
		  Request r = t.findRequest(d, "JENTEST_INTERVENTO_2");
		  Baseline b = t.findBaseline(d, "JENTEST:SCARICO15AR-12");
		  boolean result =t.createRelation(r, b);
		  List p =b.getAllLcStates();
		 
	}

}
