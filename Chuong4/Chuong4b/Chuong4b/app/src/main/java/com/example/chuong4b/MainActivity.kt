package com.example.chuong4b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cấu hình Action Bar (tiêu đề + logo)
        supportActionBar?.apply {
            title = "Action Bar Demo"
            setDisplayShowHomeEnabled(true)
            setLogo(R.drawable.logo_app)  // icon app hiển thị kế bên tiêu đề
            setDisplayUseLogoEnabled(true)
        }
    }

    // Hàm nạp menu từ file res/menu/menu_main.xml
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Xử lý sự kiện khi người dùng chọn item trong menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_settings -> {
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_refresh -> {
                Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_help -> {
                Toast.makeText(this, "Help selected", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_check_updates -> {
                Toast.makeText(this, "Checking for updates...", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
