package net.orekyuu.sample;

public class ConsoleDisplay implements Display {
    @Override
    public void print(String text) {
        System.out.println(text);
    }
}
