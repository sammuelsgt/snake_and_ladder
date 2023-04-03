package ph.stacktrek.novare.game.tuliaomiranda.snakeandladder

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val startButton : Button = view.findViewById(R.id.start_button)
        startButton.setOnClickListener {
            val intent = Intent(activity, PlayerActivity::class.java)
            startActivity(intent)

        }

        return view
    }
}
