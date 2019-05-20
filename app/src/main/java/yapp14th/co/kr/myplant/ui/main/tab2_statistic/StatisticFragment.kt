package yapp14th.co.kr.myplant.ui.main.tab2_statistic

import android.app.SearchManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_statistic.*
import kotlinx.android.synthetic.main.fragment_statistic.view.*
import kotlinx.android.synthetic.main.item_recycler_colors.view.*
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.base.BaseFragment
import yapp14th.co.kr.myplant.base.BaseRecyclerView
import yapp14th.co.kr.myplant.components.LinePagerIndicatorDecoration
import yapp14th.co.kr.myplant.components.SpacesItemDecoration
import yapp14th.co.kr.myplant.databinding.FragmentStatisticBinding
import yapp14th.co.kr.myplant.databinding.FragmentTemplateBinding
import yapp14th.co.kr.myplant.databinding.ItemRecyclerColorsBinding
import yapp14th.co.kr.myplant.ui.main.tab1_home.CalendarMonth
import yapp14th.co.kr.myplant.utils.getCurrentYear

class StatisticFragment : BaseFragment() {

    private lateinit var binding: FragmentStatisticBinding
    private lateinit var statisticDialog : StatisticDialog
    private lateinit var adapter: BaseRecyclerView.Adapter<CalendarMonth, ItemRecyclerColorsBinding>


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
        binding.stView = this
        binding.stVm = statisticVM
        return binding.root
    }


    fun openStatisticDialog(
            type: Int,
            title: String,
            subTitle: String,
            layoutRes: Int) {
        // 초기 설정
        statisticDialog.setInit(layoutRes, type)
        statisticDialog.setTitle(title, subTitle)

        statisticDialog.setOkButtonListener(View.OnClickListener {})
        statisticDialog.setCancelButtonListener(View.OnClickListener {})
        val params = statisticDialog.window.attributes;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        statisticDialog.window.setAttributes(params)

        statisticDialog.callFunction()
    }

    override fun subScribeUI() {
        super.subScribeUI()

        statisticDialog = StatisticDialog(this.context!!)
        statisticVM.years.observe(this, Observer {
            val adapter = ArrayAdapter(
                    this.context, R.layout.support_simple_spinner_dropdown_item,
                    statisticVM.years.value ?: listOf(getCurrentYear())
            )

            sp_year_statis.adapter = adapter
        })

        statisticVM.emotions.observe(this, Observer { emotions ->
            rv_color.addItemDecoration(SpacesItemDecoration(15))

            adapter = object : BaseRecyclerView.Adapter<CalendarMonth, ItemRecyclerColorsBinding>(
                    layoutResId = R.layout.item_recycler_colors,
                    bindingVariableId = null){
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ItemRecyclerColorsBinding> {
                    return super.onCreateViewHolder(parent, viewType).apply {

                    }
                }

                override fun onBindViewHolder(holder: ViewHolder<ItemRecyclerColorsBinding>, position: Int) {
                    super.onBindViewHolder(holder, position)

                    var month = emotions[position].month.toInt()
                    var color = Color.parseColor("#373768")
                    holder.itemView.cv_month_color.setColorFilter(color, PorterDuff.Mode.SRC)
                    holder.itemView.item_month.text = month.toString()
                    Log.e("Statistic", month.toString())
                    holder.itemView.setOnClickListener(View.OnClickListener {
                        openStatisticDialog(0, month.toString()+"월", "", R.layout.dialog_statistic )
                        Log.e("Statistic", position.toString())
                    })


                }
            }
            adapter.replaceAll(emotions)
            rv_color.adapter = adapter
            rv_color.layoutManager = GridLayoutManager(this.context, 3)

        })
    }
}
