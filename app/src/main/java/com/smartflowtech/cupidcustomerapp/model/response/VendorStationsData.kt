package com.smartflowtech.cupidcustomerapp.model.response

import com.squareup.moshi.Json


typealias VendorStationsData = List<VendorStation>

data class VendorStation (
    @Json(name = "id")
    val id: Long,

    @Json(name = "name")
    val name: String,

    @Json(name = "code")
    val code: String,

    @Json(name = "address")
    val address: String,

    @Json(name = "opening_time")
    val openingTime: String,

    @Json(name = "manager_name")
    val managerName: String,

    @Json(name = "manager_phone")
    val managerPhone: String,

    @Json(name = "manager_email")
    val managerEmail: String,

    @Json(name = "city")
    val city: String,

    @Json(name = "state")
    val state: String,

    @Json(name = "daily_budget")
    val dailyBudget: String,

    @Json(name = "monthly_budget")
    val monthlyBudget: String,

    @Json(name = "license_type")
    val licenseType: String,

    @Json(name = "expenses_type")
    val expensesType: String,

    @Json(name = "company_id")
    val companyID: String,

    @Json(name = "station_user_id")
    val stationUserID: Any? = null,

    @Json(name = "is_station_enabled")
    val isStationEnabled: Long,

    @Json(name = "created_at")
    val createdAt: String,

    @Json(name = "updated_at")
    val updatedAt: String,

    @Json(name = "deleted_at")
    val deletedAt: Any? = null,

    @Json(name = "v1_id")
    val v1ID: String,

    @Json(name = "show_atg_dpk")
    val showAtgDpk: Long,

    @Json(name = "show_atg_ago")
    val showAtgAgo: Long,

    @Json(name = "show_atg_pms")
    val showAtgPms: Long,

    @Json(name = "show_fcc_dpk")
    val showFccDpk: Long,

    @Json(name = "show_fcc_ago")
    val showFccAgo: Long,

    @Json(name = "show_fcc_pms")
    val showFccPms: Long,

    @Json(name = "show_atg_data")
    val showAtgData: Long,

    @Json(name = "show_fcc_data")
    val showFccData: Long,

    @Json(name = "atg_active")
    val atgActive: Long,

    @Json(name = "fcc_active")
    val fccActive: Long,

    @Json(name = "hasFCC")
    val hasFCC: Long,

    @Json(name = "hasATG")
    val hasATG: Long,

    @Json(name = "regionid")
    val regionId: Any?,

    @Json(name = "fcc_oem")
    val fccOEM: String,

    @Json(name = "atg_oem")
    val atgOEM: Any?,

    @Json(name = "daily_pms_target")
    val dailyPmsTarget: Long,

    @Json(name = "daily_ago_target")
    val dailyAgoTarget: Long,

    @Json(name = "daily_dpk_target")
    val dailyDpkTarget: Long,

    @Json(name = "daystodelivery")
    val daysToDelivery: Long,

    @Json(name = "oem_stationid")
    val oemStationId: Long,

    @Json(name = "start_date")
    val startDate: String,

    @Json(name = "pc_approval_levels")
    val pcApprovalLevels: Long,

    @Json(name = "approved_pms_tolerance")
    val approvedPmsTolerance: String,

    @Json(name = "approved_ago_tolerance")
    val approvedAgoTolerance: String,

    @Json(name = "approved_dpk_tolerance")
    val approvedDpkTolerance: String,

    @Json(name = "atg_data_source")
    val atgDataSource: String,

    @Json(name = "combox_mac_address")
    val comboxMACAddress: String? = null,

    @Json(name = "velox_station")
    val veloxStation: String,

    @Json(name = "is_loyalty_station")
    val isLoyaltyStation: String,

    @Json(name = "pts_mac_address")
    val ptsMACAddress: Any? = null,

    @Json(name = "oemterminalID")
    val oemTerminalID: Long,

    @Json(name = "IsActive")
    val isActive: Long,

    @Json(name = "IsOnline")
    val isOnline: Long,

    @Json(name = "terminalstatus")
    val terminalStatus: Long,

    @Json(name = "lastcall")
    val lastCall: String,

    @Json(name = "lastfill")
    val lastFill: String
)

