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

package io.servicecomb.serviceregistry.api.response;

import io.servicecomb.serviceregistry.api.registry.MicroserviceInstance;
import io.servicecomb.serviceregistry.api.registry.WatchAction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.servicecomb.serviceregistry.api.MicroserviceKey;

import org.junit.Assert;

/**
 * @author  
 * @since Mar 14, 2017
 * @see 
 */
public class TestMicroserviceInstanceChangedEvent {

    MicroserviceInstanceChangedEvent oMicroserviceInstanceChangedEvent = null;

    MicroserviceKey oMockMicroserviceKey = null;

    MicroserviceInstance oMockMicroserviceInstance = null;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        oMicroserviceInstanceChangedEvent = new MicroserviceInstanceChangedEvent();
        oMockMicroserviceKey = Mockito.mock(MicroserviceKey.class);
        oMockMicroserviceInstance = Mockito.mock(MicroserviceInstance.class);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        oMicroserviceInstanceChangedEvent = null;
        oMockMicroserviceKey = null;
        oMockMicroserviceInstance = null;
    }

    /**
     * Test Un-Initialized Values
     */
    @Test
    public void testDefaultValues() {
        Assert.assertNull(oMicroserviceInstanceChangedEvent.getAction());
        Assert.assertNull(oMicroserviceInstanceChangedEvent.getInstance());
        Assert.assertNull(oMicroserviceInstanceChangedEvent.getKey());
    }

    /**
     * Test InitializedValues
     */
    @Test
    public void testIntializedValues() {
        initFields(); //Initialize the Object
        Assert.assertEquals(WatchAction.CREATE, oMicroserviceInstanceChangedEvent.getAction());
        Assert.assertEquals("CREATE", oMicroserviceInstanceChangedEvent.getAction().getName());
        Assert.assertEquals(oMockMicroserviceInstance, oMicroserviceInstanceChangedEvent.getInstance());
        Assert.assertEquals(oMockMicroserviceKey, oMicroserviceInstanceChangedEvent.getKey());

        //Test Different Actions
        oMicroserviceInstanceChangedEvent.setAction(WatchAction.UPDATE);
        Assert.assertEquals("UPDATE", oMicroserviceInstanceChangedEvent.getAction().getName());

        oMicroserviceInstanceChangedEvent.setAction(WatchAction.DELETE);
        Assert.assertEquals("DELETE", oMicroserviceInstanceChangedEvent.getAction().getName());
    }

    /**
     * Initialize the Values
     */
    private void initFields() {
        oMicroserviceInstanceChangedEvent.setInstance(oMockMicroserviceInstance);
        oMicroserviceInstanceChangedEvent.setKey(oMockMicroserviceKey);
        oMicroserviceInstanceChangedEvent.setAction(WatchAction.CREATE);
    }

}
