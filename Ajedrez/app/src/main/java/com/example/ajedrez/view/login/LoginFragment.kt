package com.example.ajedrez.view.login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.ajedrez.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private lateinit var usernameEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var loginBtn: Button
    private lateinit var loading: ProgressBar
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_login, container, false)
        usernameEt = view.findViewById(R.id.usernameET)
        passwordEt = view.findViewById(R.id.passwordET)
        loginBtn = view.findViewById(R.id.loginBTN)
        loading = view.findViewById(R.id.loading)

        usernameEt.afterTextChanged {
            loginDataChanged(
                usernameEt.text.toString(),
                passwordEt.text.toString()
            )
        }

        passwordEt.afterTextChanged {
            loginDataChanged(
                usernameEt.text.toString(),
                passwordEt.text.toString()
            )
        }

        loginBtn.setOnClickListener {
            login(
                usernameEt.text.toString(),
                passwordEt.text.toString()
            )
        }

        auth = Firebase.auth

        return view
    }

    private fun login(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: " + task.result)
                } else {
                    Log.d(TAG, "No such document")
                }
            } else {
                Log.d(TAG, "get failed with ", task.exception)
            }
        }
    }

    private fun loginDataChanged(username: String, password: String) {
        loginBtn.isEnabled = !(!isUserNameValid(username) || !isPasswordValid(password))
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (TextUtils.isEmpty(username)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    /**
     * Extension function to simplify setting an afterTextChanged action to EditText components.
     */
    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    //==============================================================================================
    // Methods
    //==============================================================================================

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }
}
