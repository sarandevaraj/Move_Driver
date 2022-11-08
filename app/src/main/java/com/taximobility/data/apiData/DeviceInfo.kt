package com.taximobility.data.apiData

data class DeviceInfo(val manufacture: String?,
                      val model: String = "",
                      val sdk: Int = 0,

                      val versioncode: String = "",
                      var carrierName: String = "",
                      val googlePlayVersion: Int = 0,
                      val batteryPercent: Int = 0,
                      val availMem: String = "",
                      val availStorage: String = "",
                      val totalMem: String = "",
                      val appVersionCode: Int = 0,
                      val appversionName: String = "",
                      val current_time: String = "",
                      val deviceType: String = "1",
                      val deviceToken: String = "",
                      val deviceId: String = "")