package com.axt.esgi.esgi4a2020

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass.
 */
class MenuFragment : Fragment() {

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_menu, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    bindButtonAction(R.id.menu_recycler_btn, MenuFragmentDirections.actionMenuFragmentToPhotosFragment())

  }

  private fun bindButtonAction(@IdRes viewId: Int, directions: NavDirections) {
    view?.let {
      it.findViewById<View>(viewId).setOnClickListener { navigate(directions) }
    }
  }

  private fun navigate(directions: NavDirections) {
    findNavController().navigate(directions)
  }

}
