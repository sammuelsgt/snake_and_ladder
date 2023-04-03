package ph.stacktrek.novare.game.tuliaomiranda.snakeandladder

import android.content.ContentValues.TAG
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.add
import androidx.fragment.app.commit
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.adapters.PlayerAdapter
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.dao.PlayerDAO
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.dao.PlayerDAOSQLLiteImplementation
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.databinding.ActivityGameBinding
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.model.Player
import java.text.FieldPosition

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var diceImage: ImageView
    private lateinit var rollButton: Button
    private lateinit var playerDAO: PlayerDAO
    private lateinit var playerIndicator: TextView
    private lateinit var playerOne: TextView
    private lateinit var playerTwo: TextView
    private lateinit var playerThree: TextView
    private lateinit var playerFour: TextView
    private lateinit var gridLayout: GridLayout
    private lateinit var playerOneMarker: ImageView
    private lateinit var playerTwoMarker: ImageView
    private lateinit var playerThreeMarker: ImageView
    private lateinit var playerFourMarker: ImageView
    private lateinit var playerMarker: ArrayList<ImageView>
    private  var currentPlayer = 0
    private var test = 0
    private  var scores = arrayOf(0,0,0,0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playerDAO = PlayerDAOSQLLiteImplementation(applicationContext)

        diceImage = binding.diceImage
        rollButton = binding.rollDiceButton

        playerIndicator = binding.playerTurnIndicator
//        binding.playerTurnIndicator.text = playerDAO.getPlayers()[0].nickname
        playerIndicator.text = "${test+1}"

        playerOne = binding.playerOne
        playerOne.text = playerDAO.getPlayers()[0].nickname

        playerTwo = binding.playerTwo
        playerTwo.text = playerDAO.getPlayers()[1].nickname

        playerThree = binding.playerThree
        playerThree.text = playerDAO.getPlayers()[2].nickname

        playerFour = binding.playerFour
        playerFour.text = playerDAO.getPlayers()[3].nickname

        gridLayout = binding.gridLayout


        rollButton.setOnClickListener{
            rollDice()
        }

    }

    private fun rollDice(){
        val diceRoll: Int = (1..6).random()

        val drawableResource = when(diceRoll){
            1 -> R.drawable.dice_one
            2 -> R.drawable.dice_two
            3 -> R.drawable.dice_three
            4 -> R.drawable.dice_four
            5 -> R.drawable.dice_five
            else -> R.drawable.dice_six
        }
        diceImage.setImageResource(drawableResource)



        scores[currentPlayer] += diceRoll
        currentPlayer = (currentPlayer + 1)% 4
        Log.d("Scores", "Player 1:${scores[0]}, " +
                "Player 2:${scores[1]}, Player 3:${scores[2]} " +
                "Player 4:${scores[3]}  Dice: ${diceRoll}")

        val checkPosition =  when(scores[currentPlayer]){
            7 ->  28 //ladder
            21 ->  60 //ladder
            22 -> 16 //snake
            44 -> 4 //snake
            53 -> 67 //ladder
            51 ->  32 //snake
            64 ->  96 //ladder
            66 ->  22 //snake
            71 -> 92 //ladder
            89 ->  49 //snake
            98 ->  23 //snake
            else -> scores[currentPlayer]
        }

        scores[currentPlayer] = checkPosition
        playerMarker = ArrayList()
        playerMarker.add(binding.blackPawn)
        playerMarker.add(binding.bluePawn)
        playerMarker.add(binding.redPawn)
        playerMarker.add(binding.yellowPawn)
        moveMarker(playerMarker[currentPlayer], scores[currentPlayer])

        playerIndicator.text = "It's ${ playerDAO.getPlayers()[currentPlayer].nickname}'s Turn ${scores[currentPlayer]+1}"
        playerOne.text = "${ playerDAO.getPlayers()[0].nickname}  ${scores[0]+1} Black "
        playerTwo.text = "${ playerDAO.getPlayers()[1].nickname}  ${scores[1]} Blue"
        playerThree.text = "${ playerDAO.getPlayers()[2].nickname}  ${scores[2]} Red"
        playerFour.text = "${ playerDAO.getPlayers()[3].nickname}  ${scores[3]} Yellow"


    }

    fun moveMarker(marker: ImageView, position: Int){
        val row = 9 - position / 10
        val col = if (row % 2 == 0) 9 - position % 10 else position % 10
        val cellWidth = gridLayout.width / 10
        val cellHeight = gridLayout.height / 10
        val x = col * cellWidth
        val y = row * cellHeight


        marker.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            leftMargin = x
            topMargin = y
        }
    }







}