package com.seven.pwddialog

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.seven.pwddialog.weight.PwdDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isHind = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pwdv.setTextWatcher(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        pwdv.setPwdOnClickListener {
            Log.e("MainActivity", "MainActivity Clicked")
//            if(!isHind){
//                pwdv.showOrHideKeyBoardView(true)
//                isHind = false
//            } else {
//                pwdv.showOrHideKeyBoardView(false)
//                isHind = true
//            }
        }

        button.setOnClickListener {
            PwdDialog(this)
                    .PwdBuilder(this)
                    .setmAmount("2000")
                    .setmMobileTicket("20000")
                    .setPwdClickListener(object :PwdDialog.PwdClickListener{
                        override fun ensureClickListener(dialog: PwdDialog?, v: View?) {

                        }

                        override fun cancelClickListener(dialog: PwdDialog?, v: View?) {
                            dialog!!.dismiss()
                        }
                    })
                    .show()
        }

    }

    suspend fun doSomething(){

    }
}
