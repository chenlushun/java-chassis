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

/**
 * consumer端对业务的接口，不同场景是完全不同的
 * 所以这里只定义consumer对core的接口
 * @author   
 * @version  [版本号, 2016年12月26日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ConsumerProvider {
    String getName();

    void init() throws Exception;
}
