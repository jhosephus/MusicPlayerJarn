package com.example.musicplayer.core.presentation.transformations.resize_screen

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.musicplayer.databinding.FragmentPlayerBinding

class PlayerScreenTransformer(
    private val binding : FragmentPlayerBinding
) : ResizeScreen {
    override fun vanish() {
        binding.playerHeader.visibility = View.GONE
        binding.playerBody.visibility = View.GONE
        var originalParams = binding.llExpandablePlayer.layoutParams
        originalParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        originalParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT

        binding.llExpandablePlayer.layoutParams = originalParams
    }

    override fun fullScreen() {
        var originalParams = binding.llExpandablePlayer.layoutParams
        originalParams.height = ConstraintLayout.LayoutParams.MATCH_PARENT
        originalParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT
        binding.llExpandablePlayer.layoutParams = originalParams
        binding.playerBody.visibility = View.VISIBLE
    }

    override fun minimalExpand() {
        var originalParams = binding.llExpandablePlayer.layoutParams
        originalParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        originalParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT

        binding.llExpandablePlayer.layoutParams = originalParams
        binding.playerBody.visibility = View.GONE
    }
}