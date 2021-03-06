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

package io.servicecomb.common.rest.definition.path;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import io.servicecomb.common.rest.codec.RestObjectMapper;
import io.servicecomb.common.rest.definition.RestParam;

/**
 * @author   
 * @version  [版本号, 2017年1月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class QueryVarParamWriter extends AbstractUrlParamWriter {
    // ? or &
    private char prefix;

    /**
     * <构造函数>
     * @param paramName
     * @param argIndex [参数说明]
     */
    public QueryVarParamWriter(char prefix, RestParam param) {
        this.param = param;
        this.prefix = prefix;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(StringBuilder builder, Object[] args) throws Exception {
        builder.append(prefix);

        Object value = getParamValue(args);
        if (value == null) {
            // 连key都不写进去，才能表达null的概念
            return;
        }

        if (value.getClass().isArray()) {
            writeArray(builder, value);
            return;
        }

        if (Collection.class.isInstance(value)) {
            writeCollection(builder, value);
            return;
        }

        writeKeyEqual(builder);
        builder.append(encodeNotNullValue(value));
    }

    private void writeKeyEqual(StringBuilder builder) {
        builder.append(param.getParamName()).append('=');
    }

    @SuppressWarnings("unchecked")
    private void writeCollection(StringBuilder builder, Object value) throws Exception {
        for (Object item : (Collection<Object>) value) {
            writeItem(builder, item);
        }

        if (((Collection<Object>) value).size() != 0) {
            deleteLastChar(builder);
        }
    }

    private void writeArray(StringBuilder builder, Object value) throws Exception {
        for (Object item : (Object[]) value) {
            writeItem(builder, item);
        }

        if (((Object[]) value).length != 0) {
            deleteLastChar(builder);
        }
    }

    private void deleteLastChar(StringBuilder builder) {
        builder.setLength(builder.length() - 1);
    }

    private void writeItem(StringBuilder builder, Object item) throws Exception {
        writeKeyEqual(builder);

        // TODO:数组元素为null，当前找不到表达方式，通过issue跟踪，有解决方案后再来处理
        // http://code.huawei.com/CSE/cse-java-chassis/issues/133
        if (item != null) {
            builder.append(encodeNotNullValue(item));
        }

        builder.append('&');
    }

    private String encodeNotNullValue(Object value) throws Exception {
        String strValue = RestObjectMapper.INSTANCE.convertToString(value);
        return URLEncoder.encode(strValue, StandardCharsets.UTF_8.name());
    }
}
