package ph.stacktrek.novare.game.tuliaomiranda.snakeandladder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.adapters.HistoryAdapter
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.dao.PlayerDAO
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.dao.PlayerDAOSQLLiteImplementation
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private lateinit var playerDAO: PlayerDAO


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerDAO = context?.let { PlayerDAOSQLLiteImplementation(it) }!!
        val winners = playerDAO.getWinners()

        adapter = HistoryAdapter(winners)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)

        loadWinners()

    }

    private fun loadWinners(){

        val winners = playerDAO.getWinners()

        adapter.addPlayer(winners)
    }


}