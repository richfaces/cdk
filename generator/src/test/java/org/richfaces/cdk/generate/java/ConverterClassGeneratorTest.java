/*
 * JBoss, Home of Professional Open Source
 * Copyright , Red Hat, Inc. and individual contributors
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
package org.richfaces.cdk.generate.java;

import static org.easymock.EasyMock.anyInt;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.io.StringWriter;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.richfaces.cdk.CdkTestRunner;
import org.richfaces.cdk.generate.freemarker.PropertyModel;
import org.richfaces.cdk.model.ClassName;
import org.richfaces.cdk.model.ComponentLibrary;
import org.richfaces.cdk.model.ConverterModel;
import org.richfaces.cdk.model.EventName;
import org.richfaces.cdk.model.FacesId;

import com.google.inject.Inject;

/**
 * @author akolonitsky
 * @since Jan 20, 2010
 */
@RunWith(CdkTestRunner.class)
public class ConverterClassGeneratorTest extends AbstractClassGeneratorTest {
    @Inject
    private ConverterClassGenerator generator;

    @Test
    public void testGetOutputFileValidator() throws Exception {
        final StringWriter outputWriter = new StringWriter();
        expect(output.createOutput((String) anyObject(), anyInt())).andReturn(outputWriter);
        replay(output);

        ConverterModel converter = new ConverterModel();
        converter.setId(FacesId.parseId("foo.bar"));
        converter.setTargetClass(ClassName.parseName("org.richfaces.cdk.generate.java.GeneratedConverter"));
        converter.setBaseClass(ClassName.parseName("Object"));
        converter.setGenerate(true);
        addAttribute(converter, "myClass", PropertyModel.class, true);
        addAttribute(converter, "testObject", Object.class, true);
        addAttribute(converter, "testBoolean", Boolean.class, true);
        addAttribute(converter, "testInteger", Integer.class, true);
        addAttribute(converter, "visible", boolean.class, true);
        addAttribute(converter, "id", String.class, false);

        Set<EventName> eventNames = converter.getAttribute("id").getEventNames();
        eventNames.add(getEvent("id", false));
        eventNames.add(getEvent("action", true));

        ComponentLibrary library = new ComponentLibrary();
        library.getConverters().add(converter);
        generator.generate(library, converter);

        log.debug(outputWriter.toString());
        verify(output);
        // TODO - use source code parser to analyze generated class
        // compare(outputWriter, "GeneratedConverter.java");
    }
}
