package com.example.applicationform.activity

import UiData
import UiDataList
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.applicationform.OnResponseCompleted
import com.example.applicationform.R
import com.example.applicationform.ResponseData
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable

class MainActivity: AppCompatActivity(),OnResponseCompleted,SwipeRefreshLayout.OnRefreshListener {

    val array_text=ArrayList<String>()
    val array_edit=ArrayList<String>()
    val responseData: ResponseData = ResponseData()

    var array_s=ArrayList<Int>()
    var c=0
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        layout_main.setOnRefreshListener(this)
        if(checkNetwork())
        {
            progress_layout.visibility= View.VISIBLE
            tv_error.text="Data loading..."
            responseData.getUIdata(this)
        }
        else if(!checkNetwork())
        {
            progress_layout.visibility= View.VISIBLE
            progress.hide()
            tv_error.text="Please check your internet connection"
            progress.hide()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkNetwork():Boolean
    {
        val connManager=getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(connManager.activeNetwork==null)
        {
            return false
        }
        return true
    }

    fun getLogo_Headerdata(uidata: UiData)
    {
        if(!uidata.logo.equals(""))
        {
            val imageView = ImageView(this)
            Glide.with(this).load(uidata.logo.toString()).into(imageView)
            val params= LinearLayout.LayoutParams(800,150)
            params.setMargins(5,20,5,5)
            params.gravity= Gravity.CENTER
            imageView.layoutParams=params
            layout.addView(imageView)
        }

        if(!uidata.heading.equals(""))
        {
            val tv_header = TextView(this)
            val params= LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.setMargins(20,25,20,20)
            tv_header.setTypeface(tv_header.getTypeface(), Typeface.BOLD_ITALIC)
            tv_header.textSize = 20f
            tv_header.setTextColor(Color.parseColor("#fb6d4c"))
            tv_header.text = uidata.heading
            tv_header.layoutParams=params
            params.gravity= Gravity.CENTER
            // add TextView to Layout
            layout?.addView(tv_header)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun getFormData(uidata: List<UiDataList>?)
    {
        var tv_ui:TextView
        var editText:TextView
        var button:Button


        uidata?.let {

            for(i in it){

                if(i.uitype.equals("label"))
                {
                    tv_ui = TextView(this)
                    val params=
                        LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    params.setMargins(20,30,20,20)
                    tv_ui.setTypeface(tv_ui.getTypeface(), Typeface.BOLD_ITALIC)
                    array_text.add(i.value.toString())
                    tv_ui.textSize = 20f
                    tv_ui.text = i.value
                    tv_ui.layoutParams=params
                    // add TextView to Layout
                    layout?.addView(tv_ui)
                }

                else if(i.uitype.equals("edittext"))
                {
                    editText = EditText(this)
                    editText.setHint(i.hint)
                    editText.id=c
                    array_s.add(c)
                    editText.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    editText.setPadding(20, 20, 20, 20)
                    val params=
                        LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    params.setMargins(20,5,20,40)
                    if(i.key.equals("text_phone"))
                    {
                        editText.inputType = InputType.TYPE_CLASS_NUMBER
                        editText.filters = arrayOf(*editText.filters, InputFilter.LengthFilter(10))
                    }
                    editText.filters = arrayOf(*editText.filters, InputFilter.LengthFilter(15))
                    editText.background=resources.getDrawable(R.drawable.box_design2)
                    editText.layoutParams=params
                    // Add EditText to Layout
                    layout?.addView(editText)
                }
                else if(i.uitype.equals("button"))
                {
                    button = Button(this)
                    button.layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT
                        , ViewGroup.LayoutParams.WRAP_CONTENT)
                    button.textSize = 20f
                    val params= LinearLayout.LayoutParams(400, ViewGroup.LayoutParams.WRAP_CONTENT)
                    params.setMargins(20,80,20,20)
                    button.background=resources.getDrawable(R.drawable.box_design1)
                    // button.background=resources.getDrawable(android.R.color.black)
                    button.setTextColor(Color.WHITE)
                    params.gravity= Gravity.CENTER

                    button.layoutParams=params
                    button.text =i.value

                    // Add Button to Layout
                    layout?.addView(button)

                    button.setOnClickListener {

                        val intent= Intent(this,DetailsActivity::class.java)
                        for(s in array_s)
                        {
                            val edit=layout.findViewById<EditText>(s)
                            array_edit.add( edit?.text.toString())
                        }

                        val args=Bundle()
                        args.putSerializable("text",array_text as Serializable)
                        args.putSerializable("edit",array_edit as Serializable)
                        intent.putExtra("bundle",args)
                        startActivity(intent)
                        array_edit.clear()
                    }
                }
                c++
            }
        }
        progress_layout.visibility= View.GONE
        progress.hide()
        layout_main.isRefreshing=false
    }

    override fun onResponseCompleted(data: UiData?, status: Boolean) {

        if(status.equals(true))
        {
            progress.show()
            layout_main.isRefreshing=true
            data?.let {
                //Log.e("error----", it.toString())
                layout.removeAllViews()
                array_text.clear()
                array_edit.clear()
                getLogo_Headerdata(it)
                getFormData(it.uidata)

            }
        }
        else
        {
            progress_layout.visibility= View.GONE
            progress.hide()
            layout_main.isRefreshing=false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRefresh() {
        if(checkNetwork())
        {
            progress_layout.visibility= View.VISIBLE
            tv_error.text="Data loading..."
            responseData.getUIdata(this)
        }
        else
        {
            layout_main.isRefreshing=false
            progress_layout.visibility= View.VISIBLE
            progress.hide()
            tv_error.text="Please check your internet connection"
        }
    }
}