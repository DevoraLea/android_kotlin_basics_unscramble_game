import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.example.unscramblegame.MAX_WORDS
import com.example.unscramblegame.R
import com.example.unscramblegame.databinding.FragmentUncrambleBinding
import com.example.unscramblegame.ui.game.GameViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class UncrambleFragment : Fragment() {

    lateinit var binding: FragmentUncrambleBinding
   private val gameViewModel: GameViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding = FragmentUncrambleBinding.inflate(inflater, container, false)
        try{
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_uncramble,container,false)
        var view = binding.root
        }
        catch (ex:Exception){
            ex.printStackTrace()
            var msg = ex.message.toString()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gameViewModel = gameViewModel
        binding.maxNoOfWords = MAX_WORDS
        binding.lifecycleOwner = viewLifecycleOwner

        binding.buttonSkip.setOnClickListener {skipWord()}
        binding.buttonSubmit.setOnClickListener {submitWord()}






        /*
        gameViewModel.currentScrambledWord.observe(viewLifecycleOwner,{
            newWord ->
            binding.uncrambleWord.text = newWord
        })
        gameViewModel.score.observe(viewLifecycleOwner ,{
            newScore ->
            binding.score.text = "SCORE: ${gameViewModel.score.value}"
        })
        gameViewModel.countWords.observe(viewLifecycleOwner,{
            newCount ->
            binding.numberWords.text = "${gameViewModel.countWords.value} of $MAX_WORDS words"
        })*/
    }


    private fun skipWord() {
        if(gameViewModel.nextWord()){
            setErrorTextField(false)
        }
        else{
            showDialogScore()
        }
    }

    private fun submitWord() {
        val playerWord = binding.editTextTextPersonName.text.toString()
        if(gameViewModel.correctWord(playerWord)){
            setErrorTextField(false)

        if(!gameViewModel.nextWord()){
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
    /*
    private fun updateWordAndTexts() {
        binding.uncrambleWord.text = gameViewModel.currentScrambledWord.value
        binding.score.text = "SCORE: ${gameViewModel.score}"
        binding.numberWords.text = "${gameViewModel.countWords} of $MAX_WORDS words"
    }*/

    private fun showDialogScore() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Congratulations")
            .setMessage("you finish the game with ${gameViewModel.score.value} score")
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
        //updateWordAndTexts()
    }
    private fun exitGame(){
        activity?.finish()
    }
}