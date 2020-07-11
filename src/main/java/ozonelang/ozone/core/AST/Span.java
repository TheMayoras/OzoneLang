package ozonelang.ozone.core.AST;

import ozonelang.ozone.core.AST.Location;

public interface Span {
    Location start();
    Location end();
}
