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

package io.servicecomb.codec.protobuf.utils.schema;

import java.io.IOException;

import io.servicecomb.common.javassist.SingleWrapper;

import io.protostuff.Input;
import io.protostuff.Output;
import io.protostuff.Schema;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * @author   
 * @version  [版本号, 2016年12月16日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class NormalWrapSchema extends AbstractWrapSchema {
    /**
     * <构造函数>
     * @param schema [参数说明]
     */
    @SuppressWarnings("unchecked")
    public NormalWrapSchema(Schema<?> schema) {
        this.schema = (Schema<Object>) schema;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object readFromEmpty() {
        SingleWrapper wrapper = (SingleWrapper) schema.newMessage();
        return wrapper.readField();
    }

    public Object readObject(Input input) throws IOException {
        SingleWrapper wrapper = (SingleWrapper) schema.newMessage();
        schema.mergeFrom(input, wrapper);

        return wrapper.readField();
    }

    public void writeObject(Output output, Object value) throws IOException {
        if (value == null) {
            return;
        }

        SingleWrapper wrapper = (SingleWrapper) schema.newMessage();
        wrapper.writeField(value);

        schema.writeTo(output, wrapper);
    }
}
