/*
 * This file is part of Ozone.
 *
 * Ozone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Ozone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Ozone.  If not, see <https://www.gnu.org/licenses/>.
 */

package ozonelang.ozone.core.ast.node.expr;

/**
 * Represents the precedence of operators.
 * Used by the upcoming AST visitor to evaluate
 * expressions in the correct order.
 */
public interface Precedence {
    /**
     * Parentheses.
     */
    int HIGHEST = 15;
    /**
     * Function calls, array element access
     */
    int EXTREMELY_HIGH = 14;
    /**
     * Unary operations.
     */
    int SUPER_HIGH = 13;
    /**
     * Object creation.
     */
    int VERY_HIGH = 12;
    /**
     * Multiplicative operators: *, % and /
     */
    int QUITE_HIGH = 11;
    /**
     * Plus, minus and string concat.
     */
    int PRETTY_HIGH = 10;
    /**
     * Shift operators "<<", ">>" and ">>>"
     */
    int LITTLE_HIGH = 9;
    /**
     * Relativity
     */
    int MEDIUM = 8;
    /**
     * Equality
     */
    int LOW = 7;
    /**
     * Bitwise AND
     */
    int LITTLE_LOW = 6;
    /**
     * Bitwise XOR
     */
    int PRETTY_LOW = 5;
    /**
     * Bitwise OR
     */
    int QUITE_LOW = 4;
    /**
     * Logical AND
     */
    int VERY_LOW = 3;
    /**
     * Logical OR
     */
    int SUPER_LOW = 2;
    /**
     * The if expression/statement {@code <thing> if <condition> else <thing> }
     */
    int EXTREMELY_LOW = 1;
    /**
     * Assignment:
     *  =   +=   -=
     * *=   /=   %=
     * &=   ^=   |=
     * <<=  >>= >>>=
     *
     * Literals.
     */
    int LOWEST = 0;
}
