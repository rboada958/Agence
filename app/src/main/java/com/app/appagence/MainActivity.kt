package com.app.appagence

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.app.appagence.app.model.User
import com.app.appagence.databinding.ActivityMainBinding
import com.app.appagence.ui.login.LoginActivity
import com.app.appagence.ui.login.LoginViewModel
import com.app.appagence.utils.base.loadRect
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<LoginViewModel>()
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        startApp(navController)
        viewModel.getUserData()

        viewModel.userDataLive.observe(this) {
            it.getContentIfNotHandled()?.let { user ->
                getData(user)
                this.user = user
            }
        }

        logout()
    }

    private fun startApp(navController: NavController) {
        setSupportActionBar(binding.appBarMain.toolbar)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_profile,
                R.id.nav_product,
                R.id.nav_setting
            ),
            binding.drawerLayout
        )
        binding.navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun getData(user: User) {
        val headerView: View = binding.navView.getHeaderView(0)
        val navPicture = headerView.findViewById<View>(R.id.picture) as ImageView
        val navName = headerView.findViewById<View>(R.id.name) as TextView
        val navEmail = headerView.findViewById<View>(R.id.email) as TextView

        navPicture.loadRect(user.picture)
        navName.text = "${user.firstname} ${user.lastname}"
        navEmail.text = user.email
    }

    private fun logout() {
        binding.navView.menu.findItem(R.id.nav_logout).setOnMenuItemClickListener {
            when (user!!.tag) {
                "facebook" -> {
                    disconnectFromFacebook()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                "google" -> {
                    signOutGoogle()
                }
            }
            true
        }
    }

    private fun disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest(
                AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE,
                {
                    AccessToken.setCurrentAccessToken(null)
                    LoginManager.getInstance().logOut()
                }
            ).executeAsync()
        }
    }

    private fun signOutGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}