/*
 * Copyright 2017 Huawei Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.servicecomb.core.handler.impl;

import io.servicecomb.core.AsyncResponse;
import io.servicecomb.core.Invocation;
import io.servicecomb.core.definition.OperationMeta;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TestServiceProviderHandler {

    ProducerOperationHandler serviceProviderHandler = null;

    Invocation invocation = null;

    AsyncResponse asyncResp = null;

    io.servicecomb.core.definition.OperationMeta OperationMeta = null;

    @Before
    public void setUp() throws Exception {
        serviceProviderHandler = new ProducerOperationHandler();
        invocation = Mockito.mock(Invocation.class);
        asyncResp = Mockito.mock(AsyncResponse.class);
        OperationMeta = Mockito.mock(OperationMeta.class);

    }

    @After
    public void tearDown() throws Exception {
        serviceProviderHandler = null;
        invocation = null;
        asyncResp = null;
        OperationMeta = null;
    }

    @Test
    public void testHandle() {
        boolean status = false;
        try {
            Assert.assertNotNull(serviceProviderHandler);
            Mockito.when(invocation.getOperationMeta()).thenReturn(OperationMeta);
            serviceProviderHandler.handle(invocation, asyncResp);
        } catch (Exception e) {
            status = true;
        }
        Assert.assertFalse(status);
    }
}
