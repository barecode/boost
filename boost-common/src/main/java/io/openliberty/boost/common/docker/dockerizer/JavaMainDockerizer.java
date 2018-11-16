package io.openliberty.boost.common.docker.dockerizer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.openliberty.boost.common.BoostException;
import io.openliberty.boost.common.BoostLoggerI;

public class JavaMainDockerizer extends Dockerizer {

    public JavaMainDockerizer(File projectDirectory, File outputDirectory, File appArchive, BoostLoggerI log) {
        super(projectDirectory, outputDirectory, appArchive, log);
    }

    @Override
    public Map<String, String> getBuildArgs() {
        Map<String, String> buildArgs = new HashMap<String, String>();
        buildArgs.put("JAR_FILE", getAppPathString() + "/" + appArchive.getName());
        return buildArgs;
    }

    @Override
    public List<String> getDockerfileLines() throws BoostException {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(BOOST_GEN);
        lines.add("FROM adoptopenjdk/openjdk8-openj9");
        lines.add("ARG JAR_FILE");
        lines.add("COPY ${JAR_FILE} app.jar");
        lines.add("ENTRYPOINT [\"java\",\"-jar\",\"/app.jar\"]");
        return lines;
    }

    @Override
    public List<String> getDockerIgnoreList() {
        List<String> lines = new ArrayList<String>();
        lines.add("*.log");
        lines.add(".gradle/");
        return lines;
    }

}
