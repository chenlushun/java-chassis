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

package io.servicecomb.foundation.metrics.performance;

/**
 * PerfStatSuccFail
 * @author  
 *
 */
public class PerfStatSuccFail extends PerfStatImpl {

    private PerfStatData succ = new PerfStatData("succ");

    private PerfStatData fail = new PerfStatData("fail");

    /**
     * 构造
     * @param name name
     */
    public PerfStatSuccFail(String name) {
        super(name);

        addPerfStatData(succ);
        addPerfStatData(fail);
    }

    /**
     * add
     * @param isSucc    isSucc
     * @param msgCount  msgCount
     * @param latency   latency
     */
    public void add(boolean isSucc, int msgCount, long latency) {
        PerfStatData statData = succ;
        if (!isSucc) {
            msgCount = 0;
            statData = fail;
        }
        statData.add(msgCount, latency);
    }

    /**
     * add
     * @param isSucc    isSucc
     * @param context   context
     */
    public void add(boolean isSucc, PerfStatContext context) {
        add(isSucc, context.getMsgCount(), context.getLatency());
    }
}
