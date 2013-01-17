package juzu.example;

public class CalculatorService
{
   public String getResult(double a, double b, Character operator)
   {
      double result = 0;
      try
      {
         switch (operator)
         {
            case '+' :
               result = a + b;
               break;

            case '-' :
               result = a + b;
               break;

            case '/' :
               result = a / b;
               break;

            case '*' :
               result = a * b;
               break;

            default :
               throw new Exception();
         }
      }
      catch (Exception e)
      {
         return "Something wrong with your calculation. Such as divide to 0. Please re-check your calculation";
      }

      return String.valueOf(result);
   }
}
