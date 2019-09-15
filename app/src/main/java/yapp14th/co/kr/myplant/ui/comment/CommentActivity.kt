package yapp14th.co.kr.myplant.ui.comment

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.item_comment.view.*
import yapp14th.co.kr.myplant.BR
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.base.BaseActivity
import yapp14th.co.kr.myplant.base.BaseRecyclerView
import yapp14th.co.kr.myplant.databinding.ActivityCommentBinding
import yapp14th.co.kr.myplant.databinding.ItemCommentBinding
import yapp14th.co.kr.myplant.ui.main.tab1_home.CDayVO
import yapp14th.co.kr.myplant.utils.SharedPreferenceUtil
import yapp14th.co.kr.myplant.utils.getCurrentRefinedMonth
import yapp14th.co.kr.myplant.utils.getCurrentYear

class CommentActivity : BaseActivity() {
    override fun getLayoutRes() = R.layout.activity_comment
    override fun getIsUseDataBinding() = true

    // 선택 선언 2_1 (데이터 바인딩)
    private var binding: ActivityCommentBinding? = null
    private lateinit var adapter: BaseRecyclerView.Adapter<CDayVO, ItemCommentBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.apply {
            commentVM.setInit(getIntExtra("year", getCurrentYear()), getIntExtra("month", getCurrentRefinedMonth()))
        } ?: let { finish() }

        btn_close.setOnClickListener { finish() }

        rv_comment.layoutManager = LinearLayoutManager(this@CommentActivity, RecyclerView.VERTICAL, false)
    }

    // 선택 선언 3_1 (ViewModel)
    val commentVM: CommentViewModel by lazy {
        // 선택 선언 3_2 (ViewModel 사용 시 정의)
        ViewModelProviders.of(this).get(CommentViewModel::class.java)
    }

    override fun onDataBinding() {
        super.onDataBinding()
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding?.let { binding ->
            binding.activity = this
            binding.commentVM = commentVM
        }
    }

    override fun subScribeUI() {
        super.subScribeUI()

        commentVM.comments.observe(this, Observer { comments ->
            adapter = object : BaseRecyclerView.Adapter<CDayVO, ItemCommentBinding>(
                    layoutResId = R.layout.item_comment,
                    bindingVariableId = BR.icComment) {

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ItemCommentBinding> {
                    return super.onCreateViewHolder(parent, viewType).apply {

                    }
                }

                override fun onBindViewHolder(holder: ViewHolder<ItemCommentBinding>, position: Int) {
                    super.onBindViewHolder(holder, position)

                    val emotionType = comments[position].emotionType
                    val emotionColor = Color.parseColor(SharedPreferenceUtil.getStringData("EMOTION_$emotionType"))

                    holder.itemView.iv_color.setColorFilter(emotionColor, PorterDuff.Mode.SRC)
                }
            }

            adapter.replaceAll(comments)
            rv_comment.adapter = adapter
        })
    }
}