<%@page import="org.exoplatform.example.ContactInfo"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<portlet:defineObjects />

<%!ContactInfo contactInfo = null;%>
<%
	//retrieve the object from the session
	contactInfo = (ContactInfo) renderRequest.getPortletSession()
			.getAttribute("contactInfo");

	if (contactInfo != null)
	{
%>
<div>
	<table>
		<tbody>
			<tr>
				<th>Name:</th>
				<td><%=contactInfo.getName()%></td>
			</tr>
			<tr>
				<th>Email:</th>
				<td><%=contactInfo.getEmail()%></td>
			</tr>
		</tbody>
	</table>
</div>
<%
	} else
	{
%><p>No contact information.</p>
<%
	}
%>