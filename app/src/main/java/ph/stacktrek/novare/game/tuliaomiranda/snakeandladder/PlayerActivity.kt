package ph.stacktrek.novare.game.tuliaomiranda.snakeandladder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.adapters.PlayerAdapter
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.adapters.SwipeCallback
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.dao.PlayerDAO
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.dao.PlayerDAOSQLLiteImplementation

import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.databinding.ActivityPlayerBinding
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.model.Player

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding

    private lateinit var playerDAO: PlayerDAO
    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadPlayers()


        binding.addPlayerButton.setOnClickListener {
            val player = Player("")
            Log.d("This is player size", playerDAO.getPlayers().size.toString())
            player.nickname = binding.nicknameEditText.text.toString()

            val playerDAO = PlayerDAOSQLLiteImplementation(applicationContext)
            playerDAO.addPlayer(player)
            playerAdapter.addPlayer(player)


        }
        binding.startButton.setOnClickListener {
            val goToGame = Intent(
                applicationContext,
                GameActivity::class.java
            )
            startActivity(goToGame)
            finish()
        }





    }

    private fun loadPlayers(){
        playerDAO = PlayerDAOSQLLiteImplementation(applicationContext)
        playerAdapter = PlayerAdapter(applicationContext, playerDAO.getPlayers())
        with(binding.playerRecyclerView){
            layoutManager = LinearLayoutManager(applicationContext,
                LinearLayoutManager.VERTICAL,false)

            adapter = playerAdapter
        }
        val swipeCallback = SwipeCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        swipeCallback.playerAdapter = playerAdapter
        itemTouchHelper = ItemTouchHelper(swipeCallback).apply {

            attachToRecyclerView(binding.playerRecyclerView)
        }
    }



}
