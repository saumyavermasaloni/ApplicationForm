package com.example.applicationform.activity

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.example.applicationform.R
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.activity_details)

        val arg: Bundle? = intent.getBundleExtra("bundle")
        val text_data= arg?.getSerializable("text") as ArrayList<String>


       for(i in text_data) {
            if(!(i.equals(null)) && (!(i.equals(""))) && (!(i.equals("null"))))
                {
                    createTextData(i+":")
                }

        }

        val edit_data= arg.getSerializable("edit") as ArrayList<String>

        for(i in edit_data) {
            if(!(i.equals(null)) && (!(i.equals("null"))))
            {
                createEditTextData(i)
            }
        }
    }

    fun createTextData(i: String)
    {
        val tv_ui = TextView(this)
        val params= LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(20,20,20,20)
        tv_ui.setTypeface(tv_ui.getTypeface(), Typeface.BOLD_ITALIC)
        tv_ui.textSize = 20f
        tv_ui.layoutParams=params
        tv_ui.text = i
        // add TextView to Layout
        text_layout?.addView(tv_ui)
    }

    fun createEditTextData(i: String)
    {
        val tv_ui = TextView(this)
        val params= LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(20,20,20,20)
        tv_ui.setTypeface(tv_ui.getTypeface(), Typeface.BOLD_ITALIC)
        tv_ui.textSize = 20f
        tv_ui.layoutParams=params
        if(!(i.equals(null)) && (!(i.equals("null"))))
        {
            tv_ui.text = i
        }
        // add TextView to Layout
        edit_layout?.addView(tv_ui)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}