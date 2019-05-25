package yapp14th.co.kr.myplant.ui.main.tab2_statistic

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import buv.co.kr.base.BaseDialog
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.ScatterDataSet
import kotlinx.android.synthetic.main.base_dialog.*
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.databinding.ActivityTemplateBinding
import yapp14th.co.kr.myplant.databinding.DialogStatisticBinding
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDayVO
import yapp14th.co.kr.myplant.utils.SharedPreferenceUtil

class StatisticDialog(context: Context) : BaseDialog(context) {
    private lateinit var pleasure : TextView
    private lateinit var happy : TextView
    private lateinit var excited : TextView
    private lateinit var peace : TextView
    private lateinit var sad : TextView
    private lateinit var unset : TextView
    private lateinit var anger : TextView
    private lateinit var user : TextView


    override fun setInit(resId: Int, type: Int) {
        setContentView(resId)

        pleasure = findViewById(R.id.tv_percent_pleasure)
        happy = findViewById(R.id.tv_percent_happy)
        excited = findViewById(R.id.tv_percent_excited)
        peace = findViewById(R.id.tv_percent_peace)
        sad = findViewById(R.id.tv_percent_sad)
        unset = findViewById(R.id.tv_percent_unrest)
        anger = findViewById(R.id.tv_percent_anger)
        user = findViewById(R.id.tv_percent_user)


        val btnClose = findViewById<ImageButton>(R.id.btn_close_dialog)
        btnClose.setOnClickListener(View.OnClickListener{
            this.dismiss()
        })
    }

    fun setList(cdays : IntArray){

        setChart(findViewById<PieChart>(R.id.chart_statistic), cdays)
    }

    fun setChart(chartView: PieChart, cdays : IntArray){

        chartView.setUsePercentValues(true)
        chartView.clearAnimation()
        chartView.description.isEnabled = false
        chartView.centerText = "1순위감정\n평화"
        chartView.setCenterTextColor(context.resources.getColor(R.color.color373768))
        chartView.setCenterTextTypeface(Typeface.create("spoqa_han_sans_bold",Typeface.BOLD))
        chartView.legend.isEnabled = false

        chartView.isDrawHoleEnabled = true
        chartView.holeRadius = 95.toFloat()
        chartView.setHoleColor(Color.WHITE)
        val emotionList = ArrayList<PieEntry>()

        for(i in cdays.indices){
            if(cdays[i] != 0) {
                emotionList.add(PieEntry((cdays[i] / cdays.sum() * 100).toFloat()))
                Log.e("StatisticDialog",(cdays[i] / cdays.sum() * 100).toString() )
            }
            when(i){
                1 -> pleasure.text = (cdays[i] / cdays.sum() * 100).toString()+"%"
                2 -> happy.text = (cdays[i] / cdays.sum() * 100).toString()+"%"
                3 -> excited.text = (cdays[i] / cdays.sum() * 100).toString()+"%"
                4 -> peace.text = (cdays[i] / cdays.sum() * 100).toString()+"%"
                5 -> sad.text = (cdays[i] / cdays.sum() * 100).toString()+"%"
                6 -> unset.text = (cdays[i] / cdays.sum() * 100).toString()+"%"
                7 -> anger.text = (cdays[i] / cdays.sum() * 100).toString()+"%"
                8 -> user.text = (cdays[i] / cdays.sum() * 100).toString()+"%"
            }
        }

        if(emotionList.size == 0)
            emotionList.add(PieEntry(100f))

        val dataSet = PieDataSet(emotionList, null)

        val colors = intArrayOf(
                R.color.color373768// Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_1")),
                // Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_2")),
                // Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_3")),
                // Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_4")),
                // Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_5")),
               // Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_6")),
                // Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_7")),
                // Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_8"))
        )



        dataSet.setColors(colors, this.context)
        dataSet.setDrawValues(false)
        val data = PieData(dataSet)

//        var color = Color.parseColor("#67894568")
//        img_color.setColorFilter(color, PorterDuff.Mode.SRC)

        chartView.data = data

    }

    override fun callFunction() {
        this.setCancelable(true)
        this.show()
    }

}