package com.example.unscramblegame.ui.game

import android.text.BoringLayout
import androidx.lifecycle.ViewModel
import com.example.unscramblegame.MAX_WORDS
import com.example.unscramblegame.SCORE_WORD
import com.example.unscramblegame.listWords

class GameViewModel: ViewModel() {

    private var mutableListWords:MutableList<String> = mutableListOf()
    private lateinit var currentWord:String

    private var _countWords = 0
    val countWords:Int
    get() = _countWords
    private var _score = 0
    val score:Int
    get() = _score
    private lateinit var _currentScrambledWord:String

    val currentScrambledWord:String
    get() = _currentScrambledWord

    init {
        nextWord()
    }
    fun renitializedData(){
        _countWords = 0
        _score = 0
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
            ++_countWords
            _currentScrambledWord = String(temp)
            mutableListWords.add(currentWord)
        }
    }
    fun nextWord():Boolean{
        return if(countWords < MAX_WORDS){
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
        _score += SCORE_WORD
    }
}