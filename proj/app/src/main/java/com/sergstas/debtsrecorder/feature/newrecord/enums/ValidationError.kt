package com.sergstas.debtsrecorder.feature.newrecord.enums

enum class ValidationError {
    INCORRECT_SUM,
    CLIENT_IS_NULL,
    FIRST_NAME_IS_NULL,
    LAST_NAME_IS_NULL,
    CLIENT_ALREADY_EXISTS,
    NAME_CONTAINS_SPACES,
    UNKNOWN
}