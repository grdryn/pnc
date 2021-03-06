/**
 * JBoss, Home of Professional Open Source.
 * Copyright 2014-2019 Red Hat, Inc., and individual contributors
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
package org.jboss.pnc.client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

/**
 * @author <a href="mailto:matejonnet@gmail.com">Matej Lazar</a>
 */
public class RemoteResourceException extends ClientException {

    private final int status;

    public RemoteResourceException(Throwable cause) {
        super(cause);
        status = -1;
    }

    public RemoteResourceException(ClientErrorException cause) {
        super(cause);
        Response response = cause.getResponse();
        status = response.getStatus();
    }

    public RemoteResourceException(String message, int status) {
        super(message + " status: " + status);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
