package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var benderImage:ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView
    lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //benderImage = findViewById(R.id.iv_bender) as ImageView
        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send

        val status = savedInstanceState?.getString("STATUS")?:Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION")?:Bender.Question.NAME.name
        val countFailAnswer = savedInstanceState?.getInt("COUNTSTATE")?:0
        benderObj = Bender(Bender.Status.valueOf(status),Bender.Question.valueOf(question), countFailAnswer)

        Log.d("M_MainActivity","onCreate $status $question")
        val (r,g,b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)

        textTxt.text= benderObj.askQuestion()
        sendBtn.setOnClickListener(this)
        messageEt.setImeOptions(EditorInfo.IME_ACTION_DONE)
        messageEt.setOnEditorActionListener(TextView.OnEditorActionListener{ _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent?.keyCode == KeyEvent.KEYCODE_ENTER) {
                onClick(sendBtn)
                return@OnEditorActionListener true
            }

            false
        })
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("M_MainActivity","onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.d("M_MainActivity","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("M_MainActivity","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_MainActivity","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("M_MainActivity","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_MainActivity","onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putString("STATUS",benderObj.status.name)
        outState?.putString("QUESTION",benderObj.question.name)
        outState?.putInt("COUNTSTATE",benderObj.countFailAnswer)
        Log.d("M_MainActivity","onSaveInstanceState ${benderObj.status.name} ${benderObj.question.name} ${benderObj.countFailAnswer}")
    }

    override fun onClick(v:View?){
    if(v?.id == R.id.iv_send) {
        val (phrase,color)= benderObj.listenAnswer(messageEt.text.toString())
        messageEt.setText("")
        val (r,g,b) = color
        benderImage.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)
        textTxt.text = phrase
      // Log.d("M_MainActivity","first ${isKeyboardOpen()}")
        this.hideKeyboard()

//        Run.after(10, {
//            if (false) {
//
//            } else {
//                Log.d("M_MainActivity","second ${isKeyboardOpen()}")
//            }
//        })

    }
    }

//    class Run {
//        companion object {
//            fun after(delay: Long, process: () -> Unit) {
//                Handler().postDelayed({
//                    process()
//                }, delay)
//            }
//        }
//    }
}
