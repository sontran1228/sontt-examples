package org.exoplatform.todo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.Credentials;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.PropertyType;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.Workspace;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import junit.framework.TestCase;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.jcr.config.RepositoryConfigurationException;
import org.exoplatform.services.jcr.core.ManageableRepository;
import org.exoplatform.services.jcr.core.nodetype.ExtendedNodeTypeManager;
import org.exoplatform.services.jcr.core.nodetype.NodeTypeValue;
import org.exoplatform.services.jcr.core.nodetype.PropertyDefinitionValue;
import org.exoplatform.services.jcr.impl.core.SessionImpl;
import org.exoplatform.services.jcr.impl.core.value.DoubleValue;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase 
{
	private RepositoryService repoService;

	protected void setUp() throws Exception 
	{
		System.out.println("-----------------setUp()-----------------");
		PortalContainer container = PortalContainer.getInstance();
		this.repoService = (RepositoryService) container
				.getComponentInstanceOfType(RepositoryService.class);
		if (System.getProperty("java.security.auth.login.config") == null) {
			System.setProperty(
					"java.security.auth.login.config",
					Thread.currentThread().getContextClassLoader()
							.getResource("login.conf").toString());
		}
	}

	public void testWorkspacesName() throws Exception 
	{
		System.out.println("-----------------testWorkspacesName()-----------------");
		String[] workspacesName = repoService.getDefaultRepository()
				.getWorkspaceNames();
		
		assertEquals(3, workspacesName.length);
		
		for (String ws : workspacesName) {
			System.out.println("-----------------workspace name: " + ws
					+ "-----------------");
		}
	}
	
	public void testLogin() throws RepositoryException, RepositoryConfigurationException 
	{
		System.out.println("-----------------testLogin()-----------------");
		Credentials credential = new SimpleCredentials("exo", "exo".toCharArray());
		Repository repository = repoService.getRepository("repository");
		Session session = repository.login(credential,"production");
		assertNotNull(session);
		assertEquals("exo", session.getUserID());
		assertEquals("production", session.getWorkspace().getName());
		session.logout();
	}
	
	/*public void testBrowsingAllNode() throws RepositoryException, RepositoryConfigurationException {
		System.out.println("-----------------testBrowsingAllNode()-----------------");
		Credentials credential = new SimpleCredentials("exo", "exo".toCharArray());
		Repository repository = repoService.getRepository("repository");
		Session session = repository.login(credential,"production");
		Node root = session.getRootNode();
		dump(root);
	}*/
	
	public void testAddingNode() throws RepositoryException, RepositoryConfigurationException 
	{
		System.out.println("-----------------testAddingNode()-----------------");
		Credentials cred = new SimpleCredentials("exo", "exo".toCharArray());
		Repository repository = repoService.getRepository("repository");
		Session session = repository.login(cred, "production");
		assertNotNull(session);
		
		// Get root node
		Node rootNode = session.getRootNode();
		Node encyclopedia = rootNode.addNode("wiki:encyclopedia");
		Node p = encyclopedia.addNode("wiki:entry");
		p.setProperty("wiki:title", new String("JAVA"));
		p.setProperty("wiki:content", new String("java"));
		p.setProperty("wiki:category", new String[] { "programing language",
				"sun" });

		Node n = encyclopedia.addNode("wiki:entry");
		n.setProperty("wiki:title", new String("November Rain"));
		n.setProperty("wiki:content", new String("A song of Gun and Rose"));
		n.setProperty("wiki:category", new String[] { "song", "music" });
		session.save();
		session.logout();
		
//		dump(rootNode);
	}
	
	public void testBrowsingContent() throws RepositoryException, RepositoryConfigurationException 
	{
		System.out.println("-----------------testBrowsingContent()-----------------");
		Credentials cred = new SimpleCredentials("exo", "exo".toCharArray());
		Repository repository = repoService.getRepository("repository");
		Session session = repository.login(cred, "production");
		assertNotNull(session);

		Node rootNode = session.getRootNode();
		Node encyclopedia = rootNode.getNode("wiki:encyclopedia");
		NodeIterator entries = encyclopedia.getNodes("wiki:entry");
		while (entries.hasNext()) {
			Node entry = entries.nextNode();

			System.out.println("Name: " + entry.getName());
			System.out.println("Title: " + entry.getProperty("wiki:title").getString());
			System.out.println("Content: "
					+ entry.getProperty("wiki:content").getString());
			System.out.println("Path: " + entry.getPath());

			Property category = entry.getProperty("wiki:category");

			try {
				String c = category.getValue().getString();
				System.out.println("Category: " + c);
			} catch (ValueFormatException e) {
				Value[] categories = category.getValues();
				for (Value c : categories) {
					System.out.println("Category: " + c.getString());
				}
			}
		}
		
		session.logout();
	}
	
	public void testSearchingContentWithXPath() throws Exception
	{
		System.out
				.println("-----------------testSearchingContentWithXPath()-----------------");
		Credentials cred = new SimpleCredentials("exo", "exo".toCharArray());
		Repository repository = repoService.getRepository("repository");
		Session session = repository.login(cred, "production");
		Workspace ws = session.getWorkspace();

		QueryManager qm = ws.getQueryManager();
		Query q = qm.createQuery(
				"//wiki:encyclopedia/wiki:entry[@wiki:title = 'JAVA']",
				Query.XPATH);

		QueryResult result = q.execute();
		NodeIterator it = result.getNodes();

		while (it.hasNext()) {
			Node n = it.nextNode();
			System.out.println("Name: " + n.getName());
			System.out.println("Title: " + n.getProperty("wiki:title").getString());
			System.out.println("Content: " + n.getProperty("wiki:content").getString());
		}
		session.logout();
	}

	public void testSearchingContentWithLuceneFuzzy() throws Exception 
	{
		System.out
				.println("-----------------testSearchingContentWithLuceneFuzzy()-----------------");
		Credentials cred = new SimpleCredentials("exo", "exo".toCharArray());
		Repository repository = repoService.getRepository("repository");
		Session session = repository.login(cred, "production");
		Workspace ws = session.getWorkspace();
		QueryManager qman = ws.getQueryManager();
		Query q = qman
				.createQuery(
						"SELECT * FROM nt:base WHERE CONTAINS(wiki:tile, 'java')",
						Query.SQL);
		QueryResult result = q.execute();
		NodeIterator it = result.getNodes();

		while (it.hasNext()) {
			Node n = it.nextNode();
			System.out.println("Name: " + n.getName());
			System.out.println("Title: " + n.getProperty("wiki:title").getString());
			System.out.println("Content: " + n.getProperty("wiki:content").getString());
		}
		
		session.logout();
	}
	
	public void testRegisterNodeType() throws RepositoryException, RepositoryConfigurationException, IOException 
	{
		System.out
				.println("-----------------testRegisterNodeType()-----------------");
		ManageableRepository repository = repoService.getRepository("repository");
		SessionImpl session = (SessionImpl) repository.getSystemSession("production");
		ExtendedNodeTypeManager extendedNTManager = (ExtendedNodeTypeManager) session
				.getWorkspace().getNodeTypeManager();
		
		// Declare node type value
		NodeTypeValue ntValue = new NodeTypeValue();
		// Define parent of this node type
		List<String> superTypes = new ArrayList<String>();
		superTypes.add("nt:base");
		// Define attributes of this node type
		ntValue.setName("todo:testRegNT");
		ntValue.setMixin(true);
		ntValue.setPrimaryItemName("");
		ntValue.setDeclaredSupertypeNames(superTypes);
		
		// Define Property value
		List<PropertyDefinitionValue> pros = new ArrayList<PropertyDefinitionValue>();
		
		List<String> defValue = new ArrayList<String>();
		defValue.add("24");
		
		// name: default name (cannot change if assigned).
		pros.add(new PropertyDefinitionValue("todo:title", true, false, 1, false,
				defValue, true, PropertyType.DOUBLE, new ArrayList<String>()));
		ntValue.setDeclaredPropertyDefinitionValues(pros);
		extendedNTManager.registerNodeType(ntValue, ExtendedNodeTypeManager.FAIL_IF_EXISTS);
		
		Node root = session.getRootNode();
		Node testNode = root.addNode("testNode", "todo:testRegNT");
		
		DoubleValue[] dv = new DoubleValue[3];
		dv[0] = new DoubleValue(30);
		dv[1] = new DoubleValue(31);
		dv[2] = new DoubleValue(32);
		testNode.setProperty("todo:title", dv);
		
		session.save();
		Property property = testNode.getProperty("studentAge");
		System.out.println(property.getName());
		Value[] value = property.getValues();
		for (Value v : value) {
			System.out.println(v);
		}
		
		session.logout();
	}
	
	@SuppressWarnings("unused")
	private static void dump(Node node) throws RepositoryException {
		// First output the node path
		System.out.println(node.getPath());
		// Skip the virtual (and large!) jcr:system subtree
		if (node.getName().equals("jcr:system")) {
			return;
		}

		// Then output the properties
		PropertyIterator properties = node.getProperties();
		while (properties.hasNext()) {
			Property property = properties.nextProperty();
			if (property.getDefinition().isMultiple()) {
				// A multi-valued property, print all values
				Value[] values = property.getValues();
				for (int i = 0; i < values.length; i++) {
					System.out.println(property.getPath() + " = "
							+ values[i].getString());
				}
			} else {
				// A single-valued property
				System.out.println(property.getPath() + " = "
						+ property.getString());
			}
		}

		// Finally output all the child nodes recursively
		NodeIterator nodes = node.getNodes();
		while (nodes.hasNext()) {
			dump(nodes.nextNode());
		}
	}
}
