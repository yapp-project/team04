package yapp14th.co.kr.myplant.ui.main.tab2_statistic

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_statistic.*
import kotlinx.android.synthetic.main.item_recycler_colors.view.*
import yapp14th.co.kr.myplant.BR
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.base.BaseFragment
import yapp14th.co.kr.myplant.base.BaseRecyclerView
import yapp14th.co.kr.myplant.components.SpacesItemDecoration
import yapp14th.co.kr.myplant.databinding.FragmentStatisticBinding
import yapp14th.co.kr.myplant.databinding.ItemRecyclerColorsBinding
import yapp14th.co.kr.myplant.ui.main.tab1_home.CalendarMonth
import yapp14th.co.kr.myplant.utils.SharedPreferenceUtil
import yapp14th.co.kr.myplant.utils.getCurrentYear

class StatisticFragment : BaseFragment() {

    private lateinit var binding: FragmentStatisticBinding
    private lateinit var statisticDialog: StatisticDialog

    private val itemAdapter: BaseRecyclerView.Adapter<CalendarMonth, ItemRecyclerColorsBinding> =
        object : BaseRecyclerView.Adapter<CalendarMonth, ItemRecyclerColorsBinding>(
            layoutResId = R.layout.item_recycler_colors,
            bindingVariableId = BR.viewItem
        ) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): ViewHolder<ItemRecyclerColorsBinding> {
                return super.onCreateViewHolder(parent, viewType).apply {

                }
            }

            override fun onBindViewHolder(
                holder: ViewHolder<ItemRecyclerColorsBinding>,
                position: Int
            ) {
                super.onBindViewHolder(holder, position)

                var emotions = statisticVM.emotions.value!!
                val month = emotions[position].month.toInt()
                var color = 0
                holder.itemView.item_month.text = month.toString()

                val array = IntArray(9)
                statisticVM.emotions.value!![position].dayList.forEach { cday ->
                    array[cday.emotionType.toInt()]++
                }

                val max = array.maxOrNull() ?: return
                Log.e("StaticticFragment", array.indexOf(max!!).toString())

                when (array.indexOf(max)) {
                    1 -> color =
                        Color.parseColor(SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_1))
                    2 -> color =
                        Color.parseColor(SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_2))
                    3 -> color =
                        Color.parseColor(SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_3))
                    4 -> color =
                        Color.parseColor(SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_4))
                    5 -> color =
                        Color.parseColor(SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_5))
                    6 -> color =
                        Color.parseColor(SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_6))
                    7 -> color =
                        Color.parseColor(SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_7))
                    8 -> color =
                        Color.parseColor(SharedPreferenceUtil.getStringData(SharedPreferenceUtil.EMOTION_8))
                }

                Log.e("Statictic", SharedPreferenceUtil.getStringData("EMOTION_6"))
                holder.itemView.cv_month_color.setColorFilter(color, PorterDuff.Mode.SRC)

                holder.itemView.setOnClickListener {
                    val array = IntArray(9)
                    emotions[position].dayList.forEach { cday ->
                        array[cday.emotionType.toInt()]++
                    }

                    openStatisticDialog(
                        0,
                        month.toString() + "월",
                        "",
                        R.layout.dialog_statistic,
                        array
                    )
                    Log.e("Statistic", emotions[position].dayList.size.toString())
                }
            }
        }

    private lateinit var spinnerAdapter: ArrayAdapter<Int>

    // TODO 필수 선언 1 (기본 레이아웃 설정)
    override fun getLayoutRes() = R.layout.fragment_statistic

    // TODO 필수 선언 2 (데이터 바인딩 사용할지 말지 결정)
    override fun getIsUseDataBinding() = true

    // TODO 필수 선언 3 (onCreateView)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private val statisticVM: StatisticViewModel by lazy {
        ViewModelProviders.of(this).get(StatisticViewModel::class.java)
    }

    // TODO 필수 선언 4 (onViewCreated)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinnerAdapter = ArrayAdapter(
            requireContext(), R.layout.support_simple_spinner_dropdown_item,
            statisticVM.years.value ?: listOf(getCurrentYear())
        )
        sp_year_statis.adapter = spinnerAdapter
        sp_year_statis.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                statisticVM.getEmotionsList(sp_year_statis.getItemAtPosition(position) as Int)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        rv_color.adapter = itemAdapter
        rv_color.addItemDecoration(SpacesItemDecoration(17))
        rv_color.layoutManager = GridLayoutManager(this.context, 3)
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
        layoutRes: Int, emotion: IntArray
    ) {
        // 초기 설정
        statisticDialog.setInit(layoutRes, type)
        statisticDialog.setList(emotion)
        statisticDialog.setTitle(title, subTitle)

        statisticDialog
        statisticDialog.setOkButtonListener {}
        statisticDialog.setCancelButtonListener {}
        statisticDialog.window?.attributes?.apply {
            width = WindowManager.LayoutParams.WRAP_CONTENT;
            height = WindowManager.LayoutParams.WRAP_CONTENT;
        }.let {
            statisticDialog.window?.attributes = it
        }

        statisticDialog.callFunction()
    }

    override fun subScribeUI() {
        super.subScribeUI()

        statisticDialog = StatisticDialog(requireContext())
        statisticVM.years.observe(this) {
            spinnerAdapter = ArrayAdapter(
                requireContext(), R.layout.support_simple_spinner_dropdown_item,
                statisticVM.years.value ?: listOf(getCurrentYear())
            )
            sp_year_statis.adapter = spinnerAdapter

            val spinnerPosition = spinnerAdapter.getPosition(statisticVM.currentYear.get())
            if (spinnerPosition != -1)
                sp_year_statis.setSelection(spinnerPosition)
            else
                sp_year_statis.setSelection(0)

        }

        statisticVM.emotions.observe(this, Observer { tempEmotions ->
            Log.i("sangwo-o.lee", "$tempEmotions")
            itemAdapter.replaceAll(tempEmotions)
        })
    }
}
