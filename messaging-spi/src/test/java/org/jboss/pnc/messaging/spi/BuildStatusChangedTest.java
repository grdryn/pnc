/**
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.pnc.messaging.spi;

import org.assertj.core.api.Assertions;
import org.jboss.pnc.common.json.JsonOutputConverterMapper;
import org.jboss.pnc.model.SystemImageType;
import org.jboss.pnc.spi.BuildCoordinationStatus;
import org.jboss.pnc.spi.dto.Build;
import org.jboss.pnc.spi.dto.BuildConfigurationRevisionRef;
import org.jboss.pnc.spi.dto.BuildEnvironment;
import org.jboss.pnc.spi.dto.ProjectRef;
import org.jboss.pnc.spi.dto.RepositoryConfiguration;
import org.jboss.pnc.spi.dto.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;

/**
 * @author <a href="mailto:matejonnet@gmail.com">Matej Lazar</a>
 */
public class BuildStatusChangedTest {

    Logger logger = LoggerFactory.getLogger(BuildStatusChangedTest.class);

    @Test
    public void shouldSerializeObject() throws IOException {
        //given

        Build build = new Build(
                new ProjectRef(1, "A", "desc", "url1", "url2"),
                new RepositoryConfiguration(1, "url1", "url2", true),
                new BuildEnvironment(1, "jdk8", "desc", "url", "11", Collections.emptyMap(), SystemImageType.DOCKER_IMAGE, true),
                Collections.emptyMap(),
                new User(1, "user"),
                new BuildConfigurationRevisionRef(1, 1, "name", "desc", "true", "awqs21"),
                Collections.EMPTY_LIST,
                Collections.EMPTY_LIST,
                1,
                BuildCoordinationStatus.BUILDING,
                "build-42",
                true
        );

        String pncBuildId = "123";
        Status oldStatus = Status.ACCEPTED;
        Status newStatus = Status.BUILDING;
        BuildStatusChanged buildStatusChanged = new BuildStatusChanged(
                oldStatus.lowercase(),
                build);

        //when
        String serialized = buildStatusChanged.toJson();
        logger.info("Serialized: {}", serialized);

        BuildStatusChanged deserialized = JsonOutputConverterMapper.readValue(serialized, BuildStatusChanged.class);

        //then
        Assertions.assertThat(deserialized).isEqualToComparingFieldByField(buildStatusChanged);

    }
}
