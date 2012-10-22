package org.exoplatform.example;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;

import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessEvent;
import javax.portlet.RenderMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

public class MyListenerPortlet extends GenericPortlet
{
	@RenderMode(name = "view")
	public void viewNormal(RenderRequest request, RenderResponse response)
			throws PortletException, IOException
	{
		getPortletContext().getRequestDispatcher("/normal.jsp").forward(
				request, response);
	}

	/**
	 * This method processes received events with the following QName
	 */
	@ProcessEvent(qname = "{jcr286EventDemo}contactInfo")
	public void processEvent(EventRequest request, EventResponse response)
			throws PortletException, IOException
	{

		String contactInfo = (String) request.getEvent().getValue();

		ContactInfo contactInfoBean = new ContactInfo();

		contactInfoBean.setEmail("No Email");

		contactInfoBean.setName("No Name");

		try
		{

			contactInfoBean.setName(contactInfo.split(",")[0]);
			contactInfoBean.setEmail(contactInfo.split(",")[1]);

		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.getPortletSession()
				.setAttribute("contactInfo", contactInfoBean);
	}

}
