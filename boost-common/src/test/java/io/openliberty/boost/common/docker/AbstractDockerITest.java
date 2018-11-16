package io.openliberty.boost.common.docker;

import static org.junit.Assert.*;

import org.junit.Test;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.auth.RegistryAuthSupplier;

import io.openliberty.boost.common.BoostException;

public class AbstractDockerITest {
	private final int TAG_LENGTH_BOUNDRY = 128;
	
	private class TestAbstractDockerI implements  AbstractDockerI {
		@Override
		public void execute(DockerClient dockerClient) throws BoostException {}
		@Override
		public RegistryAuthSupplier createRegistryAuthSupplier() throws BoostException { return null; }
	}
	
	private AbstractDockerI d = new TestAbstractDockerI();

	@Test
	public void getImageName_simple_names() {
		assertEquals("repo:tag", d.getImageName("repo", "tag"));
	}

	@Test
	public void isTagValid_null_is_not_valid() {
		assertFalse(d.isTagValid(null));
	}

	@Test
	public void isTagValid_empty_string_is_not_valid() {
		assertFalse(d.isTagValid(""));
	}

	@Test
	public void isTagValid_simple_alpha_is_valid() {
		assertTrue(d.isTagValid("tag"));
	}

	@Test
	public void isTagValid_single_digit_is_valid() {
		assertTrue(d.isTagValid("1"));
	}
	
	private String getStringByLength(int len) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < len; i++) {
			sb.append('a');
		}
		return sb.toString();
	}
	
	@Test
	public void isTagValid_mid_length_is_valid() {
		assertTrue(d.isTagValid(getStringByLength(50)));
	}

	@Test
	public void isTagValid_boundry_condition_n_minus_1_is_valid() {
		assertTrue("Boundry condition for tag length is " + TAG_LENGTH_BOUNDRY + ", @ n-1 should be valid",
				d.isTagValid(getStringByLength(TAG_LENGTH_BOUNDRY-1)));
	}

	@Test
	public void isTagValid_boundry_condition_n_is_valid() {
		assertTrue("Boundry condition for tag length is " + TAG_LENGTH_BOUNDRY + ", @ n should be valid",
				d.isTagValid(getStringByLength(TAG_LENGTH_BOUNDRY)));
	}

	@Test
	public void isTagValid_boundry_condition_n_plus_1_is_not_valid() {
		assertFalse("Boundry condition for tag length is " + TAG_LENGTH_BOUNDRY + ", @ n+1 should not be valid",
				d.isTagValid(getStringByLength(TAG_LENGTH_BOUNDRY+1)));
	}

}
