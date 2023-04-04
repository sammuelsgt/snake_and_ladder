package ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.adapters.DatabaseHandler
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.model.Player

interface PlayerDAO{

fun addPlayer(player: Player)
fun getPlayers() : ArrayList<Player>
fun getWinners() : ArrayList<String>

fun getWinner(): String
fun updatePlayer(player: Player)
fun deletePlayer(player: Player)
fun deletePlayerByNickname(nickname: String)



fun addWinner(nickname: String)
}



class PlayerDAOSQLLiteImplementation(var context: Context): PlayerDAO{

    override fun addPlayer(player: Player) {
        val databaseHandler = DatabaseHandler(context)
        val db = databaseHandler.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(DatabaseHandler.TABLE_PLAYER_NAME, player.nickname)
        var status = db.insert(DatabaseHandler.TABLE_PLAYER,
            null,contentValues)
        db.close()
    }

    override fun addWinner(nickname: String) {
        val databaseHandler = DatabaseHandler(context)
        val db = databaseHandler.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(DatabaseHandler.TABLE_WINNER_NAME, nickname)
        var status = db.insert(DatabaseHandler.TABLE_WINNER, null, contentValues)
        db.close()
    }

    override fun getPlayers(): ArrayList<Player> {
        val databaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var result = ArrayList<Player>()
        var cursor: Cursor? = null
        val columns = arrayOf(DatabaseHandler.TABLE_PLAYER_ID,DatabaseHandler.TABLE_PLAYER_NAME)
        try {
            cursor = db.query(DatabaseHandler.TABLE_PLAYER,
                columns,null,null,null,null,null)
        }catch (sqlException: SQLException){
            db.close()
            return result
        }
        if(cursor.moveToFirst()){
            do{
                var player = Player("")
                player.nickname = cursor.getString(1)
                player.id = cursor.getInt(0).toString()
                result.add(player)
            }while (cursor.moveToNext())
        }
        return result
    }

    override fun getWinners(): ArrayList<String> {
        val databaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        val winners = ArrayList<String>()
        var cursor: Cursor? = null
        val columns = arrayOf(DatabaseHandler.TABLE_WINNER_ID,DatabaseHandler.TABLE_WINNER_NAME)
        try {
            cursor = db.query(DatabaseHandler.TABLE_WINNER,
                columns,null,null,null,null,
                DatabaseHandler.TABLE_WINNER_ID + " DESC", "5")
        }catch (sqlException: SQLException){
            db.close()
            return winners
        }
        if(cursor.moveToFirst()){
            do{
                var winner = Player("")
                winner.nickname = cursor.getString(1)
                winner.id = cursor.getInt(0).toString()
                winners.add(winner.nickname)
            }while (cursor.moveToNext())
        }
        return winners

    }

    override fun getWinner(): String {

            val databaseHandler = DatabaseHandler(context)
            val db = databaseHandler.readableDatabase
            val winners = ""
            var cursor: Cursor? = null
            val columns = arrayOf(DatabaseHandler.TABLE_WINNER_ID,DatabaseHandler.TABLE_WINNER_NAME)
            try {
                cursor = db.query(DatabaseHandler.TABLE_WINNER,
                    columns,null,null,null,null, DatabaseHandler.TABLE_WINNER_ID + "DESC", "1")
            }catch (sqlException: SQLException){
                db.close()
                return winners
            }
            return winners
    }

    override fun updatePlayer(player: Player) {
        TODO("Not yet implemented")
    }

    override fun deletePlayer(player: Player) {
        TODO("Not yet implemented")
    }

    override fun deletePlayerByNickname(nickname: String) {
        val databaseHandler = DatabaseHandler(context)
        val db = databaseHandler.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(DatabaseHandler.TABLE_PLAYER_NAME, nickname)
        db.delete(DatabaseHandler.TABLE_PLAYER, DatabaseHandler.TABLE_PLAYER_NAME + "=" + "'"+nickname+"'", null)
        db.close()
    }


}













