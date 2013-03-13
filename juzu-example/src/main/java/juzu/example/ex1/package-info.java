// This annotation declares a Juzu application
@Application(defaultController = juzu.example.ex1.JuzuExample.class)

@Portlet(name="JuzuExamplesPortlet")
@Servlet("/")
@Assets(
   scripts = {
      @Script(id = "jquery", src="resources/js/jquery.js")
      , @Script(id = "juzu.ajax", src="resources/js/jz-ajax.js", depends = "jquery" )
      , @Script(id = "juzu.todo", src="resources/js/jz-todo.js", depends = "jquery" )
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