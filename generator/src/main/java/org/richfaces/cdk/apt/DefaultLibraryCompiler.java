package org.richfaces.cdk.apt;

import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;

import org.richfaces.cdk.CdkException;
import org.richfaces.cdk.Logger;
import org.richfaces.cdk.ModelBuilder;
import org.richfaces.cdk.ModelValidator;
import org.richfaces.cdk.model.ComponentLibrary;

import com.google.inject.Inject;

public class DefaultLibraryCompiler implements LibraryCompiler {

    @Inject
    private Logger log;

    @Inject
    private JavaSourceProcessor javaSourceProcessor;

    @Inject
    private Set<ModelBuilder> builders;
    @Inject
    private ModelValidator validator;

    @Inject
    private ComponentLibrary library;

    /*
     * (non-Javadoc)
     *
     * @see org.richfaces.cdk.apt.LibraryWorker#beforeJavaSourceProcessing()
     */
    @Override
    public void beforeJavaSourceProcessing() {
    }

    /*
     * (non-Javadoc)
     *
     * @see org.richfaces.cdk.apt.LibraryWorker#processJavaSource(javax.annotation.processing.ProcessingEnvironment,
     * javax.annotation.processing.RoundEnvironment)
     */
    @Override
    public void processJavaSource(ProcessingEnvironment processingEnv, RoundEnvironment roundEnv) {
        javaSourceProcessor.process(processingEnv, roundEnv);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.richfaces.cdk.apt.LibraryWorker#afterJavaSourceProcessing()
     */
    @Override
    public void afterJavaSourceProcessing() {
    }

    /*
     * (non-Javadoc)
     *
     * @see org.richfaces.cdk.apt.LibraryWorker#processNonJavaSources()
     */
    @Override
    public void processNonJavaSources() throws CdkException {
        for (ModelBuilder builder : builders) {
            log.debug("Run builder " + builder.getClass().getName());
            try {
                builder.build();
            } catch (CdkException e) {
                // TODO: sendError(e);
                e.printStackTrace();
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.richfaces.cdk.apt.LibraryWorker#verify()
     */
    @Override
    public void verify() throws CdkException {
        try {
            log.debug("Validate model");
            validator.verify(library);
        } catch (CdkException e) {
            // TODO: sendError(e);
            e.printStackTrace();
        }
    }
}
