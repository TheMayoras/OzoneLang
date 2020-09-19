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

package ozonelang.ozone.core.runtime.exception;

public interface ErrorConstants {
    String ARITHMETHIC_E        = "ArithmeticError";
    String CLASSLOADER_E        = "ClassLoaderError";
    String VALUE_EXPECTED_E     = "ValueExpectedError";
    String TYPE_MISMATCH_E      = "TypeMismatchError";
    String NAME_NOT_FOUND_E     = "UnresolvedSymbolError";
    String CONST_ASSIGN_E       = "ConstantAssignmentError";
    String ALREADY_DEFINED_E    = "DuplicateValueError";
}
