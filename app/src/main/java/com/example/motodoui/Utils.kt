package com.example.motodoui

import java.util.UUID

class Utils {
// File: Utils.kt

    object Utils {
        // Utility function to generate a unique ID
        fun generateUniqueId(): String {
            return UUID.randomUUID().toString()
        }
    }
}