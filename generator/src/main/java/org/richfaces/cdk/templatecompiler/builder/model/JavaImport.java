/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */
package org.richfaces.cdk.templatecompiler.builder.model;

import java.util.Comparator;

/**
 * @author Maksim Kaszynski
 */
public interface JavaImport {
    Comparator<? super JavaImport> COMPARATOR = new Comparator<JavaImport>() {
        public int compare(JavaImport o1, JavaImport o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };

    String getName();

    boolean isStatic();

    boolean isDefault();
}
