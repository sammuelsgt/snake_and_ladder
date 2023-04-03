package ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.databinding.PlayerCreateBinding
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.model.Player

class PlayerAdapter (private val context: Context,
                     private var playerList: ArrayList<Player>):
    RecyclerView.Adapter<PlayerAdapter.ViewHolder>(){

    fun deletePlayer(position: Int){
        playerList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addPlayer(players: Player){
        playerList.add(0,players)
        notifyItemInserted(0)
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerAdapter.ViewHolder {
        val playerBinding = PlayerCreateBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
    return ViewHolder(playerBinding)
    }

    override fun onBindViewHolder(holder: PlayerAdapter.ViewHolder, position: Int) {
        holder.bindItems(playerList[position])
    }

    override fun getItemCount(): Int = playerList.size

    inner class ViewHolder(private val playerBinding: PlayerCreateBinding):
        RecyclerView.ViewHolder(playerBinding.root){

        fun bindItems(players: Player){
            playerBinding.playerName.text = players.nickname
        }
    }



}