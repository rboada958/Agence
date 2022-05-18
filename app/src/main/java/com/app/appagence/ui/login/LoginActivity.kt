package com.app.appagence.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.app.appagence.MainActivity
import com.app.appagence.app.model.User
import com.app.appagence.databinding.ActivityLoginBinding
import com.facebook.*
import com.facebook.login.BuildConfig
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    companion object {
        const val RC_SIGN_IN = 1001
    }

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()

    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        callbackManager = CallbackManager.Factory.create()

        binding.buttonLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonFacebook.setOnClickListener {
            binding.progressBar.isVisible = true
            LoginManager.getInstance()
                .logInWithReadPermissions(this, listOf("public_profile", "email"))
            facebookLogin()
        }

        binding.buttonGoogle.setOnClickListener {
            binding.progressBar.isVisible = true
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    private fun facebookLogin() {

        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    Toast.makeText(this@LoginActivity, "Login canceled", Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }

                override fun onError(error: FacebookException) {
                    Log.e("----->", error.message!!)
                    binding.progressBar.isVisible = false
                }

                override fun onSuccess(result: LoginResult) {
                    getUserProfile(result.accessToken, result.accessToken.userId)
                }
            })
    }

    fun getUserProfile(token: AccessToken?, userId: String?) {

        val parameters = Bundle()
        parameters.putString(
            "fields",
            "id, first_name, middle_name, last_name, name, picture, email"
        )
        GraphRequest(token,
            "/$userId/",
            parameters,
            HttpMethod.GET,
            { response ->
                val jsonObject = response.jsonObject

                if (BuildConfig.DEBUG) {
                    FacebookSdk.setIsDebugEnabled(true)
                    FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS)
                }

                val firstName = if (jsonObject!!.has("first_name")) {
                    val facebookFirstName = jsonObject.getString("first_name")
                    facebookFirstName
                } else {
                    "Not exists"
                }

                val lastName = if (jsonObject.has("last_name")) {
                    val facebookLastName = jsonObject.getString("last_name")
                    facebookLastName
                } else {
                    "Not exists"
                }

                val email = if (jsonObject.has("email")) {
                    val facebookEmail = jsonObject.getString("email")
                    facebookEmail
                } else {
                    "Not exists"
                }

                val id = if (jsonObject.has("id")) {
                    val facebookId = jsonObject.getString("id")
                    facebookId.toString()
                } else {
                    "Not exists"
                }

                val picture = "https://graph.facebook.com/$id/picture?type=large"

                loginViewModel.setUser(
                    User(
                        tag = "facebook",
                        id = id,
                        email = email,
                        firstname = firstName,
                        lastname = lastName,
                        picture = picture
                    )
                )
                binding.progressBar.isVisible = false

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }).executeAsync()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            loginViewModel.setUser(
                User(
                    tag = "google",
                    id = account.id,
                    email = account.email,
                    firstname = account.givenName,
                    lastname = account.familyName,
                    picture = account.photoUrl.toString()
                )
            )

            binding.progressBar.isVisible = false

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        } catch (e: ApiException) {
            Log.e("----->", "signInResult:failed code=" + e.statusCode)
            binding.progressBar.isVisible = false
        }
    }
}

