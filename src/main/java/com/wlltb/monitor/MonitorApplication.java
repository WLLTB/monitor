package com.wlltb.monitor;

import com.wlltb.monitor.service.SystemMonitorService;
import com.wlltb.monitor.service.SystemMonitorServiceImpl;

public class MonitorApplication {

    public static void main(String[] args) {
        SystemMonitorService monitorService = new SystemMonitorServiceImpl();
        try {
            monitorService.printCpuInfo();
            monitorService.printMemoryInfo();
            monitorService.printSystemInfo();
            monitorService.printJvmInfo();
            monitorService.printThreadInfo();
            monitorService.printDiskInfo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
