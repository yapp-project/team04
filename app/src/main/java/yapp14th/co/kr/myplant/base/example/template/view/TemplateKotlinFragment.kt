package yapp14th.co.kr.myplant.base.example.template.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import buv.co.kr.base.BaseDialog
import yapp14th.co.kr.myplant.MyApplication
import yapp14th.co.kr.myplant.R
import yapp14th.co.kr.myplant.base.BaseFragment
import yapp14th.co.kr.myplant.base.example.template.viewModel.TemplateFragemntViewModel
import yapp14th.co.kr.myplant.databinding.FragmentTemplateKotlinBinding

// TODO 필수 선언 1. 기본 레이아웃 설정
// TODO 필수 선언 2. 데이터 바인딩 사용할지 말지 결정
// TODO 필수 선언 3. onCreateView
// TODO 필수 선언 4. onViewCreated

// 선택 선언 1. Fragment 를 싱글턴으로 사용할 시
// 선택 선언 2. 데이터 바인딩
// 선택 선언 3. ViewModel
// 선택 선언 4. 다이얼로그 사용 시
// 선택 선언 5. LiveData 사용 시

class TemplateKotlinFragment : BaseFragment() {

    // 선택 선언 2_1 (데이터 바인딩)
    private lateinit var binding: FragmentTemplateKotlinBinding

    // 선택 선언 3_1 (ViewModel)
    val templateKotlinVM: TemplateFragemntViewModel by lazy {
        // 선택 선언 3_2 (ViewModel 사용 시 정의)
        ViewModelProviders.of(this).get(TemplateFragemntViewModel::class.java)
    }

    // TODO 필수 선언 1 (기본 레이아웃 설정)
    override fun getLayoutRes() = R.layout.fragment_template_kotlin

    // TODO 필수 선언 2 (데이터 바인딩 사용할지 말지 결정)
    override fun getIsUseDataBinding() = true

    // TODO 필수 선언 3 (onCreateView)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    // 선택 선언 2_2 (데이터바인딩 사용 시)
    override fun onDataBinding(inflater: LayoutInflater, container: ViewGroup): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.templateFragmentKotlinView = this
        binding.templateFragmentKotlinVM = templateKotlinVM
        return binding.root
    }

    // TODO 필수 선언 4 (onViewCreated)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 선택 선언 4_1 (다이얼로그 사용 시)
        dialog = BaseDialog(activity!!)
        openDialog(MyApplication.DIALOG_OK, "제목", "부제목", R.layout.base_dialog,
                { dialog.dismiss() },
                { dialog.dismiss() })
    }

    // 선택 선언 5 (LiveData 사용 시)
    override fun subScribeUI() {
        super.subScribeUI()
    }

    companion object {
        // 선택 선언 1 (Fragment를 싱글턴으로 사용 시)
        private var INSTANCE: TemplateKotlinFragment? = null

        fun getInstance(): TemplateKotlinFragment {
            if (INSTANCE == null) {
                INSTANCE = TemplateKotlinFragment()
            }
            return INSTANCE!!
        }
    }
}
