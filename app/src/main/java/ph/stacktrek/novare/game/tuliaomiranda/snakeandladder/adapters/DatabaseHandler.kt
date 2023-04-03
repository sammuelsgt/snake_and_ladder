package ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.adapters

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context):
    SQLiteOpenHelper(context, DATABASENAME, null, DATABASEVERSION){

    companion object{
        private val DATABASEVERSION = 1
        private val DATABASENAME = "SnakeAndLadder"

        const val TABLE_PLAYER = "player_table"
        const val TABLE_PLAYER_ID = "player_id"
        const val TABLE_PLAYER_NAME = "player_name"

        const val TABLE_WINNER = "winner_table"
        const val TABLE_WINNER_ID = "winner_id"
        const val TABLE_WINNER_NAME = "winner_name"

    }
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_PLAYER_TABLE =
            "CREATE TABLE $TABLE_PLAYER " +
                    "($TABLE_PLAYER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$TABLE_PLAYER_NAME TEXT)"

        val CREATE_WINNER_TABLE =
            "CREATE TABLE $TABLE_WINNER " +
                    "($TABLE_WINNER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$TABLE_WINNER_NAME TEXT)"

        db?.execSQL(CREATE_PLAYER_TABLE)
        db?.execSQL(CREATE_WINNER_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_WINNER")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_PLAYER")
        onCreate(db)
    }


}