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

import java.util.ArrayList;
import java.util.List;

import io.servicecomb.serviceregistry.api.registry.MicroserviceInstance;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author  
 * @since Mar 14, 2017
 * @see 
 */
public class TestFindInstancesResponse {

    FindInstancesResponse oFindInstancesResponse = null;

    List<MicroserviceInstance> oListMicroserviceInstance = null;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        oFindInstancesResponse = new FindInstancesResponse();
        oListMicroserviceInstance = new ArrayList<>();
        oListMicroserviceInstance.add(Mockito.mock(MicroserviceInstance.class));
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        oFindInstancesResponse = null;
        oListMicroserviceInstance = null;
    }

    /**
     * Test Un-Initialized Values
     */
    @Test
    public void testDefaultValues() {
        Assert.assertNull(oFindInstancesResponse.getInstances());
    }

    /**
     * Test InitializedValues
     */
    @Test
    public void testIntializedValues() {
        initFields(); //Initialize the Object
        Assert.assertEquals(1, oFindInstancesResponse.getInstances().size());
    }

    /**
     * Initialize the Values
     */
    private void initFields() {
        oFindInstancesResponse.setInstances(oListMicroserviceInstance);
    }

}
