package com.exe.multitool

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkStoragePermissions()

        val rv = findViewById<RecyclerView>(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = ModuleAdapter(ModuleRegistry.modules) { module ->
            launchModule(module)
        }

        findViewById<TextView>(R.id.tvGlobalLog).setOnClickListener {
            startActivity(Intent(this, GlobalLogActivity::class.java))
        }
    }

    private fun launchModule(module: ModuleInfo) {
        try {
            val cls = Class.forName(module.entryClass)
            GlobalLog.append(this, "AVVIO: ${module.name}")
            startActivity(Intent(this, cls))
        } catch (e: ClassNotFoundException) {
            GlobalLog.append(this, "ERRORE ${module.name}: classe non trovata (${module.entryClass})")
            Toast.makeText(this, "Modulo non disponibile", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            GlobalLog.append(this, "ERRORE ${module.name}: ${e.message}")
            Toast.makeText(this, "Errore avvio modulo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkStoragePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.data = Uri.parse("package:$packageName")
                    startActivity(intent)
                } catch (e: Exception) {
                    startActivity(Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION))
                }
            }
        }
    }
}

object GlobalLog {
    private const val PREFS = "multitool_global_log"
    private const val KEY = "log_content"

    fun append(context: Context, msg: String) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val ts = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        val existing = prefs.getString(KEY, "") ?: ""
        val updated = "[$ts] $msg\n$existing"
        prefs.edit().putString(KEY, updated.take(15000)).apply()
    }

    fun get(context: Context): String =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY, "") ?: ""

    fun clear(context: Context) =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().remove(KEY).apply()
}

class ModuleAdapter(
    private val items: List<ModuleInfo>,
    private val onClick: (ModuleInfo) -> Unit
) : RecyclerView.Adapter<ModuleAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val icon: TextView = v.findViewById(R.id.tvIcon)
        val name: TextView = v.findViewById(R.id.tvModuleName)
        val desc: TextView = v.findViewById(R.id.tvModuleDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_module_card, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(h: VH, pos: Int) {
        val item = items[pos]
        h.icon.text = item.icon
        h.name.text = item.name
        h.desc.text = item.description
        h.itemView.setOnClickListener { onClick(item) }
    }
}
