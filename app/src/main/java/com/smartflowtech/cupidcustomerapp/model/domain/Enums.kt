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
    TWO_WEEKS,
    ONE_MONTH,
    SIX_MONTHS,
    ONE_YEAR,
    TWO_YEARS
}

enum class DaysFilter {
    TODAY,
    LAST_SEVEN_DAYS,
    LAST_THIRTY_DAYS,
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