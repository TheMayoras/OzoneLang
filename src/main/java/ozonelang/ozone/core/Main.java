package ozonelang.ozone.core;


import org.kaivos.nept.parser.TokenScanner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        while (true) {
            new Parser().parse(new Scanner(System.in).nextLine(), "<stdin>");
        }
    }
}
