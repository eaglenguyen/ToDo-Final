package com.example.todopt2.ui.add_edit_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.todopt2.R
import com.example.todopt2.databinding.FragmentDetailScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailScreenFragment : Fragment() {


    private val viewModel: AddEditViewModel by viewModels()
    private var _binding: FragmentDetailScreenBinding? = null
    private val binding get() = _binding!!

    // You will use this property to store information about a single entity

    private fun deleteItem() {
        viewModel.deleteTask(viewModel.task!!)
        findNavController().navigateUp()
    }





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            editTextInput.setText(viewModel.taskName)
            editTextInputInfo.setText(viewModel.taskInfo)

            editTextInput.addTextChangedListener {
                viewModel.taskName = it.toString()
            }

            editTextInputInfo.addTextChangedListener {
                viewModel.taskInfo = it.toString()
            }

            updateButton.setOnClickListener {
                viewModel.updateTask()
                findNavController().navigateUp()
            }
        }

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.delete_menu, menu)
            }

            override fun onMenuItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    R.id.delete_menu -> {
                        deleteItem()
                        return true
                    }

                    else -> {false}
                }
        }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}