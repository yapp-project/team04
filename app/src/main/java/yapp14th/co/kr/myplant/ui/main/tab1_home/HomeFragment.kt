package yapp14th.co.kr.myplant.ui.main.tab1_home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import filaroid.co.kr.filaroid.components.OnSnapPositionChangeListener
import filaroid.co.kr.filaroid.components.SnapOnScrollListener
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
import yapp14th.co.kr.myplant.components.EventDecorator
import yapp14th.co.kr.myplant.ui.comment.CommentActivity

class HomeFragment : BaseFragment(), OnSnapPositionChangeListener {
    // 선택 선언 2_1 (데이터 바인딩)
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: BaseRecyclerView.Adapter<CalendarMonth, ItemMonthBinding>
    private var homeHandler = Handler()

    // 선택 선언 3_1 (ViewModel)
    val homeVM: HomeViewModel by lazy {
        // 선택 선언 3_2 (ViewModel 사용 시 정의)
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onSnapPositionChange(position: Int) {
        // homeVM.releasePrevPosition()
        // adapter.replaceItem(homeVM.getCurrentMonthEmotions(), homeVM.getCurrentMonthData())

        homeVM.currentMonth.set(position + 1)
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
        ll.scrollToPosition(homeVM.currentMonth.get() ?: 1 - 1)
        rl_calendar.layoutManager = ll

        val snapHelper = PagerSnapHelper()
        val scrollListener = SnapOnScrollListener(snapHelper, SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL, object : OnSnapPositionChangeListener {
            override fun onSnapPositionChange(position: Int) {
                homeVM.currentMonth.set(position + 1)
                // Log.d("HomeFragment : ", "currentMonth : ${position + 1}")
            }
        })
        snapHelper.attachToRecyclerView(rl_calendar)
        rl_calendar.addOnScrollListener(scrollListener)

        rl_calendar.attachSnapHelperWithListener(snapHelper, SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL, this)
        rl_calendar.setItemViewCacheSize(12)
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

        })

        homeVM.emotions.observe(this, Observer { emotions ->
            // view에서 할 내용이 없다면, 추후 ViewModel 단으로 옮김
            rl_calendar.addItemDecoration(LinePagerIndicatorDecoration(activity!!, false))

            adapter = object : BaseRecyclerView.Adapter<CalendarMonth, ItemMonthBinding>(
                    layoutResId = R.layout.item_month,
                    bindingVariableId = BR.icMonth) {

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ItemMonthBinding> {
                    return super.onCreateViewHolder(parent, viewType).apply {

                    }
                }

                override fun onBindViewHolder(holder: ViewHolder<ItemMonthBinding>, position: Int) {
                    super.onBindViewHolder(holder, position)

                    var emotion = emotions[position]

                    var year = emotion.year.toInt()
                    var month = emotion.month.toInt()
                    var maximumDay = getMonthDay(year, month)

                    holder.itemView.cv_calendar.topbarVisible = false
                    holder.itemView.cv_calendar.state().edit()
                            .setFirstDayOfWeek(DayOfWeek.SUNDAY)
                            .setMinimumDate(CalendarDay.from(year, month, 1))
                            .setMaximumDate(CalendarDay.from(year, month, maximumDay))
                            .setCalendarDisplayMode(CalendarMode.MONTHS)
                            .commit()

                    // emotionDecorator 을 둬야할듯 1~8
                    for (emotionType in 1..8) {
                        var targetEmotions = emotion.dayList.filter { cDayVO ->
                            cDayVO.emotionType == emotionType.toShort()
                        } as ArrayList<CDayVO>

                        val eventDecorator = EventDecorator(context!!, homeVM.getCalendarDays(targetEmotions), emotionType)
                        holder.itemView.cv_calendar.addDecorator(eventDecorator)
                    }

                    holder.itemView.setOnClickListener(null)

                    holder.itemView.btn_diary.setOnClickListener {
                        var intent = Intent(activity, CommentActivity::class.java)
                        intent.putExtra("year", year)
                        intent.putExtra("month", month)

                        activity?.startActivity(intent)
                    }

                    holder.setIsRecyclable(false)
                }
            }

            adapter.replaceAll(emotions)
            rl_calendar.adapter = adapter
        })

        homeVM.isFlipLive.observe(this, Observer { isFlip ->
            homeHandler.postDelayed({ homeVM.clearFlipAndFlop() }, 1000)
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