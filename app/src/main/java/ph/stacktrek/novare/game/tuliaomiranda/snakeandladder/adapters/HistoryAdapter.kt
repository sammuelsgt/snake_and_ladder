package ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.databinding.WinnerHistoryBinding

class HistoryAdapter (private var winners: ArrayList<String>):
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){




    fun addPlayer(winners: ArrayList<String>){

        this.winners = winners
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val binding = WinnerHistoryBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(winners[position])
    }


    override fun getItemCount(): Int = winners.size

    inner class ViewHolder(private val binding: WinnerHistoryBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bindItems(winners: String){
            binding.playerName.text = winners
        }
    }
}