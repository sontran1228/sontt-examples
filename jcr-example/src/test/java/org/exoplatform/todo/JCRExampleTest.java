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
public class JCRExampleTest extends TestCase 
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
		Node encyclopedia = rootNode.addNode("todo:todosList");
		Node node1 = encyclopedia.addNode("todo:entry");
		node1.setProperty("todo:title", new String("JAVA"));
		node1.setProperty("todo:content", new String("java"));
		node1.setProperty("todo:category", new String[] { "programing language",
				"sun" });

		Node n = encyclopedia.addNode("todo:entry");
		n.setProperty("todo:title", new String(".Net"));
		n.setProperty("todo:content", new String(".Net"));
		n.setProperty("todo:category", new String[] { "programming language", "Microsoft" });
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
		Node encyclopedia = rootNode.getNode("todo:todosList");
		NodeIterator entries = encyclopedia.getNodes("todo:entry");
		while (entries.hasNext()) {
			Node entry = entries.nextNode();

			System.out.println("Name: " + entry.getName());
			System.out.println("Title: " + entry.getProperty("todo:title").getString());
			System.out.println("Content: "
					+ entry.getProperty("todo:content").getString());
			System.out.println("Path: " + entry.getPath());

			Property category = entry.getProperty("todo:category");

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
				"//todo:todosList/todo:entry[@todo:title = 'JAVA']",
				Query.XPATH);

		QueryResult result = q.execute();
		NodeIterator it = result.getNodes();

		while (it.hasNext()) {
			Node n = it.nextNode();
			System.out.println("Name: " + n.getName());
			System.out.println("Title: " + n.getProperty("todo:title").getString());
			System.out.println("Content: " + n.getProperty("todo:content").getString());
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
						"SELECT * FROM nt:unstructured WHERE CONTAINS(todo:tile, 'java')",
						Query.SQL);
		QueryResult result = q.execute();
		NodeIterator it = result.getNodes();

		while (it.hasNext()) {
			Node n = it.nextNode();
			System.out.println("Name: " + n.getName());
			System.out.println("Title: " + n.getProperty("todo:title").getString());
			System.out.println("Content: " + n.getProperty("todo:content").getString());
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
		ntValue.setMixin(false);
		ntValue.setPrimaryItemName("");
		ntValue.setDeclaredSupertypeNames(superTypes);
		
		// Define Property value
		List<PropertyDefinitionValue> pros = new ArrayList<PropertyDefinitionValue>();
		
		List<String> defValue = new ArrayList<String>();
		defValue.add("24");
		
		// name: default name (cannot change if assigned).
		pros.add(new PropertyDefinitionValue("title", true, false, 1, false,
				defValue, true, PropertyType.DOUBLE, new ArrayList<String>()));
		ntValue.setDeclaredPropertyDefinitionValues(pros);
		extendedNTManager.registerNodeType(ntValue, ExtendedNodeTypeManager.FAIL_IF_EXISTS);
		
		Node root = session.getRootNode();
		Node testNode = root.addNode("testNode", "todo:testRegNT");
		
		DoubleValue[] dv = new DoubleValue[3];
		dv[0] = new DoubleValue(30);
		dv[1] = new DoubleValue(31);
		dv[2] = new DoubleValue(32);
		testNode.setProperty("title", dv);
		
		session.save();
		Property property = testNode.getProperty("title");
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
