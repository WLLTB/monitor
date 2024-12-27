# System Monitor Service

A lightweight, Java-based system monitoring service leveraging the [OSHI](https://github.com/oshi/oshi) library. This project provides a simple yet comprehensive way to monitor system metrics such as CPU usage, memory usage, operating system details, JVM information, thread statistics, and disk usage.

轻量级的基于 Java 的系统监控服务，使用 [OSHI](https://github.com/oshi/oshi) 库实现。该项目提供了一种简单而全面的方式来监控系统指标，例如 CPU 使用率、内存使用情况、操作系统详情、JVM 信息、线程统计和磁盘使用率。

## Features / 特性

- **CPU Monitoring / CPU 监控**:
    - Logical processor count / CPU 核心数
    - System usage, user usage, and overall CPU usage percentage / 系统使用率、用户使用率和整体 CPU 使用率
- **Memory Monitoring / 内存监控**:
    - Total memory, used memory, free memory, and usage percentage / 总内存、已用内存、剩余内存和使用率
- **Operating System Info / 操作系统信息**:
    - OS name, architecture, and additional details / 操作系统名称、架构和详细信息
- **JVM Monitoring / JVM 监控**:
    - JVM memory stats (total, max, free) / JVM 内存统计（总内存、最大内存、空闲内存）
    - Java version and runtime duration / Java 版本和运行时间
- **Thread Monitoring / 线程监控**:
    - Active threads, thread states, and details / 活动线程数量、线程状态和详细信息
- **Disk Monitoring / 磁盘监控**:
    - Disk total size, used size, available size, and usage percentage / 磁盘总大小、已用大小、可用大小和使用率

## Example Output / 示例输出
----------------CPU信息----------------
CPU核心数: 8
CPU系统使用率: 10.25%
CPU用户使用率: 15.50%
CPU当前使用率: 25.75%

----------------主机内存信息----------------
总内存: 16 GB
已用内存: 10 GB
剩余内存: 6 GB
内存使用率: 62.50%

----------------操作系统信息----------------
操作系统: Windows 10
系统架构: amd64
详细系统信息: Windows 10 (10.0)

----------------JVM信息----------------
Java版本: 11.0.12
JVM内存总量: 512 MB
JVM最大内存: 2 GB
JVM已用内存: 256 MB
JVM运行时间: 0天 1小时 15分钟

----------------线程信息----------------
线程: ID=1, 名称=main, 状态=RUNNABLE
线程: ID=2, 名称=Monitor Ctrl-Break, 状态=RUNNABLE

----------------磁盘信息----------------
磁盘: C:\, 总大小: 256 GB, 已用: 128 GB, 可用: 128 GB, 使用率: 50.00%

## How It Works / 工作原理
1.	System Metrics / 系统指标: Uses the OSHI library to fetch hardware and operating system details.
使用 OSHI 库获取硬件和操作系统的详细信息。
2.	JVM Monitoring / JVM 监控: Uses standard Java libraries (Runtime, ManagementFactory) for JVM stats.
使用标准 Java 库 (Runtime, ManagementFactory) 获取 JVM 统计信息。
3.	Thread Monitoring / 线程监控: Enumerates all active threads in the current JVM process.
枚举当前 JVM 进程中的所有活动线程。
4.	Output / 输出: Displays collected data in a human-readable format.
以人类可读的格式显示收集的数据。