package almaviva.serena;

import com.serena.dmclient.api.Baseline;
import com.serena.dmclient.api.DimensionsConnection;
import com.serena.dmclient.api.ItemRevision;
import com.serena.dmclient.api.Project;
import com.serena.dmclient.api.Request;
import com.serena.dmclient.objects.DimensionsObject;

public interface ConnectionSerena {

	public DimensionsConnection getConnection(String username, String pwd, String dbName, String dbConn, String server);
	public Request findRequest(final DimensionsConnection connection, final String requestID);
	public Baseline findBaseline(DimensionsConnection connection, final String baselineID) throws Exception;
	public  Project findProject(DimensionsConnection connection, String productID, String projectID) ;
	public  ItemRevision findItem(Project project, String itemID, String variant, String itemType, String revision);
	public boolean createRelation(Request request, DimensionsObject item);
}
