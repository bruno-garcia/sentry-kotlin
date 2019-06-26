package io.sentry

import java.time.Instant

class SentryContext : HashMap<String, Any>() {
    var app: App? = null
    var browser: Browser? = null
    var device: Device? = null
    var operatingSystem: OperatingSystem? = null
    var runtime: Runtime? = null
    var gpu: Gpu? = null
}

class Gpu {
    val type: String = "gpu"
    var name: String? = null
    var id: String? = null
    var vendor: String? = null
    var vendorId: Int? = null
    var memorySize: Int? = null
    var apiType: Int? = null
    var multiThreadedRendering: Int? = null
    var version: String? = null
    var npotSupport: String? = null
}

class Runtime {
    val type: String = "runtime"
    var name: String? = null
    var version: String? = null
    var rawDescription: String? = null
    var build: String? = null
}

class OperatingSystem {
    val type: String = "os"
    var name: String? = null
    var version: String? = null
    var rawDescription: String? = null
    var build: String? = null
    var kernelVersion: String? = null
    var rooted: Boolean? = null
}

class Device {
    val type: String = "device"
    var name: String? = null
    var manufacturer: String? = null
    var brand: String? = null
    var family: String? = null
    var model: String? = null
    var modelId: String? = null
    var architecture: String? = null
    var isCharging: Boolean? = null
    var isOnline: Boolean? = null
    var orientation: DeviceOrientation? = null
    var isSimulator: Boolean? = null
    var memorySize: Long? = null
    var freeMemory: Long? = null
    var usableMemory: Long? = null
    var lowMemory: Boolean? = null
    var storageSize: Long? = null
    var freeStorage: Long? = null
    var externalStorageSize: Long? = null
    var externalFreeStorage: Long? = null
    var screenResolution: String? = null
    var screenDensity: Float? = null
    var screenDpi: Int? = null
    var bootTime: Instant? = null
}

enum class DeviceOrientation {
    PORTRAIT,
    LANDSCAPE
}

class Browser {
    val type: String = "browser"
    var name: String? = null
    var version: String? = null
}

class App {
    val type: String = "app"
    var identifier: String? = null
    var startTime: String? = null
    var hash: String? = null
    var buildType: String? = null
    var name: String? = null
    var version: String? = null
    var build: String? = null
}
