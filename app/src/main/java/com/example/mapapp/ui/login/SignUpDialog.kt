package com.example.mapapp.ui.login

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.mapapp.R
import com.example.mapapp.util.InputUtil.isEmail
import com.example.mapapp.util.InputUtil.isValidPW
import com.example.mapapp.util.InputUtil.setErrorText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.signup_fragment_dialog.view.*


class SignUpDialog(
    private val clickListener: SignUpClickListener,
    private val viewModel: LoginViewModel
) : DialogFragment() {

    interface SignUpClickListener {
        fun successSignUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomFullDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.signup_fragment_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.signUpResponseData.observe(this, Observer {task ->
            if (task.isSuccessful) {
                clickListener.successSignUp()
                dismiss()
            } else {
                Toast.makeText(context, "Authentication failed.\n${task.exception.toString()}",
                    Toast.LENGTH_SHORT).show()
            }
        })
        setupClickListeners(view)


    }

    override fun onStart() {
        super.onStart()

        val window: Window = dialog!!.window!!

        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val params: WindowManager.LayoutParams = window.attributes
        // 화면에 가득 차도록
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT

        // 열기&닫기 시 애니메이션 설정
        params.windowAnimations = R.style.AnimationPopupStyle
        window.attributes = params

        // UI 하단 정렬
        window.setGravity(Gravity.BOTTOM)
    }




    private fun setupClickListeners(view: View) {
        view.btn_close.setOnClickListener {
            hideSoftKeyBoard()
            dismiss()
        }
        view.btn_sign_in.setOnClickListener {
            hideSoftKeyBoard()

            if(checkValidInput(view)){
                viewModel.startSignUp(view.text_email.text.toString(), view.text_pw.text.toString())
            }


        }
    }


    private fun checkValidInput(v: View) : Boolean{

        var check = true

        val email = v.text_email.text.toString()
        val name = v.text_name.text.toString()
        val pw = v.text_pw.text.toString()
        val pw_check = v.text_pw_check.text.toString()


        check = check && setErrorText(v.signup_email_input, email.isEmpty(), "이메일 주소를 입력해주세요.")
        check = check && setErrorText(v.signup_email_input, !isEmail(email), "이메일 형식이 아닙니다.")
        check = check && setErrorText(v.signup_name_input, name.isEmpty(), "이름을 입력해주세요.")
        check = check && setErrorText(v.signup_pw_input, pw.isEmpty(), "비밀번호를 입력해주세요.")
        check = check && setErrorText(v.signup_pw_input, !isValidPW(pw), "최소 6자리의 비밀번호를 입력해주세요.")
        check = check && setErrorText(v.signup_pw_check_input, pw_check.isEmpty(), "비밀번호 확인을 입력해주세요.")

       if(pw.isNotEmpty() && pw_check.isNotEmpty()){
           check = check && setErrorText(v.signup_pw_check_input, pw != pw_check,
               "비밀번호와 비밀번호 확인이 다릅니다.")
       }


        return check
    }



    private fun hideSoftKeyBoard() {
        activity!!.window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}