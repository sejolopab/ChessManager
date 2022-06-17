package com.example.ajedrez.view.lessons

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ajedrez.R
import com.example.ajedrez.model.Assistance
import com.example.ajedrez.model.Lesson
import com.example.ajedrez.network.NetworkManager
import com.example.ajedrez.utils.AlertsManager
import com.example.ajedrez.utils.Utils.Companion.hideKeyboard
import com.example.ajedrez.view.BaseFragment
import com.example.ajedrez.view.assistance.AssistanceListAdapter
import com.example.ajedrez.view.assistance.AssistanceListAdapter.AssistanceViewHolder
import com.example.ajedrez.view.assistance.StudentsAssistanceListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class LessonEditFragment(private var lesson: Lesson, private var listener: StudentsAssistanceListener) : BaseFragment() {

    //==============================================================================================
    // Properties
    //==============================================================================================

    private var recyclerView: RecyclerView? = null
    private var adapter: AssistanceListAdapter? = null

    companion object {
        fun newInstance(lesson: Lesson, listener: StudentsAssistanceListener): LessonEditFragment {
            return LessonEditFragment(lesson, listener)
        }
    }

    //==============================================================================================
    // Lifecycle
    //==============================================================================================

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_lesson_edit, container, false)
        recyclerView = view.findViewById(R.id.assistanceEditList)
        val saveAssistance: FloatingActionButton = view.findViewById(R.id.saveAssistance)
        saveAssistance.setOnClickListener { saveAssistance() }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AssistanceListAdapter(lesson.assistance, listener)
        recyclerView!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.adapter = adapter
        setupEventListeners(view)
    }

    override fun onResume() {
        super.onResume()
        hideKeyboardFrom(context, view)
    }

    //==============================================================================================
    // Methods
    //==============================================================================================

    private fun setupEventListeners(view: View) {
        val contentLayout = view.findViewById<LinearLayout>(R.id.contentLayout)
        contentLayout.setOnClickListener {
            hideKeyboardFrom(
                requireContext(),
                view
            )
        }
        setupSearchBar(view)
        setupSwipeListener()
    }

    private fun setupSearchBar(view: View) {
        val searchText: AppCompatEditText = view.findViewById(R.id.searchText)
        searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.toString().isNotEmpty()) {
                    val filteredList = filter(s.toString())
                    adapter!!.setAssistanceList(filteredList)
                    adapter!!.notifyDataSetChanged()
                } else {
                    adapter!!.setAssistanceList(lesson.assistance)
                    adapter!!.notifyDataSetChanged()
                }
            }
        })
        searchText.onFocusChangeListener = OnFocusChangeListener { v: View?, hasFocus: Boolean ->
            if (!hasFocus) {
                hideKeyboard(v!!, requireContext())
            }
        }
    }

    private fun setupSwipeListener() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                swipeDir: Int
            ) {
                val vHolder = viewHolder as AssistanceViewHolder
                val tStudent = vHolder.item
                for (assistance in lesson.assistance) {
                    if (assistance.student!!.name == tStudent.student!!.name) {
                        tStudent.assisted = swipeDir == 4
                        adapter!!.notifyDataSetChanged()
                    }
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun filter(text: String): List<Assistance?> {
        val filteredList: MutableList<Assistance?> = ArrayList()
        for (item in lesson.assistance) {
            val student = item.student
            if (student!!.name != null) {
                if (student.name!!.lowercase().contains(text.lowercase())) {
                    filteredList.add(item)
                    continue
                }
            }
            if (student.school != null) {
                if (student.school!!.lowercase().contains(text.lowercase())) {
                    filteredList.add(item)
                    continue
                }
            }
            if (student.birthDay != null) {
                if (student.birthDay!!.contains(text)) {
                    filteredList.add(item)
                }
            }
        }
        return filteredList
    }

    private fun saveAssistance() {
        NetworkManager.getInstance().updateAttendance(lesson, lesson.date,
            //onComplete
            {
                AlertsManager.getInstance().showAlertDialog(
                    null,
                    getString(R.string.successful),
                    context
                )
            },
            //onError
            {
                AlertsManager.getInstance().showAlertDialog(
                    getString(R.string.error_title),
                    getString(R.string.error_unsuccessful),
                    context
                )
            })
    }
}