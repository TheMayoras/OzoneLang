package main.java.ozonelang.ozone.core.AST;

import main.java.ozonelang.ozone.core.AST.Location;

public interface Span {
    Location start();
    Location end();
}
