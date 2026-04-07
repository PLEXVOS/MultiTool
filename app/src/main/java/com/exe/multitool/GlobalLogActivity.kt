package com.exe.multitool

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GlobalLogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global_log)

        val tvLog = findViewById<TextView>(R.id.tvLogContent)
        val btnCopy = findViewById<Button>(R.id.btnCopyLog)
        val btnClear = findViewById<Button>(R.id.btnClearLog)

        refreshLog(tvLog)

        btnCopy.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("MultiTool Log", tvLog.text))
            Toast.makeText(this, "Log copiato", Toast.LENGTH_SHORT).show()
        }

        btnClear.setOnClickListener {
            GlobalLog.clear(this)
            refreshLog(tvLog)
            Toast.makeText(this, "Log cancellato", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshLog(tv: TextView) {
        val content = GlobalLog.get(this)
        tv.text = if (content.isBlank()) "Nessun log disponibile." else content
    }
}
