package com.github.cmunier.examples.classgraph.issue843;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.Resource;
import io.github.classgraph.ResourceList;
import io.github.classgraph.ScanResult;

@WebListener
public class StartupListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(StartupListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            test();
        } catch (Exception e) {
            throw new IllegalStateException("Test failed", e);
        }

        LOG.info("### Test successful ###");
    }

    protected void test() {
        try (ScanResult scanResult = new ClassGraph().scan();
                ResourceList txtResources = scanResult.getResourcesWithExtension("txt")) {

            Resource ejbModuleFile = txtResources.stream()
                .filter(resource -> resource.getPath().endsWith("file-in-ejb-jar.txt"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("File in EJB module not found"));

            if (!getContentAsString(ejbModuleFile).equals("works")) {
                throw new IllegalStateException("File in EJB module not read correctly");
            }
        }
    }
    
    protected String getContentAsString(Resource resource) {
        try {
            return resource.getContentAsString();
        } catch (IOException e) {
            throw new IllegalStateException("Could not get contents from resource: " + resource.getURI(), e);
        }
    }

}
