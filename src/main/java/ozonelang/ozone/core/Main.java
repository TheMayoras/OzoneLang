package ozonelang.ozone.core;

import ozonelang.ozone.core.parser.Parser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        String line = "";
        while (!line.equals("exit")) {
            line = s.nextLine();
            new Parser().parse(line, "<stdin>");
        }
    }
}
