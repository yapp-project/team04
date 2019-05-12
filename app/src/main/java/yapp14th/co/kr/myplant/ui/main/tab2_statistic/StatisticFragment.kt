package yapp14th.co.kr.myplant.ui.main.tab2_statistic

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_statistic.view.*
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.base.BaseFragment
import yapp14th.co.kr.myplant.databinding.FragmentStatisticBinding
import yapp14th.co.kr.myplant.databinding.FragmentTemplateBinding

class StatisticFragment : BaseFragment() {

    private lateinit var binding: FragmentStatisticBinding

    // TODO 필수 선언 1 (기본 레이아웃 설정)
    override fun getLayoutRes() = R.layout.fragment_statistic

    // TODO 필수 선언 2 (데이터 바인딩 사용할지 말지 결정)
    override fun getIsUseDataBinding() = true

    // TODO 필수 선언 3 (onCreateView)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private val statisticVM : StatisticViewModel by lazy {
        ViewModelProviders.of(this).get(StatisticViewModel::class.java)
    }

    // TODO 필수 선언 4 (onViewCreated)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setChart(view.chart_statistic)
        view.view_color_excited.setColor(Color.BLACK)
    }

    fun setChart(chartView: PieChart){

        chartView.setUsePercentValues(true)
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
        dataSet.setColors(intArrayOf(R.color.color373768, R.color.colorAccent), activity)
        dataSet.setDrawValues(false)
        val data = PieData(dataSet)

        chartView.data = data

    }


    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: StatisticFragment? = null

        fun getInstance(): StatisticFragment {
            if (INSTANCE == null) {
                INSTANCE = StatisticFragment()
            }
            return INSTANCE!!
        }
    }

    override fun onDataBinding(inflater: LayoutInflater, container: ViewGroup): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.statisticFragmentView = this
        binding.statisticFragmentVM = statisticVM
        return binding.root
    }
}
