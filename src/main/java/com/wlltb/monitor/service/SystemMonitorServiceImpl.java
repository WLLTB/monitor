package com.wlltb.monitor.service;

import com.wlltb.monitor.utils.MonitorUtils;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * SystemMonitorServiceImpl
 * 实现了 SystemMonitorService 接口，用于监控系统的运行状态。
 * 包含CPU信息、内存信息、操作系统信息、JVM信息、线程信息以及磁盘信息。
 */
public class SystemMonitorServiceImpl implements SystemMonitorService {

    /**
     * 打印CPU信息，包括CPU核心数、系统使用率、用户使用率和总使用率。
     * @throws InterruptedException 如果线程在睡眠时被中断。
     */
    @Override
    public void printCpuInfo() throws InterruptedException {
        System.out.println("----------------CPU信息----------------");

        // 创建SystemInfo实例，用于访问硬件信息
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();

        // 获取CPU负载的初始状态
        long[] prevTicks = processor.getSystemCpuLoadTicks();

        // 等待1秒，收集新的CPU负载数据
        TimeUnit.SECONDS.sleep(1);

        // 获取CPU负载的当前状态
        long[] ticks = processor.getSystemCpuLoadTicks();
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long sys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + sys + idle;

        // 输出CPU信息
        System.out.println("CPU核心数: " + processor.getLogicalProcessorCount());
        System.out.println("CPU系统使用率: " + MonitorUtils.RATE_DECIMAL_FORMAT.format(sys * 1.0 / totalCpu));
        System.out.println("CPU用户使用率: " + MonitorUtils.RATE_DECIMAL_FORMAT.format(user * 1.0 / totalCpu));
        System.out.println("CPU当前使用率: " + MonitorUtils.RATE_DECIMAL_FORMAT.format(1.0 - idle * 1.0 / totalCpu));
    }

    /**
     * 打印主机内存信息，包括总内存、已用内存、剩余内存和使用率。
     */
    @Override
    public void printMemoryInfo() {
        System.out.println("----------------主机内存信息----------------");

        // 获取主机内存信息
        SystemInfo systemInfo = new SystemInfo();
        GlobalMemory memory = systemInfo.getHardware().getMemory();

        long total = memory.getTotal();       // 总内存
        long available = memory.getAvailable(); // 可用内存

        // 输出内存信息
        System.out.println("总内存: " + MonitorUtils.formatByte(total));
        System.out.println("已用内存: " + MonitorUtils.formatByte(total - available));
        System.out.println("剩余内存: " + MonitorUtils.formatByte(available));
        System.out.println("内存使用率: " + MonitorUtils.RATE_DECIMAL_FORMAT.format((total - available) * 1.0 / total));
    }

    /**
     * 打印操作系统信息，包括操作系统名称、系统架构和详细信息。
     */
    @Override
    public void printSystemInfo() {
        System.out.println("----------------操作系统信息----------------");

        // 使用Java的System类获取操作系统属性
        Properties props = System.getProperties();

        // 使用OSHI库获取操作系统的详细信息
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();

        // 输出操作系统信息
        System.out.println("操作系统: " + props.getProperty("os.name"));
        System.out.println("系统架构: " + props.getProperty("os.arch"));
        System.out.println("详细系统信息: " + os);
    }

    /**
     * 打印JVM信息，包括Java版本、JVM内存信息和运行时间。
     */
    @Override
    public void printJvmInfo() {
        System.out.println("----------------JVM信息----------------");

        // 使用Java的System类和Runtime类获取JVM信息
        Properties props = System.getProperties();
        Runtime runtime = Runtime.getRuntime();

        long totalMemory = runtime.totalMemory();  // JVM已分配的总内存
        long maxMemory = runtime.maxMemory();      // JVM最大可用内存
        long freeMemory = runtime.freeMemory();    // JVM空闲内存

        // 获取JVM启动时间
        long startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
        LocalDateTime start = LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime), ZoneId.systemDefault());
        Duration uptime = Duration.between(start, LocalDateTime.now());

        // 输出JVM信息
        System.out.println("Java版本: " + props.getProperty("java.version"));
        System.out.println("JVM内存总量: " + MonitorUtils.formatByte(totalMemory));
        System.out.println("JVM最大内存: " + MonitorUtils.formatByte(maxMemory));
        System.out.println("JVM已用内存: " + MonitorUtils.formatByte(totalMemory - freeMemory));
        System.out.println(String.format("JVM运行时间: %d天 %d小时 %d分钟", uptime.toDays(), uptime.toHoursPart(), uptime.toMinutesPart()));
    }

    /**
     * 打印线程信息，包括活动线程数量、线程ID、线程名称和线程状态。
     */
    @Override
    public void printThreadInfo() {
        System.out.println("----------------线程信息----------------");

        // 获取主线程组
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        while (group.getParent() != null) group = group.getParent();

        // 获取活动线程的数量
        int activeThreads = group.activeCount();
        Thread[] threads = new Thread[activeThreads];
        group.enumerate(threads);

        // 输出线程信息
        for (Thread thread : threads) {
            System.out.println("线程: ID=" + thread.getId() + ", 名称=" + thread.getName() + ", 状态=" + thread.getState());
        }
    }

    /**
     * 打印磁盘信息，包括磁盘名称、总大小、已用空间、可用空间和使用率。
     */
    @Override
    public void printDiskInfo() {
        System.out.println("----------------磁盘信息----------------");

        // 使用OSHI库获取文件系统信息
        SystemInfo systemInfo = new SystemInfo();
        FileSystem fileSystem = systemInfo.getOperatingSystem().getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();

        // 输出每个磁盘的信息
        for (OSFileStore fs : fileStores) {
            System.out.printf("磁盘: %s, 总大小: %s, 已用: %s, 可用: %s, 使用率: %s%n",
                    fs.getName(),
                    MonitorUtils.formatByte(fs.getTotalSpace()),
                    MonitorUtils.formatByte(fs.getTotalSpace() - fs.getUsableSpace()),
                    MonitorUtils.formatByte(fs.getUsableSpace()),
                    MonitorUtils.RATE_DECIMAL_FORMAT.format((fs.getTotalSpace() - fs.getUsableSpace()) * 1.0 / fs.getTotalSpace()));
        }
    }
}