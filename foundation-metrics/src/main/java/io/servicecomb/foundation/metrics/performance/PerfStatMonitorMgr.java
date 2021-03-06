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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PerfStatMonitorMgr
 * @author  
 *
 */
public class PerfStatMonitorMgr {
    private static final Logger LOGGER = LoggerFactory.getLogger(PerfStatMonitorMgr.class);

    private Map<String, PerfStatMonitor> monitorMap = new HashMap<>();

    private List<PerfStatMonitor> monitorList = new ArrayList<>();

    private String header = String.format(
            "             call count       msg count        avg tps    avg latency(ms) |%s",
            PerfStatData.getStrSegmentDef());

    private String statFmt = "%-16d %-16d %-10d %-16.3f %s\n";

    /**
     * registerPerfStat
     * @param perfStat  perfStat
     * @param index     index
     */
    public void registerPerfStat(PerfStat perfStat, int index) {
        String name = perfStat.getName();
        PerfStatMonitor monitor = monitorMap.get(name);
        if (monitor == null) {
            monitor = new PerfStatMonitor(name, index);
            monitorMap.put(name, monitor);

            monitorList.add(monitor);

            monitorList.sort(new Comparator<PerfStatMonitor>() {

                @Override
                public int compare(PerfStatMonitor o1, PerfStatMonitor o2) {
                    return o1.getIndex() - o2.getIndex();
                }
            });
        }

        monitor.addThreadStat(perfStat);
    }

    /**
     * onCycle
     * @param msNow    now
     * @param msCycle  cycle
     */
    public void onCycle(long msNow, long msCycle) {
        StringBuilder sb = new StringBuilder();
        sb.append("Cycle stat output:\n" + header + "\n");
        for (PerfStatMonitor monitor : monitorList) {
            monitor.calcCycle(msNow, msCycle);

            sb.append(" " + monitor.getName() + ":\n");
            monitor.format(sb, statFmt);
        }

        LOGGER.info(sb.toString());
    }

    public Map<String, PerfStat> getMonitorPerfStat() {
        return monitorList.stream().collect(Collectors.toMap(m -> m.getName(), m -> m.getPerfStat()));
    }

    public List<PerfStatMonitor> getMonitorList() {
        return monitorList;
    }

}
