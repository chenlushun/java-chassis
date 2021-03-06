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
package io.servicecomb.swagger.invocation.springmvc.response;

import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.core.Response.StatusType;

import io.servicecomb.swagger.invocation.response.Headers;
import io.servicecomb.swagger.invocation.response.producer.ProducerResponseMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.servicecomb.core.Response;
import io.servicecomb.core.context.HttpStatus;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * @author
 * @version  [版本号, 2017年4月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component
public class SpringmvcProducerResponseMapper implements ProducerResponseMapper {
    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getResponseClass() {
        return ResponseEntity.class;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Response mapResponse(StatusType status, Object response) {
        ResponseEntity<Object> springmvcResponse = (ResponseEntity<Object>) response;

        StatusType responseStatus = new HttpStatus(springmvcResponse.getStatusCode().value(),
                springmvcResponse.getStatusCode().getReasonPhrase());
        Response cseResponse = Response.status(responseStatus).entity(springmvcResponse.getBody());
        HttpHeaders headers = springmvcResponse.getHeaders();

        Headers cseHeaders = cseResponse.getHeaders();
        for (Entry<String, List<String>> entry : headers.entrySet()) {
            if (entry.getValue() == null || entry.getValue().isEmpty()) {
                continue;
            }

            for (String value : entry.getValue()) {
                cseHeaders.addHeader(entry.getKey(), value);
            }
        }
        return cseResponse;
    }
}
