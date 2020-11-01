package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.smarttoolfactory.tutorial3_1transitions.MockDataCreator
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.TravelAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.TravelModel
import com.smarttoolfactory.tutorial3_1transitions.databinding.ItemTravelBinding

@Suppress("UNCHECKED_CAST")
class Fragment2_6ExpandCollapseList : Fragment() {

    private val recycledViewPool by lazy {
        RecyclerView.RecycledViewPool()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment2_6list, container, false)

        prepareTransitions(view)
        postponeEnterTransition()
        return view
    }

    private fun prepareTransitions(view: View) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)


        val listAdapter =
            TravelAdapter(recycledViewPool) { binding: ItemTravelBinding, model: TravelModel ->
//                goToDetailPage(binding, model)
            }


        recyclerView.apply {
            adapter = listAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }

        listAdapter.submitList(
            MockDataCreator.generateMockTravelData(requireContext()).toMutableList()
        )

        recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun goToDetailPage(binding: ItemTravelBinding, model: TravelModel) {

        val direction: NavDirections =  Fragment2_6ExpandCollapseListDirections
        .actionFragment26ExpandCollapseListToFragment26ExpandCollapseDetails(model)

        val extras = FragmentNavigatorExtras(
            binding.ivAvatar to binding.ivAvatar.transitionName,
            binding.tvTitle to binding.tvTitle.transitionName,
        )

        findNavController().navigate(direction, extras)

    }
}