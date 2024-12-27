package com.wlltb.monitor.service;

public interface SystemMonitorService {
    void printCpuInfo() throws InterruptedException;

    void printMemoryInfo();

    void printSystemInfo();

    void printJvmInfo();

    void printThreadInfo();

    void printDiskInfo();
}