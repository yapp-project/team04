package yapp14th.co.kr.myplant.ui.main.tab1_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_month.view.*
import org.threeten.bp.DayOfWeek
import yapp14th.co.kr.myplant.BR
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.base.BaseFragment
import yapp14th.co.kr.myplant.base.BaseRecyclerView
import yapp14th.co.kr.myplant.components.LinePagerIndicatorDecoration
import yapp14th.co.kr.myplant.databinding.FragmentHomeBinding
import yapp14th.co.kr.myplant.databinding.ItemMonthBinding
import yapp14th.co.kr.myplant.utils.attachSnapHelperWithListener
import yapp14th.co.kr.myplant.utils.getCurrentYear
import yapp14th.co.kr.myplant.utils.getMonthDay

class HomeFragment : BaseFragment(), OnSnapPositionChangeListener {
    // 선택 선언 2_1 (데이터 바인딩)
    private lateinit var binding: FragmentHomeBinding

    // 선택 선언 3_1 (ViewModel)
    val homeVM: HomeViewModel by lazy {
        // 선택 선언 3_2 (ViewModel 사용 시 정의)
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onSnapPositionChange(position: Int) {
        homeVM.month.set(position + 1)
    }

    // TODO 필수 선언 1 (기본 레이아웃 설정)
    override fun getLayoutRes() = R.layout.fragment_home

    // TODO 필수 선언 2 (데이터 바인딩 사용할지 말지 결정)
    override fun getIsUseDataBinding() = true

    // TODO 필수 선언 3 (onCreateView)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    // 선택 선언 2_2 (데이터바인딩 사용 시)
    override fun onDataBinding(inflater: LayoutInflater, container: ViewGroup): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.homeView = this
        binding.homeVM = homeVM
        return binding.root
    }

    // TODO 필수 선언 4 (onViewCreated)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ll = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        rl_calendar.layoutManager = ll

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rl_calendar)
        rl_calendar.attachSnapHelperWithListener(snapHelper, SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL, this)
    }

    // 선택 선언 5 (LiveData 사용 시)
    override fun subScribeUI() {
        super.subScribeUI()

        homeVM.years.observe(this, Observer {
            val adapter = ArrayAdapter(
                    this@HomeFragment.context,
                    R.layout.support_simple_spinner_dropdown_item,
                    homeVM.years.value
                            ?: listOf(getCurrentYear()))

            sp_year.adapter = adapter
        })

        homeVM.calendars.observe(this, Observer { calendars ->
            rl_calendar.addItemDecoration(LinePagerIndicatorDecoration(activity!!, false))

            var adapter = object : BaseRecyclerView.Adapter<Pair<Int, Int>, ItemMonthBinding>(
                    layoutResId = R.layout.item_month,
                    bindingVariableId = BR.icMonth) {

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ItemMonthBinding> {
                    return super.onCreateViewHolder(parent, viewType).apply {

                    }
                }

                override fun onBindViewHolder(holder: ViewHolder<ItemMonthBinding>, position: Int) {
                    super.onBindViewHolder(holder, position)

                    var year = calendars[position].first
                    var month = calendars[position].second

                    holder.itemView.cv_calendar.topbarVisible = false
                    holder.itemView.cv_calendar.state().edit()
                            .setFirstDayOfWeek(DayOfWeek.SUNDAY)
                            .setMinimumDate(CalendarDay.from(year, month, 1))
                            .setMaximumDate(CalendarDay.from(year, month, getMonthDay(year, month)))
                            .setCalendarDisplayMode(CalendarMode.MONTHS)
                            .commit()

                    holder.setIsRecyclable(false)
                }
            }

            adapter.replaceAll(calendars)
            rl_calendar.adapter = adapter
        })
    }

    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: HomeFragment? = null

        fun getInstance(): HomeFragment {
            if (INSTANCE == null) {
                INSTANCE = HomeFragment()
            }
            return INSTANCE!!
        }
    }
}