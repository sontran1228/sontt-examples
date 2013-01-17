package juzu.example.calculator;

import juzu.Action;

import juzu.Response;

import juzu.Route;

import juzu.example.CalculatorService;

import juzu.Path;
import juzu.View;
import juzu.template.Template;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Calculator
{
   @Inject
   CalculatorService calculatorService;

   @Inject
   @Path("index.gtmpl")
   Template index;

   @View
   public void index() throws IOException
   {
      index("");
   }

   @View
   @Route("/result/{result}")
   public void index(String result)
   {
      Map<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("result", result);
      index.render(parameters);
   }

   @Action
   @Route("/calculate")
   public Response.View calculate(String number1, String number2, String operator)
   {
      String result = null;
      try
      {
         double a = Double.parseDouble(number1);
         double b = Double.parseDouble(number2);
         result = calculatorService.getResult(a, b, operator.charAt(0));
      }
      catch (Exception e)
      {
         result = "Something wrong. Please check your inputs";
      }
      return Calculator_.index(result);
   }
}
