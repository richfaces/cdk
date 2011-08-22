/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.richfaces.cdk.templatecompiler;

import static org.richfaces.cdk.util.ComparatorUtils.nullSafeCompare;

import java.util.Comparator;

import javax.xml.namespace.QName;

/**
 * @author Nick Belaevski
 *
 */
public final class QNameComparator implements Comparator<QName> {
    public static final Comparator<QName> QNAME_COMPARATOR = new QNameComparator();

    private QNameComparator() {
        // private constructor
    }

    @Override
    public int compare(QName o1, QName o2) {
        int result;

        result = nullSafeCompare(o1.getNamespaceURI(), o2.getNamespaceURI());

        if (result == 0) {
            result = nullSafeCompare(o1.getLocalPart(), o2.getLocalPart());
        }

        return result;
    }
}
