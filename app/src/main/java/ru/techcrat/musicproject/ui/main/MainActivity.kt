package ru.techcrat.musicproject.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.techcrat.musicproject.R
import ru.techcrat.musicproject.ui.main.adapters.SectionsPagerAdapter


class MainActivity : AppCompatActivity() {

    private val vm by viewModel<MainViewModule>()
    private val PERMISSION_REQUEST_CODE: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                supportFragmentManager
            )
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        )
        requestToken()
    }

    override fun onStart() {
        super.onStart()
        SpotifyService.connect(this) {
            onResume()
        }
    }


    fun requestToken() {
        val request =
            getAuthenticationRequest(AuthorizationResponse.Type.TOKEN)
        AuthorizationClient.openLoginActivity(this, MainActivity.AUTH_TOKEN_REQUEST_CODE, request)
    }

    private fun getAuthenticationRequest(type: AuthorizationResponse.Type): AuthorizationRequest? {
        return AuthorizationRequest.Builder(
            CLIENT_ID,
            type,
            getRedirectUri()?.toString()
        )
            .setShowDialog(false)
            .setScopes(
                arrayOf(
                    "user-read-email",
                    "user-library-read",
                    "user-read-private",
                    "streaming"
                )
            )
            .setCampaign("your-campaign-token")
            .build()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    this.onResume()
                } else {
                    this.finish()
                }
            }

        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        val response = AuthorizationClient.getResponse(resultCode, data)
        if (response.error != null && response.error.isEmpty()) {
            //setResponse(response.error)
        }
        if (requestCode == AUTH_TOKEN_REQUEST_CODE) {
            vm.onTokenObtained(response.accessToken)
        }
    }

    private fun getRedirectUri(): Uri? {
        return Uri.Builder()
            .scheme(getString(R.string.com_spotify_sdk_redirect_scheme))
            .authority(getString(R.string.com_spotify_sdk_redirect_host))
            .build()
    }

    companion object {
        private const val REDIRECT_URI = "musicproject://callback"
        private const val AUTH_TOKEN_REQUEST_CODE = 0x10
        private const val CLIENT_ID = "fbaa28eb256b47ada72a8bc2a16f0fb5"
        private const val REQUEST_CODE = 1337
    }
}
