// This annotation declares a Juzu application
@Application(defaultController = juzu.example.calculator.Calculator.class)

// defines the route of the application when the application is used as a servlet: domainName/contextPath/routeName
@Route("/index")

// generates a portlet application class used by the portlet container that named as lastPackageName+Portlet
// in this example, the name will be ExamplePortlet
//@juzu.plugin.portlet.Portlet
@Assets(
   stylesheets={
      @Stylesheet(src="resources/css/jquery-ui-1.9.2.css")
      ,@Stylesheet(src="resources/css/mytodo.css")
   }
   ,scripts={
      @Script(id="jquery", src = "resources/js/jquery-1.8.3.js")
      ,@Script(id="jquery-ui", src="resources/js/jquery-ui-1.9.2.js", depends="jquery")
      ,@Script(id="addtodo", src="resources/js/addtodo.js", depends="jquery")
   }
   , location= AssetLocation.SERVER)
package juzu.example.calculator;

import juzu.asset.AssetLocation;
import juzu.Application;
import juzu.Route;
import juzu.plugin.asset.*;

