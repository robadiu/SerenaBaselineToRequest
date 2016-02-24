package almaviva.serena.business;
import java.util.List;
import java.util.Properties;

import com.serena.dmclient.api.*;
import com.serena.dmclient.collections.Products;
import com.serena.dmclient.objects.DimensionsObject;
import com.serena.dmclient.objects.Product;

import almaviva.serena.ConnectionSerena;


public class GestioneSerenaDM  implements ConnectionSerena{
	
	public DimensionsConnection getConnection(String username, String pwd, String dbName, String dbConn, String server){
		DimensionsConnectionDetails details = new DimensionsConnectionDetails();
		Properties properties = new Properties();
		details.setUsername("botjenkins");
		details.setPassword("jenkinsbot.001");
		details.setDbName("siancm");
		details.setDbConn("dim9");
		details.setServer("172.21.2.10");
		DimensionsConnection connection = DimensionsConnectionManager
				.getConnection(details);
		return connection;
	}
	public Request findRequest(final DimensionsConnection connection, final String requestID) {
		// find the request QLARIUS_CR_1 - note that normally you would have
		// obtained a Request instance through some other means than this
		// unless you actually know the request ID in advance.
		Request requestObj = connection.getObjectFactory().findRequest(
				requestID);
		// note that you should refer to the attribute numbers by symbolic
		// constant where possible; certain of the SystemAttributes may
		// change their int value in future releases of the Java API.
		return requestObj;
	}
	public Baseline findBaseline(DimensionsConnection connection, final String baselineID) throws Exception{
		Filter filter = new Filter();
		filter.criteria().add(new Filter.Criterion(SystemAttributes.OBJECT_SPEC, baselineID, Filter.Criterion.EQUALS));
		List<Baseline> baselines=connection.getObjectFactory().getBaselines(filter);
		if(baselines.isEmpty()){
			throw new Exception("Baseline non trovata "+baselineID);
		}
		return baselines.get(0);
	}
	@SuppressWarnings("unchecked")
	public  Project findProject(DimensionsConnection connection, String productID, String projectID) {
		DimensionsDatabaseAdmin defaultDatabase = connection.getObjectFactory().getBaseDatabaseAdmin();
		Products products = defaultDatabase.getProducts();
		Product product = products.get(productID);
		Filter filter = new Filter();
		filter.criteria().add(
				new Filter.Criterion(SystemAttributes.OBJECT_ID, projectID, Filter.Criterion.EQUALS));
		List<Project> projects = product.getProjects(filter);
		return projects.get(0);
	}
	@SuppressWarnings("unchecked")
	public  ItemRevision findItem(Project project, String itemID, String variant, String itemType, String revision) {
		Filter filter = new Filter();
		filter.criteria().add(
				new Filter.Criterion(SystemAttributes.OBJECT_ID, itemID, Filter.Criterion.EQUALS));
		filter.criteria().add(
				new Filter.Criterion(SystemAttributes.VARIANT, variant, Filter.Criterion.EQUALS));
		filter.criteria().add(
				new Filter.Criterion(SystemAttributes.TYPE_NAME, itemType, Filter.Criterion.EQUALS));
		filter.criteria().add(
				new Filter.Criterion(SystemAttributes.REVISION, revision, Filter.Criterion.EQUALS));
		List<DimensionsRelatedObject> relObjs = project.getChildItems(filter);
		DimensionsRelatedObject relObj = relObjs.get(0);
		return  (ItemRevision) relObj.getObject();
	}
	public boolean createRelation(Request request, DimensionsObject item){
		DimensionsResult dr=request.addChild(SystemRelationship.IN_RESPONSE, item);
		return true;
	}
	
}
