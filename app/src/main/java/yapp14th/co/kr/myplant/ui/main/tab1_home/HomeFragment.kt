package yapp14th.co.kr.myplant.ui.main.tab1_home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import yapp14th.co.kr.myplant.components.EventDecorator
import yapp14th.co.kr.myplant.components.LinePagerIndicatorDecoration
import yapp14th.co.kr.myplant.databinding.FragmentHomeBinding
import yapp14th.co.kr.myplant.databinding.ItemMonthBinding
import yapp14th.co.kr.myplant.ui.comment.CommentActivity
import yapp14th.co.kr.myplant.ui.insert.InsertActivity
import yapp14th.co.kr.myplant.utils.*
import java.io.File
import java.util.*

class HomeFragment : BaseFragment(), OnSnapPositionChangeListener {
    // 선택 선언 2_1 (데이터 바인딩)
    private lateinit var binding: FragmentHomeBinding
    private val itemAdapter: BaseRecyclerView.Adapter<CalendarMonth, ItemMonthBinding> =
        object : BaseRecyclerView.Adapter<CalendarMonth, ItemMonthBinding>(
            layoutResId = R.layout.item_month,
            bindingVariableId = BR.icMonth
        ) {

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): ViewHolder<ItemMonthBinding> {
                return super.onCreateViewHolder(parent, viewType).apply {

                }
            }

            override fun onBindViewHolder(
                holder: ViewHolder<ItemMonthBinding>,
                position: Int
            ) {
                super.onBindViewHolder(holder, position)

                var emotion = homeVM.emotions.value!![position]

                var year = emotion.year.toInt()
                var month = emotion.month.toInt()
                var maximumDay = getMonthDay(year, month)

                Log.e("HomeFrag", month.toString())

                holder.itemView.cv_calendar.topbarVisible = false
                holder.itemView.cv_calendar.state().edit()
                    .setFirstDayOfWeek(DayOfWeek.SUNDAY)
                    .setMinimumDate(CalendarDay.from(year, month, 1))
                    .setMaximumDate(CalendarDay.from(year, month, maximumDay))
                    .setCalendarDisplayMode(CalendarMode.MONTHS)
                    .commit()

                holder.itemView.cv_calendar.setOnDateChangedListener { widget, date, selected ->
                    val clickDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, date.year)
                        set(Calendar.MONTH, date.month)
                        set(Calendar.DATE, date.day - 1)
                    }

                    val currentDate = Calendar.getInstance().apply {
                        set(Calendar.MONTH, this.get(Calendar.MONTH) + 1)
                    }

                    val isPast = (clickDate.timeInMillis - currentDate.timeInMillis < 0)
                    Log.d("isPast", "$isPast")

