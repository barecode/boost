package io.openliberty.boost.common.docker;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.auth.RegistryAuthSupplier;

import io.openliberty.boost.common.BoostException;
import io.openliberty.boost.common.docker.dockerizer.JavaMainDockerizer;

public class DockerBuildITest {

	private class TestDockerBuildI implements  DockerBuildI {
		@Override public void execute(DockerClient dockerClient) throws BoostException {}
		@Override public RegistryAuthSupplier createRegistryAuthSupplier() throws BoostException { return null; }
		@Override public File getAppArchive() throws BoostException { return null; }
	}

	@Test
	public void testGetDockerizer() {
		DockerBuildI d = new TestDockerBuildI();
		DockerizerConfig config = new DockerizerConfig(null, null, null, null);
		DockerParameters params = new DockerParameters("");

//		assertTrue(d.getDockerizer("jar", config, params) instanceof JavaMainDockerizer);
	}

}
