package com.example.poseexercise.views.fragment

import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.poseexercise.R
import com.example.poseexercise.adapters.PlanAdapter
import com.example.poseexercise.data.plan.Plan
import com.example.poseexercise.viewmodels.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment(), CoroutineScope {
    val TAG = "RepDetect Debug"
    private lateinit var homeViewModel: HomeViewModel
    private var planList: List<Plan>? = emptyList()
    var today : String = DateFormat.format("EEEE" , Date()) as String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity = activity as Context
        Log.d(TAG, "TODAY IS ${today}")
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.today_plans)
        val adapter = PlanAdapter(activity, homeViewModel)
        recyclerView.adapter = adapter
        launch{
            planList = homeViewModel.getPlanByDay(today)
            planList?.map {
                Log.d(TAG, "Exercise is ${it.exercise}")
            }
            planList?.let { adapter.setPlans(it) }
        }

        return view
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
}
