// This annotation declares a Juzu application
@Application(defaultController = juzu.example.ex1.JuzuExample.class)

@Portlet(name="JuzuExamplesPortlet")
@Servlet("/")
@Assets(
   scripts = {
      @Script(id = "json", src="resources/js/json2.js")
      , @Script(id = "jquery", src="resources/js/jquery.js")
      , @Script(id = "underscore", src="resources/js/underscore.js")
      , @Script(id = "backbone", src="resources/js/backbone.js", depends = { "jquery","underscore" })
      , @Script(id = "backbone-storage", src="resources/js/backbone-localstorage.js", depends = { "jquery","backbone" })
      , @Script(id = "todo", src="resources/js/todos.js", depends = { "jquery","backbone" })
   }
   , stylesheets = {
      @Stylesheet(src="resources/css/todos.css")
   }
   
   , location = juzu.asset.AssetLocation.SERVER
)
package juzu.example.ex1;

import juzu.Application;
import juzu.plugin.asset.*;
import juzu.plugin.servlet.Servlet;
import juzu.plugin.portlet.*;