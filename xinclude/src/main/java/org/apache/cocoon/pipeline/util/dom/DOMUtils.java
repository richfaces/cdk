/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.cocoon.pipeline.util.dom;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.cocoon.pipeline.component.sax.SAXConsumer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public final class DOMUtils {
    private static final DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();

    static {
        DBF.setNamespaceAware(true);
        DBF.setXIncludeAware(false);
    }

    private DOMUtils() {

        // instances are not allowed
    }

    public static Document toDOM(InputSource source, EntityResolver resolver) throws SAXException, IOException {
        DocumentBuilder documentBuilder;

        try {

            // TODO - configuration
            documentBuilder = DBF.newDocumentBuilder();
            documentBuilder.setEntityResolver(resolver);

            return documentBuilder.parse(source);
        } catch (ParserConfigurationException e) {
            throw new SAXException("Error during XPointer evaluation while trying to load " + source, e);
        }
    }

    public static void stream(Node node, SAXConsumer xmlConsumer) throws SAXException {
        new DOMStreamer(xmlConsumer).stream(node);
    }
}
