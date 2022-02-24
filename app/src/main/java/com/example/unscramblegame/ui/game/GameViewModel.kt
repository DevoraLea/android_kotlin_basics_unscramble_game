package com.example.unscramblegame.ui.game

import android.text.BoringLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unscramblegame.MAX_WORDS
import com.example.unscramblegame.SCORE_WORD
import com.example.unscramblegame.listWords

class GameViewModel: ViewModel() {

    private var mutableListWords:MutableList<String> = mutableListOf()
    private lateinit var currentWord:String

    private val _countWords = MutableLiveData(0)
    val countWords:LiveData<Int>
    get() = _countWords

    private var _score = MutableLiveData(0)
    val score:LiveData<Int>
    get() = _score

    private val _currentScrambledWord = MutableLiveData<String>()
    val currentScrambledWord:LiveData<String>
    get() = _currentScrambledWord

    init {
        nextWord()
    }
    fun renitializedData(){
        _countWords.value = 0
        _score.value = 0
        mutableListWords.clear()
        getNextWord()
    }
    fun getNextWord(){

        currentWord = listWords.random()
        var temp = currentWord.toCharArray()
        temp.shuffle()
        while(String(temp).equals(currentWord,false)){
            temp.shuffle()
        }
        if(mutableListWords.contains(currentWord)){
            getNextWord()
        }
        else{
            _countWords.value = _countWords.value?.inc()
            _currentScrambledWord.value = String(temp)
            mutableListWords.add(currentWord)
        }
    }
    fun nextWord():Boolean{
        return if(countWords.value!! < MAX_WORDS){
            getNextWord()
            true
        }
        else false
    }
    fun correctWord(word:String):Boolean{
        var wordd = word.removePrefix(" ")
        if(wordd.equals(currentWord,false)){
            increaseScore()
            return true
        }
        return false
    }
    fun increaseScore(){
        _score.value = _score.value?.plus(SCORE_WORD)
    }
}