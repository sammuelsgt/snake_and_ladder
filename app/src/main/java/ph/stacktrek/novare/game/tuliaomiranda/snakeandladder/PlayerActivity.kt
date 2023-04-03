package ph.stacktrek.novare.game.tuliaomiranda.snakeandladder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.adapters.PlayerAdapter
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.dao.PlayerDAO
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.dao.PlayerDAOSQLLiteImplementation

import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.databinding.ActivityPlayerBinding
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.model.Player

    class PlayerActivity : AppCompatActivity() {

        lateinit var binding: ActivityPlayerBinding
        private lateinit var playerDAO: PlayerDAO
        private lateinit var playerAdapter: PlayerAdapter


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityPlayerBinding.inflate(layoutInflater)

            setContentView(binding.root)
            loadPlayers()

            binding.addPlayerButton.setOnClickListener {
                val player = Player("")
                player.nickname = binding.nicknameEditText.text.toString()

                if(playerAdapter.itemCount < 4){
                    val playerDAO = PlayerDAOSQLLiteImplementation(applicationContext)
                    playerDAO.addPlayer(player)
                    playerAdapter.addPlayer(player)
                }

                binding.addPlayerButton.isEnabled = playerAdapter.itemCount < 4
                binding.startButton.isEnabled = playerAdapter.itemCount in 2..4




            }

            //Start Button
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
            val players = playerDAO.getPlayers()
            playerAdapter = PlayerAdapter(this@PlayerActivity, playerDAO.getPlayers())
            with(binding.playerRecyclerView){
                layoutManager = LinearLayoutManager(applicationContext,
                    LinearLayoutManager.VERTICAL,false)

                adapter = playerAdapter
            }

            binding.addPlayerButton.isEnabled = players.size < 4
            binding.startButton.isEnabled = players.size in 2..4

        }
    }

