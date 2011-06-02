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
package org.richfaces.cdk.xmlconfig.testmodel;

import java.io.StringWriter;

import org.junit.Test;
import org.richfaces.cdk.model.ClassName;
import org.richfaces.cdk.model.ComponentLibrary;
import org.richfaces.cdk.model.ConverterModel;
import org.richfaces.cdk.model.FacesId;
import org.richfaces.cdk.xmlconfig.XmlTest;

/**
 * @author akolonitsky
 * @since Jan 10, 2010
 */
public class ConverterBeanTest extends XmlTest {
    @Test
    public void testMarshal() throws Exception {
        ComponentLibrary library = new ComponentLibrary();

        ConverterModel converterModel = new ConverterModel(FacesId.parseId("my_converter"));
        converterModel.setTargetClass(ClassName.parseName(Object.class.getName()));
        library.getConverters().add(converterModel);

        // Jaxb marshaling
        StringWriter facesConfig = generateFacesConfig(library);

        // Checks
        checkXmlStructure(facesConfig);
        validateXml(facesConfig);
    }
}
