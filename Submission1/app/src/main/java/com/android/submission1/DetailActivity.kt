package com.android.submission1

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mikhaellopez.circularimageview.CircularImageView

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_PERSON = "extra_person"
    }

    @SuppressLint("CutPasteId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        title = "Detail User\'s"

        val user = intent.getParcelableExtra<User>(EXTRA_PERSON) as User
        val imgAvatar = findViewById<CircularImageView>(R.id.img_avatar)
        val tvName: TextView = findViewById(R.id.tv_name)
        val tvUsername: TextView = findViewById(R.id.tv_username)
        val tvRepository: TextView = findViewById(R.id.tv_repository)
        val tvFollowers: TextView = findViewById(R.id.tv_followers)
        val tvFollowing: TextView = findViewById(R.id.tv_following)
        val tvCompany: TextView = findViewById(R.id.tv_company)
        val tvLocation: TextView = findViewById(R.id.tv_location)
        imgAvatar.setImageResource(user.avatar)
        tvName.text = user.name
        tvUsername.text = "@${user.username}"
        tvRepository.text = user.repository
        tvFollowers.text = user.followers
        tvFollowing.text = user.following
        tvCompany.text = user.company
        tvLocation.text = user.location
        tvLocation.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=${user.location}")))
        }
    }
}