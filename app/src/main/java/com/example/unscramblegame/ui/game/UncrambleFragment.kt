import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.unscramblegame.MAX_WORDS
import com.example.unscramblegame.R
import com.example.unscramblegame.databinding.FragmentUncrambleBinding
import com.example.unscramblegame.ui.game.GameViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class UncrambleFragment : Fragment() {

    private val gameViewModel: GameViewModel by viewModels()

    lateinit var binding: FragmentUncrambleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUncrambleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initGame()
        binding.buttonSkip.setOnClickListener {skipWord()}
        binding.buttonSubmit.setOnClickListener {submitWord()}
    }

    private fun initGame() {
        updateWordAndTexts()
    }

    private fun skipWord() {
        if(gameViewModel.nextWord()){
            updateWordAndTexts()
        }
        else{
            showDialogScore()
        }
    }

    private fun submitWord() {
        val playerWord = binding.editTextTextPersonName.text.toString()
        if(gameViewModel.correctWord(playerWord)){
            setErrorTextField(false)

        if(gameViewModel.nextWord()){
            updateWordAndTexts()
        }
        else{
            showDialogScore()
        }}
        else{
            setErrorTextField(true)
        }
    }
    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.inputText.isErrorEnabled = true
            //binding.editTextTextPersonName.error = "try again"
            binding.inputText.error = "one more"
        } else {
            binding.inputText.isErrorEnabled = false
            binding.editTextTextPersonName.text = null
        }
    }
    private fun updateWordAndTexts() {
        binding.uncrambleWord.text = gameViewModel.currentScrambledWord
        binding.score.text = "SCORE: ${gameViewModel.score}"
        binding.numberWords.text = "${gameViewModel.countWords} of $MAX_WORDS words"
    }

    private fun showDialogScore() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Congratulations")
            .setMessage("you finish the game with ${gameViewModel.score} score")
            .setIcon(R.drawable.words)
            .setCancelable(false)
            .setNegativeButton("exit"){_ ,_ ->
                exitGame()
            }
            .setPositiveButton("play again"){_ ,_ ->
                restartGame()
            }
            .show()
    }

    private fun restartGame() {
        gameViewModel.renitializedData()
        setErrorTextField(false)
        updateWordAndTexts()
    }
    private fun exitGame(){
        activity?.finish()
    }
}