package org.exoplatform.example;

import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;

import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.application.portlet.PortletRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.lifecycle.UIFormLifecycle;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.exoplatform.webui.form.UIForm;
import org.exoplatform.webui.form.UIFormStringInput;
import org.exoplatform.webui.form.validator.MandatoryValidator;
import org.exoplatform.webui.form.validator.StringLengthValidator;

@ComponentConfig(lifecycle = UIFormLifecycle.class, template = "system:/groovy/webui/form/UIForm.gtmpl", events = {
		@EventConfig(listeners = UITestStringInput.SaveActionListener.class),
		@EventConfig(listeners = UITestStringInput.CancelActionListener.class) })
public class UITestStringInput extends UIForm
{

	public static final String TEXT_STRING_INPUT = "UITestPortletTextStringInput";

	public UITestStringInput() throws Exception
	{
		PortletRequestContext portletRequestContext = WebuiRequestContext
				.getCurrentInstance();
		PortletPreferences preferences = portletRequestContext.getRequest()
				.getPreferences();
		String text = preferences.getValue(
				UIContainerTestPortlet.TEXT_PREFERENCE, null);
		addChild(new UIFormStringInput(TEXT_STRING_INPUT, TEXT_STRING_INPUT,
				text).addValidator(MandatoryValidator.class).addValidator(StringLengthValidator.class, 5, 20));
		setActions(new String[] { "Save", "Cancel" });
	}

	public static class SaveActionListener extends
			EventListener<UITestStringInput>
	{
		@Override
		public void execute(Event<UITestStringInput> event) throws Exception
		{
			UITestStringInput basicConfig = event.getSource();
			UIFormStringInput textStringInput = basicConfig
					.getUIStringInput(TEXT_STRING_INPUT);
			PortletRequestContext portletRequestContext = WebuiRequestContext
					.getCurrentInstance();
			PortletPreferences preferences = portletRequestContext.getRequest()
					.getPreferences();
			preferences.setValue(UIContainerTestPortlet.TEXT_PREFERENCE,
					textStringInput.getValue());
			preferences.store();
			PortletRequestContext context = (PortletRequestContext) event
					.getRequestContext();
			context.setApplicationMode(PortletMode.VIEW);
		}
	}

	public static class CancelActionListener extends
			EventListener<UITestStringInput>
	{
		@Override
		public void execute(Event<UITestStringInput> event) throws Exception
		{
			PortletRequestContext context = (PortletRequestContext) event
					.getRequestContext();
			context.setApplicationMode(PortletMode.VIEW);
		}
	}

}