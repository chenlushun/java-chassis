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

package io.servicecomb.swagger.generator.core.processor.annotation;

import io.servicecomb.swagger.generator.core.OperationGenerator;
import org.springframework.util.StringUtils;

import io.servicecomb.swagger.generator.core.MethodAnnotationProcessor;

import io.swagger.annotations.ApiOperation;
import io.swagger.models.Operation;
import io.swagger.models.Scheme;
import io.swagger.util.BaseReaderUtils;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * @author
 * @version  [版本号, 2017年3月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ApiOperationProcessor implements MethodAnnotationProcessor {
    /**
     * {@inheritDoc}
     */
    @Override
    public void process(Object annotation, OperationGenerator operationGenerator) {
        ApiOperation apiOperationAnnotation = (ApiOperation) annotation;
        Operation operation = operationGenerator.getOperation();

        operationGenerator.setHttpMethod(apiOperationAnnotation.httpMethod());

        if (!StringUtils.isEmpty(apiOperationAnnotation.value())) {
            operation.setSummary(apiOperationAnnotation.value());
        }

        if (!StringUtils.isEmpty(apiOperationAnnotation.notes())) {
            operation.setDescription(apiOperationAnnotation.notes());
        }

        operation.setOperationId(apiOperationAnnotation.nickname());
        operation.getVendorExtensions().putAll(BaseReaderUtils.parseExtensions(apiOperationAnnotation.extensions()));

        convertTags(apiOperationAnnotation.tags(), operation);
        convertProduces(apiOperationAnnotation.produces(), operation);
        convertConsumes(apiOperationAnnotation.consumes(), operation);
        convertProtocols(apiOperationAnnotation.protocols(), operation);
        AnnotationUtils.addResponse(operationGenerator.getSwagger(),
                operation,
                apiOperationAnnotation);

        // responseReference未解析
        // hidden未解析
        // authorizations未解析
    }

    // protocols以分号为创建，比如：http, https, ws, wss
    private void convertProtocols(String protocols, Operation operation) {
        if (protocols == null) {
            return;
        }

        for (String protocol : protocols.split(",")) {
            if (StringUtils.isEmpty(protocol)) {
                continue;
            }

            operation.addScheme(Scheme.forValue(protocol));
        }
    }

    // consumes以分号为创建，比如：application/json, application/xml
    private void convertConsumes(String consumes, Operation operation) {
        if (StringUtils.isEmpty(consumes)) {
            return;
        }

        for (String consume : consumes.split(",")) {
            if (StringUtils.isEmpty(consume)) {
                continue;
            }

            operation.addConsumes(consume);
        }
    }

    // produces以分号为创建，比如：application/json, application/xml
    private void convertProduces(String produces, Operation operation) {
        if (StringUtils.isEmpty(produces)) {
            return;
        }

        for (String produce : produces.split(",")) {
            if (StringUtils.isEmpty(produce)) {
                continue;
            }

            operation.addProduces(produce);
        }
    }

    private void convertTags(String[] tags, Operation operation) {
        if (tags == null || tags.length == 0) {
            return;
        }

        for (String tag : tags) {
            if (StringUtils.isEmpty(tag)) {
                continue;
            }

            operation.addTag(tag);
        }
    }
}