                    if (isPast && selected) {
                        // Toast.makeText(activity, "클릭 할꺼야 안할꺼야 ${date.year} ${date.month} ${date.day}", Toast.LENGTH_SHORT).show()

                        var value = getTargetDate(date.year, date.month, date.day)
                        var intent = Intent(activity, InsertActivity::class.java)

                        intent.putExtra("year", date.year)
                        intent.putExtra("month", date.month)
                        intent.putExtra("day", date.day)
                        intent.putExtra(
                            "emotionType",
                            if (value.size == 0) 0 else value[0].emotionType.toInt()
                        )
                        intent.putExtra(
                            "comment",
                            if (value.size == 0) "" else value[0].comment
                        )

                        startActivityForResult(intent, REQ_GO_TO_INSERT)
                    } else {
                        Toast.makeText(
                            activity,
                            "미래에 대한 감정을 등록할 수 없습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                // emotionDecorator 을 둬야할듯 1~8
                holder.itemView.cv_calendar.removeDecorators()
                Log.d("cv_calendar : ", "removeDecorators() finished")
                for (emotionType in 1..8) {
                    var targetEmotions = emotion.dayList.filter { cDayVO ->
                        cDayVO.emotionType == emotionType.toShort()
                    } as ArrayList<CDayVO>

                    val eventDecorator = EventDecorator(
                        context!!,
                        homeVM.getCalendarDays(targetEmotions),
                        emotionType
                    )
                    holder.itemView.cv_calendar.addDecorator(eventDecorator)
                }
                holder.itemView.cv_calendar.invalidateDecorators()

                holder.itemView.setOnClickListener(null)

                holder.itemView.btn_diary.setOnClickListener {
                    var intent = Intent(activity, CommentActivity::class.java)
                    intent.putExtra("year", year)
                    intent.putExtra("month", month)

                    activity?.startActivity(intent)
                }

                holder.itemView.btn_download.setOnClickListener {
                    if (ContextCompat.checkSelfPermission(
                            activity!!,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            activity!!,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            113
                        )
                    } else {
                        // 1. 갤러리 해당 경로에 파일 생성
                        val tempFilePath =
                            activity!!.getExternalFilesDir(null)?.path + "/" + "${year}_${month}_calendar_photo.jpg"
                        val tempFile = File(tempFilePath)

                        // 2. 테마색이 반영된 비트맵 생성
                        val bitmapPhoto = BitmapFactory.decodeResource(
                            resources,
                            homeVM.getBiggestEmotionImage(month - 1)
                        )
                        val copyBitmap: Bitmap =
                            bitmapPhoto.copy(Bitmap.Config.ARGB_8888, true)

                        var canvas = Canvas(copyBitmap)
                        canvas.drawColor(homeVM.getSecondEmotionFilter(month - 1))
                        canvas.drawBitmap(copyBitmap, 0F, 0F, null)

                        // 3. 만들어진 비트맵을 파일과 매칭
                        var newFile = getFile(tempFile, copyBitmap)

                        // 4. 갤러리 경로 존재 확인
                        val path =
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath + "/bora"
                        val file = File(path)

                        // Log.d("Directory Pictures 존재? ", File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath).exists().toString())
                        if (!file.exists()) {
                            file.mkdirs()
                            Log.d("갤러리 저장 ", "디렉토리 생성완료")
                        }

                        val galleryFilePath =
                            "$path/${year}_${month}_calendar_photo_${System.currentTimeMillis()}.jpg"
                        val galleryFile = File(galleryFilePath)
                        Log.d("galleryAddPic.newFile", galleryFilePath)

                        copyFile(tempFile, galleryFile)

                        activity?.sendBroadcast(
                            Intent(
                                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.parse("file://$galleryFilePath")
                            )
                        )
                        activity?.sendBroadcast(
                            Intent(
                                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.fromFile(newFile)
                            )
                        )

                        // 5. 갤러리로 copy 한 후 내장되어있는 파일은 삭제한다.
                        tempFile.delete()

                        Toast.makeText(activity, "성공적으로 갤러리에 저장되었습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                // holder.itemView.img_picture.setImageResource(homeVM.getBiggestEmotionImage(month - 1))
                // holder.itemView.img_filter.setBackgroundColor(homeVM.getSecondEmotionFilter(month - 1))

                holder.setIsRecyclable(false)
            }
        }

    private var homeHandler = Handler(Looper.getMainLooper())
    private var initSetting: Boolean = true
    private var fromInsert: Boolean = false

    // 선택 선언 3_1 (ViewModel)
    val homeVM: HomeViewModel by lazy {
        // 선택 선언 3_2 (ViewModel 사용 시 정의)
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onSnapPositionChange(position: Int) {
        // homeVM.releasePrevPosition()
        // adapter.replaceItem(homeVM.getCurrentMonthEmotions(), homeVM.getCurrentMonthData())

        // homeVM.currentMonth.set(position + 1)
    }

    // TODO 필수 선언 1 (기본 레이아웃 설정)
    override fun getLayoutRes() = R.layout.fragment_home

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
        initSetting = true
        rl_calendar.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rl_calendar)

        val scrollListener = SnapOnScrollListener(
            snapHelper,
            SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
            object : OnSnapPositionChangeListener {
                override fun onSnapPositionChange(position: Int) {
                    // homeVM.currentMonth.set(position + 1)
                    // Log.d("HomeFragment : ", "currentMonth : ${position + 1}")
                }
            })
        rl_calendar.addOnScrollListener(scrollListener)

        rl_calendar.attachSnapHelperWithListener(
            snapHelper,
            SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
            this
        )
        rl_calendar.setItemViewCacheSize(12)
        rl_calendar.addItemDecoration(LinePagerIndicatorDecoration(requireActivity(), false))

        oneSecondAnimation(tv_message)

        sp_year.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                homeVM.year.value = homeVM.years.value?.get(position) ?: getCurrentYear()

                homeVM.getEmotionsList()
            }
        }
    }

    // 선택 선언 5 (LiveData 사용 시)
    override fun subScribeUI() {
        super.subScribeUI()

        homeVM.years.observe(this, Observer { years ->
            val yearSpinnerAdapter = ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                years ?: listOf(getCurrentYear())
            )

            sp_year.adapter = yearSpinnerAdapter
            sp_year.setSelection(years.lastIndex)
        })

        // homeVM.calendars.observe(this, Observer { calendars ->
        //
        // })

        homeVM.emotions.observe(this) { emotions ->
            // view에서 할 내용이 없다면, 추후 ViewModel 단으로 옮김
            if (initSetting) {
                rl_calendar.adapter = itemAdapter
                initSetting = false
            }

            itemAdapter.replaceAll(emotions)

            if (fromInsert) {
                fromInsert = false
            } else if (homeVM.year.value == getCurrentYear()) {
                homeVM.month.set(getCurrentMonth())
                rl_calendar.smoothScrollToPosition(homeVM.month.get()!!)
            } else {
                homeVM.month.set(1)
                rl_calendar.smoothScrollToPosition(0)
            }
        }

        homeVM.isFlipLive.observe(this) {
            homeHandler.postDelayed({ homeVM.clearFlipAndFlop() }, 1000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_GO_TO_INSERT && resultCode == Activity.RESULT_OK) {
            fromInsert = true
            homeVM.getEmotionsList()

            sp_year.setSelection(
                homeVM.years.value?.indexOf(homeVM.year.value ?: getCurrentYear()) ?: 0
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        homeHandler.removeCallbacksAndMessages(null)
    }

    private fun oneSecondAnimation(targetView: View) {
        homeHandler.postDelayed({
            targetView.visibility = View.VISIBLE
            val dropDownAnim = AnimationUtils.loadAnimation(activity, R.anim.anim_drop_down)
            targetView.startAnimation(dropDownAnim)

            homeHandler.postDelayed({
                val riseUpAnim = AnimationUtils.loadAnimation(activity, R.anim.anim_rise_up)
                targetView.startAnimation(riseUpAnim)
                targetView.visibility = View.GONE
            }, 2000)
        }, 1000)
    }

    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: HomeFragment? = null
        var REQ_GO_TO_INSERT = 1002

        fun getInstance(): HomeFragment {
            if (INSTANCE == null) {
                INSTANCE = HomeFragment()
            }
            return INSTANCE!!
        }
    }
}
