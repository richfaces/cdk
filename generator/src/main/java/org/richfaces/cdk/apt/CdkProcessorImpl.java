/*
 * $Id$
 *
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
package org.richfaces.cdk.apt;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import org.richfaces.cdk.Logger;

import com.google.inject.Inject;

/**
 * <p class="changed_added_4_0">
 * Base class for all CDK Annotation processors. That class provides access to current CDK context and utility methods for Java
 * source models.
 * </p>
 *
 * @author asmirnov@exadel.com
 *
 */
public class CdkProcessorImpl extends AbstractProcessor implements CdkProcessor {
    private static final Set<String> PROCESSED_ANNOTATION = Collections.singleton("*");

    @Inject
    private SourceUtilsProvider sourceUtilsProducer;

    @Inject
    private LibraryCompiler compiler;

    @Inject
    private LibraryGenerator generator;

    @Inject
    private Logger log;

    private boolean firstRound = true;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        sourceUtilsProducer.setProcessingEnv(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            if (firstRound) {
                firstRound = false;
                compiler.beforeJavaSourceProcessing();
            }
            compiler.processJavaSource(processingEnv, roundEnv);
        } else {
            compiler.afterJavaSourceProcessing();
            continueAfterJavaSourceProcessing();
        }
        return false;
    }

    @Override
    public void continueAfterJavaSourceProcessing() {

        compiler.processNonJavaSources();
        compiler.verify();
        if (0 == log.getErrorCount()) {
            generator.generate();
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return PROCESSED_ANNOTATION;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {

        // CDK supports Java 5 or 6 source code.
        return SourceVersion.RELEASE_6;
    }
}
