package ph.stacktrek.novare.game.tuliaomiranda.snakeandladder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.adapters.PlayerAdapter
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.dao.PlayerDAO
import ph.stacktrek.novare.game.tuliaomiranda.snakeandladder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private val homeFragment = HomeFragment()
    private val instructionFragment = InstructionFragment()
    private val historyFragment = HistoryFragment()

    companion object{
        const val FRAGMENT_KEY = "FragmentKey"
        const val HOME_FRAGMENT = 0
        const val INSTRUCTION_FRAGMENT = 1
        const val HISTORY_FRAGMENT = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = when(intent.getIntExtra(FRAGMENT_KEY, HOME_FRAGMENT)){
            HOME_FRAGMENT -> homeFragment
            INSTRUCTION_FRAGMENT -> instructionFragment
            HISTORY_FRAGMENT -> historyFragment
            else -> homeFragment
        }

        replaceFragment(fragment)



        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.home -> replaceFragment(homeFragment)
                R.id.instruction -> replaceFragment(instructionFragment)
                R.id.history -> replaceFragment(historyFragment)

                else -> {
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManger = supportFragmentManager
        val fragmentTransaction = fragmentManger.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()

        when(fragment){
            homeFragment -> binding.bottomNavigationView.menu.findItem(R.id.home).isChecked = true
            instructionFragment -> binding.bottomNavigationView.menu.findItem(R.id.instruction).isChecked = true
            historyFragment -> binding.bottomNavigationView.menu.findItem(R.id.history).isChecked = true
        }
    }
}