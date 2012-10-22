package org.exoplatform.example;

import javax.portlet.PortletPreferences;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.application.portlet.PortletRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.core.UIPortletApplication;
import org.exoplatform.webui.core.lifecycle.UIApplicationLifecycle;

@ComponentConfig(lifecycle = UIApplicationLifecycle.class, template = "app:/groovy/webui/portlet/UITestForm.gtmpl")
public class UITestForm extends UIPortletApplication
{

	private String text;

	public UITestForm() throws Exception
	{
		super();
	}

	public void init()
	{
		PortletRequestContext portletRequestContext = WebuiRequestContext
				.getCurrentInstance();
		PortletPreferences preferences = portletRequestContext.getRequest()
				.getPreferences();
		text = preferences.getValue(UIContainerTestPortlet.TEXT_PREFERENCE,
				null);
	}

	public void processRender(WebuiRequestContext context) throws Exception
	{
		init();
		super.processRender(context);
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}
}
