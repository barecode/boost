package io.openliberty.boost.common.docker;

import java.io.File;

import io.openliberty.boost.common.BoostLoggerI;

public class DockerizerConfig {
    private final File projectDirectory;
    private final File outputDirectory;
    private final File appArchive;
    private final BoostLoggerI log;

    private String springBootVersion;

    public DockerizerConfig(File projectDirectory, File outputDirectory, File appArchive, BoostLoggerI log) {
        this.projectDirectory = projectDirectory;
        this.outputDirectory = outputDirectory;
        this.appArchive = appArchive;
        this.log = log;
    }

    public File getProjectDirectory() {
        return projectDirectory;
    }

    public File getOutputDirectory() {
        return outputDirectory;
    }

    public File getAppArchive() {
        return appArchive;
    }

    public BoostLoggerI getBoostLogger() {
        return log;
    }

    public boolean isSpringBootProject() {
        return springBootVersion != null;
    }

    public String getSpringBootVersion() {
        return springBootVersion;
    }

    public void setSpringBootVersion(String springBootVersion) {
        this.springBootVersion = springBootVersion;
    }
}
