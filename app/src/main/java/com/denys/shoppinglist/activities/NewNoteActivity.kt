package com.denys.shoppinglist.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.denys.shoppinglist.R
import com.denys.shoppinglist.databinding.ActivityNewNoteBinding
import com.denys.shoppinglist.entities.NoteItem
import com.denys.shoppinglist.fragments.NoteFragment
import com.denys.shoppinglist.utils.HtmlManager
import com.denys.shoppinglist.utils.MyTouchListener
import com.denys.shoppinglist.utils.ThemeUtils
import com.denys.shoppinglist.utils.TimeManager

class NewNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewNoteBinding
    private var note: NoteItem? = null
    private var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(ThemeUtils.applyTheme(this))
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        retrieveNoteFromIntent()
        setupColorPicker()
        initializeTouchListener()
        setTextSize()
        setupActionMenuCallback()
    }

    //onClickColorPicker
    private fun setupColorPicker() = with(binding) {
        val colorButtons = mapOf(
            R.color.picker_red to imRed,
            R.color.picker_black to imBlack,
            R.color.picker_blue to imBlue,
            R.color.picker_yellow to imYellow,
            R.color.picker_green to imGreen,
            R.color.picker_orange to imOrange
        )

        colorButtons.forEach { (color, button) ->
            button.setOnClickListener { applyTextColor(color) }
        }
    }

    //init
    @SuppressLint("ClickableViewAccessibility")
    private fun initializeTouchListener() {
        binding.colorPicker.setOnTouchListener(MyTouchListener())
        pref = PreferenceManager.getDefaultSharedPreferences(this)
    }

    //getNote
    private fun retrieveNoteFromIntent() {
        note = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY) as? NoteItem
        note?.let { fillNoteFields(it) }
    }

    private fun fillNoteFields(note: NoteItem) = with(binding) {
        edTitle.setText(note.title)
        edDescription.setText(HtmlManager.getFromHtml(note.content).trim())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.id_save -> {
                saveNote()
                true
            }

            android.R.id.home -> {
                finish()
                true
            }

            R.id.id_bold -> {
                toggleBoldForSelectedText()
                true
            }

            R.id.id_color -> {
                toggleColorPickerVisibility()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleBoldForSelectedText() {
        applyTextStyle(StyleSpan(Typeface.BOLD))
    }

    private fun applyTextColor(colorId: Int) {
        applyTextStyle(ForegroundColorSpan(ContextCompat.getColor(this, colorId)))
    }

    private fun applyTextStyle(style: Any) = with(binding) {
        val startPos = edDescription.selectionStart
        val endPos = edDescription.selectionEnd

        if (startPos != endPos) {
            removeExistingSpans(startPos, endPos, style::class.java)
            edDescription.text.setSpan(style, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            edDescription.setSelection(startPos)
        } else {
            showToast("No text selected")
        }
    }

    private fun removeExistingSpans(start: Int, end: Int, type: Class<*>) {
        val spans = binding.edDescription.text.getSpans(start, end, type)
        spans.forEach { binding.edDescription.text.removeSpan(it) }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveNote() {
        val editState = if (note == null) "new" else "update"
        val updatedNote = createOrUpdateNote()

        setResult(RESULT_OK, Intent().apply {
            putExtra(NoteFragment.NEW_NOTE_KEY, updatedNote)
            putExtra(NoteFragment.EDIT_STATE_KEY, editState)
        })
        finish()
    }

    private fun createOrUpdateNote(): NoteItem {
        val title = binding.edTitle.text.toString()
        val content = HtmlManager.toHtml(binding.edDescription.text)
        val time = TimeManager.getCurrentTime()

        return note?.copy(title = title, content = content) ?: NoteItem(
            null,
            title,
            content,
            time,
            ""
        )
    }

    //actionBarSettings
    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun toggleColorPickerVisibility() = with(binding) {
        if (colorPicker.isShown) {
            closeColorPicker()
        } else {
            openColorPicker()
        }
    }

    private fun openColorPicker() = with(binding) {
        colorPicker.visibility = View.VISIBLE
        colorPicker.startAnimation(
            android.view.animation.AnimationUtils.loadAnimation(
                this@NewNoteActivity,
                R.anim.open_color_picker
            )
        )
    }

    private fun closeColorPicker() = with(binding) {
        val closeAnim =
            android.view.animation.AnimationUtils.loadAnimation(
                this@NewNoteActivity,
                R.anim.close_color_picker
            )
        closeAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                colorPicker.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        colorPicker.startAnimation(closeAnim)
    }

    //actionMenuCallback
    private fun setupActionMenuCallback() {
        val actionCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?) =
                true.also { menu?.clear() }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) =
                true.also { menu?.clear() }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?) = true
            override fun onDestroyActionMode(mode: ActionMode?) {}
        }
        binding.edDescription.customSelectionActionModeCallback = actionCallback
    }

    private fun setTextSize() = with(binding) {
        edTitle.setTextSize(pref?.getString("title_size_key", "16"))
        edDescription.setTextSize(pref?.getString("content_size_key", "14"))
    }

    private fun EditText.setTextSize(size: String?) {
        if (size != null) this.textSize = size.toFloat()
    }
}
