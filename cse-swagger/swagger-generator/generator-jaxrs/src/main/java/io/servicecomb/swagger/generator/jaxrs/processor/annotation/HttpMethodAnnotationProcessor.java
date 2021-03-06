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

package io.servicecomb.swagger.generator.jaxrs.processor.annotation;

import java.lang.annotation.Annotation;

import javax.ws.rs.HttpMethod;

import io.servicecomb.swagger.generator.core.MethodAnnotationProcessor;
import io.servicecomb.swagger.generator.core.OperationGenerator;

public class HttpMethodAnnotationProcessor implements MethodAnnotationProcessor {
    /**
     * {@inheritDoc}
     */
    @Override
    public void process(Object annotation, OperationGenerator operationGenerator) {
        Annotation httpMethodAnnotation = (Annotation) annotation;
        HttpMethod httpMethod = httpMethodAnnotation.annotationType().getAnnotation(HttpMethod.class);

        operationGenerator.setHttpMethod(httpMethod.value());
    }
}
