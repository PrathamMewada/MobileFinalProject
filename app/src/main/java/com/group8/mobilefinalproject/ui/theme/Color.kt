// Keep the package line from your existing Color.kt file
package com.group8.mobilefinalproject.ui.theme

import androidx.compose.ui.graphics.Color

// ----- Vagabond brand colors -----
val VagabondBlue = Color(0xFF1E6FD9)    // primary: buttons, selected tabs, links
val VagabondNavy = Color(0xFF0B2545)    // headings and main text
val SunsetOrange = Color(0xFFFF8A3D)    // FAB, call-to-action, meal slots
val SkyTint = Color(0xFFE8F1FD)         // chips and soft backgrounds
val SlateGray = Color(0xFF5C6B80)       // secondary text
val LineGray = Color(0xFFD3DEEC)        // borders and dividers
val LightBackground = Color(0xFFF5F8FD) // app background (light mode)

// ----- Budget status colors (used by budget bars and the Budget tab) -----
val BudgetUnder = Color(0xFF2EC4B6)     // teal: under 90% of the budget
val BudgetWarning = Color(0xFFF2B705)   // yellow: more than 90% used
val BudgetOver = Color(0xFFE5484D)      // red: over the budget

// ----- Dark mode colors -----
val BlueDark = Color(0xFF7DB0F2)        // lighter blue so it is readable on dark backgrounds
val OrangeDark = Color(0xFFFFA66B)
val DarkBackground = Color(0xFF0E1A2B)
val DarkSurface = Color(0xFF16263B)
val DarkSurfaceVariant = Color(0xFF22344D)
val DarkText = Color(0xFFE6EDF7)
val DarkSecondaryText = Color(0xFFA9B6C8)