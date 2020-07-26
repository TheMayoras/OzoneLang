package ozonelang.ozone.core;

import ozonelang.ozone.core.lexer.Lexer;
import ozonelang.ozone.core.lexer.TokenStream;

import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        TokenStream ts;
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.print(">>> ");
            ts = TokenStream.open(new Lexer(new StringReader(s.nextLine()), "<stdin>"));
            while (ts.hasNext()) {
                System.out.println(ts.seek());
                ts.advance();
            }
        }
    }
}
