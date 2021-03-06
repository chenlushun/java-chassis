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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import io.servicecomb.core.definition.OperationMeta;
import io.servicecomb.core.definition.SchemaMeta;
import io.servicecomb.core.invocation.InvocationType;
import io.servicecomb.core.provider.consumer.ReferenceConfig;
import io.servicecomb.swagger.invocation.SwaggerInvocation;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * @author   
 * @version  [版本号, 2016年11月30日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Invocation extends SwaggerInvocation {
    private ReferenceConfig referenceConfig;

    // 本次调用对应的schemaMeta
    private SchemaMeta schemaMeta;

    // 本次调用对应的operatoinMeta
    private OperationMeta operationMeta;

    // loadbalance查询得到的地址，由transport client使用
    // 之所以不放在handlerContext中，是因为这属于核心数据，没必要走那样的机制
    private Endpoint endpoint;

    // 只用于handler之间传递数据，是本地数据
    private Map<String, Object> handlerContext = new HashMap<>();

    // handler链，是arrayList，可以高效地通过index访问
    private List<Handler> handlerList;

    private int handlerIndex;

    // 应答的处理器
    // 同步模式：避免应答在网络线程中处理解码等等业务级逻辑
    private Executor responseExecutor;

    /**
     * <构造函数> [参数说明]
     */
    public Invocation(ReferenceConfig referenceConfig, OperationMeta operationMeta, Object[] swaggerArguments) {
        this.invocationType = InvocationType.CONSUMER;
        this.referenceConfig = referenceConfig;
        init(operationMeta, swaggerArguments);
    }

    /**
     * <构造函数> [参数说明]
     */
    public Invocation(Endpoint endpoint, OperationMeta operationMeta, Object[] swaggerArguments) {
        this.invocationType = InvocationType.PRODUCER;
        this.endpoint = endpoint;
        init(operationMeta, swaggerArguments);
    }

    private void init(OperationMeta operationMeta, Object[] swaggerArguments) {
        this.schemaMeta = operationMeta.getSchemaMeta();
        this.operationMeta = operationMeta;
        this.swaggerArguments = swaggerArguments;
        this.handlerList = getHandlerChain();
        handlerIndex = 0;
    }

    /**
     * 获取transport的值
     * @return 返回 transport
     */
    public Transport getTransport() {
        return endpoint.getTransport();
    }

    /**
     * 根据invocationType得到对应的handler链
     * @return
     */
    public List<Handler> getHandlerChain() {
        return (InvocationType.CONSUMER.equals(invocationType)) ? schemaMeta.getConsumerHandlerChain()
                : schemaMeta.getProviderHandlerChain();
    }

    /**
     * 获取responseExecutor的值
     * @return 返回 responseExecutor
     */
    public Executor getResponseExecutor() {
        return responseExecutor;
    }

    /**
     * 对responseExecutor进行赋值
     * @param responseExecutor responseExecutor的新值
     */
    public void setResponseExecutor(Executor responseExecutor) {
        this.responseExecutor = responseExecutor;
    }

    /**
     * 获取schemaMeta的值
     * @return 返回 schemaMeta
     */
    public SchemaMeta getSchemaMeta() {
        return schemaMeta;
    }

    /**
     * 获取operationMeta的值
     * @return 返回 operationMeta
     */
    public OperationMeta getOperationMeta() {
        return operationMeta;
    }

    /**
     * 获取args的值
     * @return 返回 args
     */
    public Object[] getArgs() {
        return swaggerArguments;
    }

    /**
     * 获取endpoint的值
     * @return 返回 endpoint
     */
    public Endpoint getEndpoint() {
        return endpoint;
    }

    /**
     * 对endpoint进行赋值
     * @param endpoint endpoint的新值
     */
    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * 获取handlerContext的值
     * @return 返回 handlerContext
     */
    public Map<String, Object> getHandlerContext() {
        return handlerContext;
    }

    /**
     * 获取handlerIndex的值
     * @return 返回 handlerIndex
     */
    public int getHandlerIndex() {
        return handlerIndex;
    }

    /**
     * 对handlerIndex进行赋值
     * @param handlerIndex handlerIndex的新值
     */
    public void setHandlerIndex(int handlerIndex) {
        this.handlerIndex = handlerIndex;
    }

    public void next(AsyncResponse asyncResp) throws Exception {
        // 不必判断有效性，因为整个流程都是内部控制的
        int runIndex = handlerIndex;
        handlerIndex++;
        handlerList.get(runIndex).handle(this, asyncResp);
    }

    public String getSchemaId() {
        return schemaMeta.getSchemaId();
    }

    public String getOperationName() {
        return operationMeta.getOperationId();
    }

    public String getConfigTransportName() {
        return referenceConfig.getTransport();
    }

    public String getRealTransportName() {
        return (endpoint != null) ? endpoint.getTransport().getName() : getConfigTransportName();
    }

    public String getMicroserviceName() {
        return schemaMeta.getMicroserviceName();
    }

    public String getAppId() {
        return schemaMeta.getMicroserviceMeta().getAppId();
    }

    /**
     * 获取microserviceVersionRule的值
     * @return 返回 microserviceVersionRule
     */
    public String getMicroserviceVersionRule() {
        return referenceConfig.getMicroserviceVersionRule();
    }

    public String getInvocationQualifiedName() {
        return invocationType.name() + " " + getRealTransportName() + " "
                + getOperationMeta().getMicroserviceQualifiedName();
    }
}
