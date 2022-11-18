package com.smartflowtech.cupidcustomerapp.model.domain

enum class Status {
    COMPLETED,
    FAILED,
    PENDING
}

enum class Product {
    PMS,
    DPK,
    AGO
}

enum class Period {
    TODAY,
    ONE_WEEK,
    ONE_MONTH,
    ONE_YEAR,
    TWO_YEARS //to be removed
}

enum class CardHistoryPeriodFilterContext {
    MONTH_YEAR,
    DEFAULT
}

enum class Category {
    STATUS,
    PRODUCT,
    DATE
}

enum class PaymentGateway {
    PAYSTACK
}

enum class PaymentMethodPreference {
    PAYSTACK,
    ASK_ALWAYS
}


enum class StationFilter {
    STATE,
    CITY
}