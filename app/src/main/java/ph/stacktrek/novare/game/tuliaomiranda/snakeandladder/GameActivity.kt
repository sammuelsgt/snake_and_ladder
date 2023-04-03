package ph.stacktrek.novare.game.tuliaomiranda.snakeandladder

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.dao.PlayerDAO
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.dao.PlayerDAOSQLLiteImplementation
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.databinding.ActivityGameBinding
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.databinding.DialogPlayerWinnerBinding

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
    private lateinit var playerMarker: ArrayList<ImageView>
    private  var currentPlayer = 0
    private var test = 0
    private  var scores = arrayOf(0,0,0,0)
    private var winner = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playerDAO = PlayerDAOSQLLiteImplementation(applicationContext)

        diceImage = binding.diceImage
        rollButton = binding.rollDiceButton

        playerIndicator = binding.playerTurnIndicator
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
        val newPosition = scores[currentPlayer] + diceRoll
        val excess = newPosition - 99
        val finalPosition = if (excess > 0 ){
            scores[currentPlayer] - excess
        }
        else{
            newPosition
        }

        var checkPosition =  when(finalPosition){
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
            else -> finalPosition
        }
        if(checkPosition == 99){
            moveMarker(playerMarker[currentPlayer], scores[currentPlayer])
            winner = playerDAO.getPlayers()[currentPlayer].nickname
            showWinnerDialogue().show()

        }
        else{

            scores[currentPlayer] = checkPosition
            playerMarker = arrayListOf(binding.blackPawn,binding.bluePawn,binding.redPawn,binding.yellowPawn)
            moveMarker(playerMarker[currentPlayer], scores[currentPlayer])

            playerOne.text = "${ playerDAO.getPlayers()[0].nickname}  ${scores[0]+1} Black "
            playerTwo.text = "${ playerDAO.getPlayers()[1].nickname}  ${scores[1]+1} Blue"
            playerThree.text = "${ playerDAO.getPlayers()[2].nickname}  ${scores[2]+1} Red"
            playerFour.text = "${ playerDAO.getPlayers()[3].nickname}  ${scores[3]+1} Yellow"
            currentPlayer = (currentPlayer + 1) % 4
            playerIndicator.text = "It's ${ playerDAO.getPlayers()[currentPlayer].nickname}'s Turn ${scores[currentPlayer]+1}"

        }





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
            marker.animate().translationX(x.toFloat())
                .translationY(y.toFloat()).duration = 500

        }
    }

    private fun resetGame(){
        currentPlayer = 0
        scores = arrayOf(0,0,0,0)

    }

    private fun showWinnerDialogue(): Dialog {
        return this.let{
            val builder = AlertDialog.Builder(it)
            val dialogPlayerActivity: DialogPlayerWinnerBinding =
                DialogPlayerWinnerBinding.inflate(it.layoutInflater)

            with(builder){
                setPositiveButton("New Game", DialogInterface.OnClickListener { dialog, id ->
                    val winner = winner
                    Log.d("winner positive", winner)
                    dialogPlayerActivity.winnerDialogText.text = "Congrats, ${winner} Won!"
                    val playerDAO = PlayerDAOSQLLiteImplementation(applicationContext)
                    playerDAO.addWinner(winner)
                    resetGame()

                })
                setNegativeButton("Next", DialogInterface.OnClickListener { dialog, id ->
                    val winner = winner

                    val playerDAO = PlayerDAOSQLLiteImplementation(applicationContext)
                    playerDAO.addWinner(winner)
                    Log.d("winner negative", winner)
                    resetGame()
                })
                setView(dialogPlayerActivity.root)
                create()
            }
        }
    }







}