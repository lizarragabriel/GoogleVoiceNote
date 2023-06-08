package com.lizarragabriel.mynote.main

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.speech.RecognizerIntent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lizarragabriel.mynote.R
import com.lizarragabriel.mynote.databinding.ActivityMainBinding
import com.lizarragabriel.mynote.main.adapter.NoteAdapter
import com.lizarragabriel.mynote.utils.MyNote
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val mMainViewModel: MainViewModel by viewModels()
    private lateinit var myAdapter: NoteAdapter
    private lateinit var myContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        myContext = this
        myToolbar()
        myViewModel()
        myNoteAdapter()
        mOnSwpiteDeleteNote()
    }

    private fun myToolbar() {
        setSupportActionBar(binding.mToolbar)
    }

    private fun myViewModel() {
        binding.apply {
            viewmodel = mMainViewModel
            lifecycleOwner = this@MainActivity
        }
        mMainViewModel.notes.observe(this, { notes -> myAdapter.setList(notes) })
    }

    private fun myNoteAdapter() {
        myAdapter = NoteAdapter()
        binding.mRecyclerView.adapter = myAdapter
        myAdapter.onItemClick = { mMainViewModel.mUpdate(it) }
        myAdapter.onItemClick2 = { mMainViewModel.mUpdate(it) }
    }

    private fun mOnSwpiteDeleteNote() {
        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    mMainViewModel.mDeleteNote(myAdapter.getNumber(viewHolder.adapterPosition))
                }
            }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.mRecyclerView) // delete one task
    }

    fun mCall(view: View) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Listo para hablar")
        try {
            resultLauncher.launch(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val result = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if(!result.isNullOrEmpty()) {
                val note = result[0]
                mMainViewModel.mTextVoice(note)
                isAlarmProgrammable(note)
            }
        }
    }

    private fun isAlarmProgrammable(note: String) {
        val myNote = MyNote(note)
        if(myNote.myConvertNote()) {
            showAlarmDialog(myNote.note, myNote.hour, myNote.minute)
        }
    }

    private fun showAlarmDialog(newNote: String, hour: Int, minute: Int) {
        val alert = MaterialAlertDialogBuilder(myContext, R.style.AlertDialogTheme)
            .setTitle("Alarma")
            .setMessage("¿Quieres establecer una alarma para '$newNote' el día de hoy a las $hour:$minute?")
            .setPositiveButton("Aceptar") { m1, m2 ->
                setAlarm(newNote, hour, minute)
            }
            .setNegativeButton("Cancelar") { m1, m2 -> }
            .setCancelable(false)
        alert.show()
    }

    private fun setAlarm(message: String, hour: Int, minute: Int) {
        val alarmIntent = Intent(AlarmClock.ACTION_SET_ALARM)
        alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, message)
        alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, hour)
        alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, minute)
        alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, false)
        if(alarmIntent.resolveActivity(packageManager) != null) {
            startActivity(alarmIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.mDeleteAll -> mDeleteAlert()
            R.id.mDeleteCompleted -> mMainViewModel.mDeleteCompleted()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun mDeleteAlert() {
        val mAlertDialog = MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
            .setTitle("Eliminar")
            .setMessage("¿Desea eliminar todo?")
            .setPositiveButton("Eliminar") { mdialog, mwhich ->
                mMainViewModel.mDeleteAll()
            }
            .setNegativeButton("Cancelar") { mdialog, mWhich -> }
        mAlertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}