package net.orekyuu.sample;

import java.util.ServiceLoader;

public class FizzBuzzGenerator {
  public static void main(String[] args) {
    var displays = ServiceLoader.load(Display.class);
    for (int i = 1; i < 100; i++) {
      String result;
      if ((i % 15) == 0) {
        result = "FizzBuzz";
      } else if ((i % 3) == 0) {
        result = "Fizz";
      } else if ((i % 5) == 0) {
        result = "Buzz";
      } else {
        result = String.valueOf(i);
      }
      for (Display display : displays) {
        display.print(result);
      }
    }
  }
}
