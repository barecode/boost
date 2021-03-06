/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package application;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.command.PullImageResultCallback;

public class DockerPushIT {

    private static DockerClient dockerClient;
    private static String imageName;

    @BeforeClass
    public static void setup() throws Exception {
        dockerClient = DockerClientBuilder.getInstance().build();
        imageName = "localhost:5000/test-docker:latest";
    }

    @Test
    public void testPushDockerImageToLocalRegistry() throws Exception {
        Image pushedImage = getImage(imageName);
        assertNotNull(imageName + " was not built.", pushedImage);

        long sizeOfPushedImage = pushedImage.getSize();
        String idOfPushedImage = pushedImage.getId();

        // Remove the local image.
        removeImage(imageName);

        // Pull the image from the local repository which got pushed by the plugin. This
        // is possible if the plugin successfully pushed to the registry.
        dockerClient.pullImageCmd("localhost:5000/test-docker").withTag("latest").exec(new PullImageResultCallback())
                .awaitCompletion(10, TimeUnit.SECONDS);

        Image pulledImage = getImage(imageName);
        assertNotNull(imageName + " was not pulled.", pulledImage);

        long sizeOfPulledImage = pulledImage.getSize();
        String idOfPulledImage = pulledImage.getId();

        assertEquals("Expected image was not pulled, size doesn't match.", sizeOfPushedImage, sizeOfPulledImage);
        assertEquals("Expected image was not pulled, id doesn't match.", idOfPushedImage, idOfPulledImage);
    }

    private Image getImage(String imageName) throws Exception {
        List<Image> images = dockerClient.listImagesCmd().exec();
        for (Image image : images) {
            String[] repoTags = image.getRepoTags();
            if (repoTags != null) {
                String repoTag = repoTags[0];
                if (repoTag != null && repoTag.equals(imageName)) {
                    return image;
                }
            }
        }
        return null;
    }

    private void removeImage(String imageName) throws Exception {
        dockerClient.removeImageCmd(imageName).exec();
        Image removedImage = getImage(imageName);
        assertNull(imageName + " was not removed.", removedImage);
    }
}