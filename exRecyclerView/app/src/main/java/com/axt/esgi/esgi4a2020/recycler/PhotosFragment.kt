package com.axt.esgi.esgi4a2020.recycler

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.axt.esgi.esgi4a2020.R
import com.axt.esgi.esgi4a2020.common.showError
import com.axt.esgi.esgi4a2020.data.api.FlickrProvider
import com.axt.esgi.esgi4a2020.data.api.Listener
import com.axt.esgi.esgi4a2020.data.model.Photo

/**
 * A simple [Fragment] subclass.
 */
class PhotosFragment : Fragment() {

    private lateinit var photosRecyclerView: RecyclerView
    private val photosAdapter: PhotosAdapter by lazy { PhotosAdapter() }
    private val removeDrawableBgColor: ColorDrawable = ColorDrawable(Color.RED)
    private lateinit var trashIcon: Drawable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photosRecyclerView = view.findViewById(R.id.photos_rcv)
        initRecyclerView()

        getPhotos()
    }

    private fun getPhotos() {
        FlickrProvider.getPhotos(object : Listener<List<Photo>> {
            override fun onSuccess(data: List<Photo>) {
                photosAdapter.data = data
            }

            override fun onError(t: Throwable) {
                showError(t)
            }
        })
    }

    private fun initRecyclerView() {
        trashIcon = resources.getDrawable(R.drawable.ic_delete_black, null)
        photosRecyclerView.layoutManager = LinearLayoutManager(context)
        photosRecyclerView.adapter = photosAdapter
        photosRecyclerView.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )

        val itemTouchHelper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    recyclerView.adapter?.notifyItemMoved(
                        viewHolder.adapterPosition,
                        target.adapterPosition
                    )
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    photosAdapter.removeItem(viewHolder.adapterPosition)
                }

                override fun onChildDraw(
                    canvas: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {

                    val itemView = viewHolder.itemView
                    val iconMargin = (viewHolder.itemView.height - trashIcon.intrinsicHeight) / 2

                    if (dX > 0) {
                        removeDrawableBgColor.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                        trashIcon.setBounds(itemView.left + iconMargin, itemView.top + iconMargin,
                            itemView.left + iconMargin + trashIcon.intrinsicWidth, itemView.bottom - iconMargin)
                    } else {
                        removeDrawableBgColor.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                        trashIcon.setBounds(itemView.right - iconMargin - trashIcon.intrinsicWidth, itemView.top + iconMargin,
                            itemView.right - iconMargin, itemView.bottom - iconMargin)
                        trashIcon.level = 0
                    }

                    removeDrawableBgColor.draw(canvas)
                    canvas.save()

                    if(dX > 0)
                        canvas.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                    else
                        canvas.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)

                    trashIcon.draw(canvas)
                    canvas.restore()
                    super.onChildDraw(
                        canvas,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }
        )
        itemTouchHelper.attachToRecyclerView(photosRecyclerView)

        photosAdapter.listener = this::navigateToDetail
    }



    private fun navigateToDetail(photo: Photo) {
        findNavController().navigate(
            PhotosFragmentDirections.actionPhotosFragmentToPhotoDetailFragment(
                photo.id
            )
        )
    }
}


