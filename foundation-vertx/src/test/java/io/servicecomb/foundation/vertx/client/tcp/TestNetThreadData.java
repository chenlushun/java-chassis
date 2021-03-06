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

/**
 * 
 */
package io.servicecomb.foundation.vertx.client.tcp;

import java.util.concurrent.atomic.AtomicInteger;

import io.servicecomb.foundation.vertx.client.ClientPoolFactory;
import io.servicecomb.foundation.vertx.client.NetThreadData;
import io.servicecomb.foundation.vertx.client.http.HttpClientWithContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author 
 *
 */
public class TestNetThreadData {

    private NetThreadData<HttpClientWithContext> instance;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        @SuppressWarnings("unchecked")
        ClientPoolFactory<HttpClientWithContext> factory = Mockito.mock(ClientPoolFactory.class);
        instance = new NetThreadData<>(factory, 1);
        Assert.assertNotNull(instance);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * Test method for {@link NetThreadData#getFactory()}.
     */
    @Test
    public void testGetFactory() {
        ClientPoolFactory<HttpClientWithContext> factory = instance.getFactory();
        Assert.assertNotNull(factory);
    }

    /**
     * Test method for {@link NetThreadData#getPools()}.
     */
    @Test
    public void testGetPools() {
        instance.getPools();
        Assert.assertNotNull(instance.getPools());
    }

    /**
     * Test method for {@link NetThreadData#getBindIndex()}.
     */
    @Test
    public void testGetBindIndex() {
        AtomicInteger count = instance.getBindIndex();
        Assert.assertNotNull(count);
    }

    /**
     * Test method for {@link NetThreadData#selectClientPool()}.
     */
    @Test
    public void testSelectClientPool() {
        Assert.assertNull(instance.selectClientPool());
    }
}
