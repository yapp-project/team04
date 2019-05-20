package yapp14th.co.kr.myplant.ui.main.tab2_statistic

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import buv.co.kr.base.BaseDialog
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.base_dialog.*
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.databinding.ActivityTemplateBinding
import yapp14th.co.kr.myplant.databinding.DialogStatisticBinding

class StatisticDialog(context: Context) : BaseDialog(context) {
    private lateinit var binding: DialogStatisticBinding

    override fun setInit(resId: Int, type: Int) {
        setContentView(resId)

        setChart(findViewById<PieChart>(R.id.chart_statistic))
        val btnClose = findViewById<ImageButton>(R.id.btn_close_dialog)
        btnClose.setOnClickListener(View.OnClickListener{
            this.dismiss()
        })
    }

    fun setChart(chartView: PieChart){

        chartView.setUsePercentValues(true)
        chartView.clearAnimation()
        chartView.description.isEnabled = false
        chartView.centerText = "가장 많은 감정은"
        chartView.legend.isEnabled = false

        chartView.isDrawHoleEnabled = true
        chartView.holeRadius = 95.toFloat()
        chartView.setHoleColor(Color.WHITE)
        val testList = ArrayList<PieEntry>()

        testList.add(PieEntry(10f))
        testList.add(PieEntry(10f))
        testList.add(PieEntry(10f))
        testList.add(PieEntry(10f))
        testList.add(PieEntry(10f))
        testList.add(PieEntry(10f))
        testList.add(PieEntry(10f))
        testList.add(PieEntry(10f))

        val dataSet = PieDataSet(testList, null)
        dataSet.setColors(intArrayOf(R.color.color373768, R.color.colorAccent), this.context)
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