package com.group8.mobilefinalproject.data.model

import androidx.annotation.StringRes
import com.group8.mobilefinalproject.R

/**
 * Travel style chosen on the trip form. BudgetCalculator uses it to estimate costs.
 *
 * The enum [name] (BUDGET, MID_RANGE, LUXURY) is what is saved in SQLite and Firestore.
 * [labelRes] is the text shown to the user, so no UI text is hardcoded.
 */
enum class TravelStyle(@StringRes val labelRes: Int) {
    BUDGET(R.string.travel_style_budget),
    MID_RANGE(R.string.travel_style_mid_range),
    LUXURY(R.string.travel_style_luxury);

    companion object {
        /** Reads a saved value back. Unknown or missing values fall back to MID_RANGE. */
        fun fromName(name: String?): TravelStyle =
            entries.firstOrNull { it.name == name } ?: MID_RANGE
    }
}
