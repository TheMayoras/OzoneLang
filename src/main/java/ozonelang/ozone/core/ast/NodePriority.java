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

package ozonelang.ozone.core.ast;

/**
 * Indicates the priority of evaluating an {@link ozonelang.ozone.core.ast.node.ExpressionNode}.
 */
public enum NodePriority {
    /** POW symbol ( ^ ) */
    HIGHEST,
    /** Parentheses */
    HIGH,
    /** Division and multiplication */
    NORMAL,
    /** Addition and subtraction,
     * also includes "..", "..." and "&" for strings.
     */
    LOW,
}
