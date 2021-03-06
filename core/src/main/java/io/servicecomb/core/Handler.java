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

package io.servicecomb.core;

import io.servicecomb.core.definition.MicroserviceMeta;
import io.servicecomb.core.invocation.InvocationType;

// 每个微服务 + invocationType，都对应一个handler实例
public interface Handler {
    void init(MicroserviceMeta microserviceMeta, InvocationType invocationType);

    void handle(Invocation invocation, AsyncResponse asyncResp) throws Exception;
}
