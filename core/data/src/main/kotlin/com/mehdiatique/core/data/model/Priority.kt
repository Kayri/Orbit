package com.mehdiatique.core.data.model

enum class Priority(val label: String) {
    NONE("None"),
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");

    companion object {
        fun fromOrdinal(ordinal: Int): Priority = entries.getOrElse(ordinal) { NONE }
    }
}