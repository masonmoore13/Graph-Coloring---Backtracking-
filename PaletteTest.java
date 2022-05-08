/**
   Program to test the basic functionality of the PaintShop class
*/
public class PaletteTest
{
   public static void main(String [] args)
   {
      PaintShop p1 = new PaintShop("colors0.txt");
      System.out.println("SOLUTION? " + p1.getSolution());         // EXPECTED: [red, green, blue]
      System.out.println("HOW MANY? " + p1.howManySolutions());    // EXPECTED: 2
      
      p1 = new PaintShop("colors1.txt");
      System.out.println("SOLUTION? " + p1.getSolution());         // EXPECTED: [purple, red, pink, black]
      System.out.println("HOW MANY? " + p1.howManySolutions());    // EXPECTED: 8
   }
}