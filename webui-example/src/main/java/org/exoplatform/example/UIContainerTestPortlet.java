package org.exoplatform.example;

import javax.portlet.PortletMode;

import org.exoplatform.webui.application.portlet.PortletRequestContext;
import org.exoplatform.webui.application.WebuiApplication;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.core.UIPortletApplication;
import org.exoplatform.webui.core.lifecycle.UIApplicationLifecycle;

@ComponentConfig(lifecycle = UIApplicationLifecycle.class)
public class UIContainerTestPortlet extends UIPortletApplication
{

	public static final String TEXT_PREFERENCE = "text";

	public UIContainerTestPortlet() throws Exception
	{
		super();
	}

	public void processRender(WebuiApplication app, WebuiRequestContext context)
			throws Exception
	{
		getChildren().clear();
		PortletRequestContext pContext = (PortletRequestContext) context;
		PortletMode currentMode = pContext.getApplicationMode();
		if (PortletMode.VIEW.equals(currentMode))
		{
			if (this.getChild(UITestForm.class) == null)
				this.addChild(UITestForm.class, null, null);
		} else
		{
			if (this.getChild(UITestStringInput.class) == null)
				this.addChild(UITestStringInput.class, null, null);
		}
		super.processRender(app, context);
	}

}
