package com.example.quizapp.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val window: Window = this@ProfileActivity.window
        window.statusBarColor = ContextCompat.getColor(this@ProfileActivity, R.color.grey)

        binding.apply {
            setupUserData()
            setupClickListeners()
            setupBottomNavigation()
        }
    }

    private fun ActivityProfileBinding.setupUserData() {
        // Load user data
        userName.text = getUserName()
        userEmail.text = getUserEmail()
        totalScore.text = getTotalScore().toString()
        gamesPlayedCount.text = getGamesPlayed().toString()
        winRatePercentage.text = "${getWinRate()}%"

        // Load profile image using Glide
        val drawableResourceId: Int = binding.root.resources.getIdentifier(
            getUserProfilePic(), "drawable", binding.root.context.packageName
        )
        Glide.with(root.context)
            .load(drawableResourceId)
            .into(profileImage)
    }

    private fun ActivityProfileBinding.setupClickListeners() {
        backButton.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
            finish()
        }

        viewAllAchievements.setOnClickListener {
            navigateToAchievements()
        }

        scienceAchBtn.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, LeaderActivity::class.java))
        }

        historyAchBtn.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, LeaderActivity::class.java))
        }
    }

    private fun ActivityProfileBinding.setupBottomNavigation() {
        bottomMenu.setItemSelected(R.id.profile)
        bottomMenu.setOnItemSelectedListener { id ->
            when (id) {
                R.id.home -> {
                    startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
                    finish()
                }
                R.id.Board -> {
                    startActivity(Intent(this@ProfileActivity, LeaderActivity::class.java))
                    finish()
                }
                R.id.profile -> {
                    // Already on profile screen
                }
            }
        }
    }

    private fun navigateToAchievements() {
        Toast.makeText(this, "Achievements", Toast.LENGTH_SHORT).show()
        // startActivity(Intent(this, AchievementsActivity::class.java))
    }

    // Helper methods to get user data from SharedPreferences or default values
    private fun getUserName(): String {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("user_name", "Yash") ?: "Yash"
    }

    private fun getUserEmail(): String {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("user_email", "yash@example.com") ?: "yash@example.com"
    }

    private fun getUserProfilePic(): String {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("user_profile_pic", "profile") ?: "profile"
    }

    private fun getTotalScore(): Int {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        return sharedPreferences.getInt("total_score", 2024)
    }

    private fun getGamesPlayed(): Int {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        return sharedPreferences.getInt("games_played", 45)
    }

    private fun getWinRate(): Int {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        return sharedPreferences.getInt("win_rate", 78)
    }
}