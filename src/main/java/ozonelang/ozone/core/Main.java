package ozonelang.ozone.core;

import ozonelang.ozone.core.AST.SymbolType;
import ozonelang.ozone.core.lexer.Lexer;
import ozonelang.ozone.core.parser.Parser;

import java.io.StringReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        String line = "";
        Lexer l;
        while (true) {
            l = new Lexer(new StringReader(s.nextLine()), "<stdin>", "EOF");
            System.out.println(l.yylex());
        }
    }
}
