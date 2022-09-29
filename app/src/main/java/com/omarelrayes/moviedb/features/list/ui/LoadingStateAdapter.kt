package com.omarelrayes.moviedb.features.list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.omarelrayes.moviedb.R
import com.omarelrayes.moviedb.databinding.LoadingStateLayoutBinding

class LoadingStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingStateAdapter.NetworkStateItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {
        val binding = LoadingStateLayoutBinding.bind(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.loading_state_layout, parent, false)
        )
        return NetworkStateItemViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    class NetworkStateItemViewHolder(
        private val binding: LoadingStateLayoutBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retry.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading
                retry.isVisible = loadState is LoadState.Error
                errorMessage.isVisible =
                    !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                errorMessage.text = (loadState as? LoadState.Error)?.error?.message
            }
        }
    }
}
