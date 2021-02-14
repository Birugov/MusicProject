package ru.techcrat.musicproject.ui.main

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.techcrat.musicproject.R
import ru.techcrat.musicproject.ui.main.adapters.SectionsPagerAdapter


class MainActivity : AppCompatActivity() {

    private val vm by viewModel<MainViewModule>()
    private var mSpotifyAppRemote: SpotifyAppRemote? = null
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
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val response: AuthenticationResponse =
                AuthenticationClient.getResponse(result.resultCode, result.data)
            Log.d("token", response.accessToken ?: "none")
            vm.onTokenObtained(response.accessToken)
        }
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        )
        val builder: AuthenticationRequest.Builder = AuthenticationRequest.Builder(
            CLIENT_ID,
            AuthenticationResponse.Type.TOKEN,
            Uri.parse(REDIRECT_URI).toString()
        )
        builder
            .setShowDialog(true)
            .setScopes(arrayOf("user-read-private", "streaming"))
        val request: AuthenticationRequest = builder.build()

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request)
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

//    override fun onStart() {
//        super.onStart()
//        val connectionParams = ConnectionParams.Builder(CLIENT_ID)
//            .setRedirectUri(REDIRECT_URI)
//            .showAuthView(true)
//            .build()
//        SpotifyAppRemote.connect(this, connectionParams,
//            object : Connector.ConnectionListener {
//                override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
//                    mSpotifyAppRemote = spotifyAppRemote
//                    mSpotifyAppRemote?
//                    Log.d("MainActivity", "Connected! Yay!")
//
//                    // Now you can start interacting with App Remote
//                    connected()
//                }
//
//                override fun onFailure(throwable: Throwable) {
//                    Log.e("MyActivity", throwable.message, throwable)
//
//                    // Something went wrong when attempting to connect! Handle errors here
//                }
//            })
//    }
//
//    override fun onStop() {
//        super.onStop()
//        SpotifyAppRemote.disconnect(mSpotifyAppRemote)
//    }
//
//    private fun connected() {
//        // Play a playlist
//        mSpotifyAppRemote?.getPlayerApi()?.play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL")
//
//        // Subscribe to PlayerState
//        mSpotifyAppRemote?.getPlayerApi()
//            ?.subscribeToPlayerState()
//            ?.setEventCallback { playerState ->
//                val track: Track = playerState.track
//                if (track != null) {
//                    Log.d("MainActivity", track.name.toString() + " by " + track.artist.name)
//                }
//            }
//    }

    companion object {
        private const val REDIRECT_URI = "spotify-sdk://auth"
        private const val CLIENT_ID = "089d841ccc194c10a77afad9e1c11d54"
        private const val REQUEST_CODE = 1337
    }
}
