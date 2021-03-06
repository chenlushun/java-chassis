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

package io.servicecomb.common.rest.codec.produce;

import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.JavaType;
import io.servicecomb.common.rest.codec.RestObjectMapper;

/**
 * application/json produce类型的processor
 * @author   
 * @version  [版本号, 2017年1月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ProduceJsonProcessor extends AbstractProduceProcessor {

    @Override
    public String getName() {
        return MediaType.APPLICATION_JSON;
    }

    @Override
    public void encodeResponse(OutputStream output, Object result) throws Exception {
        RestObjectMapper.INSTANCE.writeValue(output, result);
    }

    @Override
    public Object decodeResponse(InputStream input, JavaType type) throws Exception {
        return RestObjectMapper.INSTANCE.readValue(input, type);
    }
}
