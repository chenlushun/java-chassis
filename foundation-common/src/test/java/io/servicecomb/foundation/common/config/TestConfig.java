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

package io.servicecomb.foundation.common.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import io.servicecomb.foundation.common.config.impl.IdXmlLoader;
import io.servicecomb.foundation.common.config.impl.PaaSPropertiesLoaderUtils;
import io.servicecomb.foundation.common.config.impl.XmlLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import io.servicecomb.foundation.common.utils.BeanUtils;
import io.servicecomb.foundation.common.utils.Log4jUtils;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author   
 * @version  [版本号, 2016年11月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TestConfig {

    private static final int TEST_PROP_LIST_SIZE = 2;

    private static ApplicationContext context;

    @Before
    public void init() throws Exception {
        Log4jUtils.init();
        BeanUtils.init();

    }

    /**
     * 根据id读取配置
     * @throws Exception Exception
     */
    @Test
    public void loadMergedProperties() throws Exception {
        Properties prop = ConfigMgr.INSTANCE.getConfig("pTest");
        Assert.assertEquals("0", prop.get("1"));
        Assert.assertEquals("1", prop.get("1.1"));
        Assert.assertEquals("2", prop.get("1.2"));
    }

    /**
     * spring的placeholder，根据id注入配置
     */
    @Test
    public void testBean() {
        BeanProp bp = (BeanProp) BeanUtils.getBean("beanProp");
        Assert.assertEquals("2", bp.getTest());
    }

    /**
     * Test Get/Set Context
     */
    @Test
    public void testBeanContext() {
        BeanUtils.setContext(context);
        Assert.assertEquals(context, BeanUtils.getContext());
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @throws Exception Exception
     */
    @Test
    public void testConvertProperties() throws Exception {
        BeanProp bp = PaaSResourceUtils.loadConfigAs("pTest", BeanProp.class);
        Assert.assertEquals("2", bp.getTest());
    }

    /**
     * <一句话功能简述>
     * <功能详细描述> 
     * @throws Exception Exception
     */
    @Test
    public void testConvertXml() throws Exception {
        Object ret = PaaSResourceUtils.loadConfigAs("xTest", BeanProp.class);
        BeanProp bp = (BeanProp) ret;
        Assert.assertEquals("test value", bp.getTest());
    }

    /**
     * 使用默认方式对xml做增量加载
     * @throws Exception Exception
     */
    @Test
    public void testXml() throws Exception {
        List<String> locationPatternList = new ArrayList<>();
        locationPatternList.add("classpath*:config/config.test.inc.xml");
        ConfigLoader loader = new XmlLoader(locationPatternList, ".inc.xml");
        Document doc = loader.load();

        Element root = doc.getDocumentElement();
        Assert.assertEquals("configs", root.getNodeName());

        {
            NodeList propList = root.getElementsByTagName("properties");
            Assert.assertEquals(2, propList.getLength());

            {
                Element prop = (Element) propList.item(0);
                Assert.assertEquals("pTest", prop.getAttributes().getNamedItem("id").getNodeValue());

                NodeList pathList = prop.getElementsByTagName("path");
                Assert.assertEquals(1, pathList.getLength());
                Assert.assertEquals("classpath*:config/test.properties", pathList.item(0).getTextContent());
            }

            {
                Element prop = (Element) propList.item(1);
                Assert.assertEquals("pTest", prop.getAttributes().getNamedItem("id").getNodeValue());

                NodeList pathList = prop.getElementsByTagName("path");
                Assert.assertEquals(1, pathList.getLength());
                Assert.assertEquals("classpath*:config/test.ext.properties",
                        pathList.item(0).getTextContent());
            }
        }

        {
            NodeList xmlList = root.getElementsByTagName("xml");
            Assert.assertEquals(2, xmlList.getLength());

            {
                Element xml = (Element) xmlList.item(0);
                Assert.assertEquals("xTest", xml.getAttributes().getNamedItem("id").getNodeValue());

                NodeList pathList = xml.getElementsByTagName("path");
                Assert.assertEquals(1, pathList.getLength());
                Assert.assertEquals("classpath*:config/test.xml", pathList.item(0).getTextContent());
            }

            {
                Element xml = (Element) xmlList.item(1);
                Assert.assertEquals("xTest", xml.getAttributes().getNamedItem("id").getNodeValue());

                NodeList pathList = xml.getElementsByTagName("path");
                Assert.assertEquals(1, pathList.getLength());
                Assert.assertEquals("classpath*:config/test.ext.xml", pathList.item(0).getTextContent());
            }
        }
    }

    /**
     * 使用根据id合并的方式增量加载
     * @throws Exception Exception
     */
    @Test
    public void testIdXml() throws Exception {
        List<String> locationPatternList = new ArrayList<>();
        locationPatternList.add("classpath*:config/config.test.inc.xml");
        ConfigLoader loader = new IdXmlLoader(locationPatternList, ".inc.xml");
        Document doc = loader.load();

        Element root = doc.getDocumentElement();
        Assert.assertEquals("configs", root.getNodeName());

        NodeList propList = root.getElementsByTagName("properties");
        Assert.assertEquals(1, propList.getLength());

        Element prop = (Element) propList.item(0);
        Assert.assertEquals("pTest", prop.getAttributes().getNamedItem("id").getNodeValue());

        NodeList pathListProp = prop.getElementsByTagName("path");
        Assert.assertEquals(TEST_PROP_LIST_SIZE, pathListProp.getLength());
        Assert.assertEquals("classpath*:config/test.properties", pathListProp.item(0).getTextContent());
        Assert.assertEquals("classpath*:config/test.ext.properties", pathListProp.item(1).getTextContent());

        NodeList xmlList = root.getElementsByTagName("xml");
        Assert.assertEquals(1, xmlList.getLength());

        Element xml = (Element) xmlList.item(0);
        Assert.assertEquals("xTest", xml.getAttributes().getNamedItem("id").getNodeValue());

        NodeList pathListXml = xml.getElementsByTagName("path");
        Assert.assertEquals(TEST_PROP_LIST_SIZE, pathListXml.getLength());
        Assert.assertEquals("classpath*:config/test.xml", pathListXml.item(0).getTextContent());
        Assert.assertEquals("classpath*:config/test.ext.xml", pathListXml.item(1).getTextContent());
    }

    /**
     * Test PaaSResourceUtils
     * @throws Exception 
     */
    @Test
    public void testPaaSResourceUtils() throws Exception {
        List<Resource> oList = PaaSResourceUtils.getSortedXmls("test.xml");
        Assert.assertNotEquals(null, oList);
        Assert.assertNotEquals(null, PaaSResourceUtils.loadMergedProperties("config/test.properties"));
        PaaSResourceUtils.sortProperties(oList);
        PaaSResourceUtils.sortXmls(oList);
        Assert.assertNotEquals(null, PaaSPropertiesLoaderUtils.loadMergedProperties("config/test.properties"));
        try {
            PaaSPropertiesLoaderUtils.fillMergedProperties(new Properties(), "");
        } catch (Exception e) {
            Assert.assertEquals(true, (e.getMessage()).contains("Resource path must not be null or empty"));
        }
        try {
            PaaSPropertiesLoaderUtils.fillMergedProperties(new Properties(), "tes.kunle");
        } catch (Exception e) {
            Assert.assertEquals(true, (e.getMessage()).contains("Resource path must ends with"));
        }
    }
}
