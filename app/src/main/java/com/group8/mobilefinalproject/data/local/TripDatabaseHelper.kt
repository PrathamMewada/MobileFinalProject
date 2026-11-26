package com.group8.mobilefinalproject.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Table and column names for the "trips" table.
 */
object TripTable {
    const val TABLE = "trips"
    const val COL_ID = "id"
    const val COL_NAME = "name"
    const val COL_COUNTRY = "country"
    const val COL_CITY = "city"
    const val COL_START_DATE = "start_date"
    const val COL_END_DATE = "end_date"
    const val COL_BUDGET = "budget"
    const val COL_ESTIMATED_COST = "estimated_cost"
    const val COL_TRAVEL_STYLE = "travel_style"
    const val COL_UPDATED_AT = "updated_at"

    const val CREATE_TABLE = """
        CREATE TABLE $TABLE (
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_NAME TEXT NOT NULL,
            $COL_COUNTRY TEXT NOT NULL,
            $COL_CITY TEXT NOT NULL,
            $COL_START_DATE INTEGER NOT NULL,
            $COL_END_DATE INTEGER NOT NULL,
            $COL_BUDGET REAL NOT NULL,
            $COL_ESTIMATED_COST REAL NOT NULL DEFAULT 0,
            $COL_TRAVEL_STYLE TEXT NOT NULL,
            $COL_UPDATED_AT INTEGER NOT NULL
        )
    """
}

/**
 * Table and column names for the "plan_items" table.
 * Each row is one place in a trip's itinerary and belongs to one trip.
 */
object PlanItemTable {
    const val TABLE = "plan_items"
    const val COL_ID = "id"
    const val COL_TRIP_ID = "trip_id"
    const val COL_XID = "xid"
    const val COL_PLACE_NAME = "place_name"
    const val COL_CATEGORY = "category"
    const val COL_DAY_NUMBER = "day_number"
    const val COL_ORDER_IN_DAY = "order_in_day"

    // ON DELETE CASCADE: when a trip row is deleted, SQLite also deletes its plan items.
    const val CREATE_TABLE = """
        CREATE TABLE $TABLE (
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_TRIP_ID INTEGER NOT NULL,
            $COL_XID TEXT NOT NULL,
            $COL_PLACE_NAME TEXT NOT NULL,
            $COL_CATEGORY TEXT NOT NULL,
            $COL_DAY_NUMBER INTEGER NOT NULL,
            $COL_ORDER_IN_DAY INTEGER NOT NULL,
            FOREIGN KEY ($COL_TRIP_ID) REFERENCES ${TripTable.TABLE}(${TripTable.COL_ID})
                ON DELETE CASCADE
        )
    """

    // Plan items are always loaded by trip, so an index on trip_id makes that fast.
    const val CREATE_INDEX =
        "CREATE INDEX idx_${TABLE}_${COL_TRIP_ID} ON $TABLE($COL_TRIP_ID)"
}

/**
 * Creates and opens the app's local SQLite database (vagabond.db).
 * This class only defines the schema. All reading and writing is done in
 * TripRepository, so screens and ViewModels never touch SQL directly.
 */
class TripDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context.applicationContext, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TripTable.CREATE_TABLE)
        db.execSQL(PlanItemTable.CREATE_TABLE)
        db.execSQL(PlanItemTable.CREATE_INDEX)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${PlanItemTable.TABLE}")
        db.execSQL("DROP TABLE IF EXISTS ${TripTable.TABLE}")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "vagabond.db"
        const val DATABASE_VERSION = 1
    }
}
