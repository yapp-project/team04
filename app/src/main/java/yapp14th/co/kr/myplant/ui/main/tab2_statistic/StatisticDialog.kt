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

    private lateinit var vPleasure : EmotionView
    private lateinit var vHappy : EmotionView
    private lateinit var vExcited : EmotionView
    private lateinit var vPeace : EmotionView
    private lateinit var vSad : EmotionView
    private lateinit var vUnset : EmotionView
    private lateinit var vAnger : EmotionView
    private lateinit var vUser : EmotionView


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

        vPleasure = findViewById(R.id.view_color_pleasure)
        vPeace = findViewById(R.id.view_color_peace)
        vHappy = findViewById(R.id.view_color_happy)
        vExcited = findViewById(R.id.view_color_excited)
        vSad = findViewById(R.id.view_color_sad)
        vUnset = findViewById(R.id.view_color_unrest)
        vAnger = findViewById(R.id.view_color_anger)
        vUser = findViewById(R.id.view_color_user)

        vPleasure.setColor(Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_1")))
        vHappy.setColor(Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_2")))
        vExcited.setColor(Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_3")))
        vPeace.setColor(Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_4")))
        vSad.setColor(Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_5")))
        vUnset.setColor(Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_6")))
        vAnger.setColor(Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_7")))
        vUser.setColor(Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_8")))

        val tvUser = findViewById<TextView>(R.id.tv_user)
        tvUser.setText(SharedPreferenceUtil.getStringData("last"))

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
        chartView.centerText = "1순위감정"
        chartView.setCenterTextColor(context.resources.getColor(R.color.color373768))
        chartView.setCenterTextTypeface(Typeface.create("spoqa_han_sans_bold",Typeface.NORMAL))
        chartView.legend.isEnabled = false

        chartView.isDrawHoleEnabled = true
        chartView.holeRadius = 95.toFloat()
        chartView.setHoleColor(Color.WHITE)
        val emotionList = ArrayList<PieEntry>()

        for(i in cdays.indices){
            if(cdays[i] != 0) {
                emotionList.add(PieEntry(((cdays[i] / cdays.sum().toDouble()) * 100).toFloat()))

            } else{
                emotionList.add(PieEntry(0f))
            }
            if(cdays.sum() != 0) {
                when (i) {
                    1 -> pleasure.text = String.format("%.1f%%",(cdays[i] / cdays.sum().toDouble() * 100))
                    2 -> happy.text =  String.format("%.1f%%",(cdays[i] / cdays.sum().toDouble() * 100))
                    3 -> excited.text =  String.format("%.1f%%",(cdays[i] / cdays.sum().toDouble() * 100))
                    4 -> peace.text =  String.format("%.1f%%",(cdays[i] / cdays.sum().toDouble() * 100))
                    5 -> sad.text =  String.format("%.1f%%",(cdays[i] / cdays.sum().toDouble() * 100))
                    6 -> unset.text =  String.format("%.1f%%",(cdays[i] / cdays.sum().toDouble() * 100))
                    7 -> anger.text =  String.format("%.1f%%",(cdays[i] / cdays.sum().toDouble() * 100))
                    8 -> user.text =  String.format("%.1f%%",(cdays[i] / cdays.sum().toDouble() * 100))
                }
            }
        }

        if(cdays.sum() == 0)
            emotionList.add(PieEntry(100f))
        else{
            when(cdays.indexOf(cdays.maxOrNull() ?: return)){
                1 -> chartView.centerText = chartView.centerText.toString() + "\n기쁨"
                2 -> chartView.centerText = chartView.centerText.toString() + "\n행복"
                3 -> chartView.centerText = chartView.centerText.toString() + "\n신남"
                4 -> chartView.centerText = chartView.centerText.toString() + "\n평화"
                5 -> chartView.centerText = chartView.centerText.toString() + "\n슬픔"
                6 -> chartView.centerText = chartView.centerText.toString() + "\n불안"
                7 -> chartView.centerText = chartView.centerText.toString() + "\n분노"
                8 ->chartView.centerText = chartView.centerText.toString() + "\n"+SharedPreferenceUtil.getStringData(SharedPreferenceUtil.last)

            }
        }
        Log.e("StatisticDialog", emotionList.size.toString())


        val dataSet = PieDataSet(emotionList, null)

        val colors = intArrayOf(
                Color.TRANSPARENT,
                Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_1")),
                Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_2")),
                Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_3")),
                Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_4")),
                Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_5")),
                Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_6")),
                Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_7")),
                Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_8"))
        )

        dataSet.setColors(colors.toList())
        dataSet.setDrawValues(false)
        val data = PieData(dataSet)

        chartView.data = data
    }

    override fun callFunction() {
        this.setCancelable(true)
        this.show()
    }

}
