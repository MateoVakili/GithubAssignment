package com.mateo.anwbassignment.domain.github.model

import android.os.Parcelable
import com.mateo.anwbassignment.presentation.util.view.decode
import com.mateo.anwbassignment.presentation.util.view.encode
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubRepositoryDetails(
    val owner: String,
    val ownerUrl: String,
    val repo: String,
    val repoUrl: String
) : Parcelable {
    fun decode() : GithubRepositoryDetails{
        return this.copy(
            owner = owner.decode(),
            ownerUrl = ownerUrl.decode(),
            repo = repo.decode(),
            repoUrl = repoUrl.decode()
        )
    }

    fun encode() : GithubRepositoryDetails{
        return this.copy(
            owner = owner.encode(),
            ownerUrl = ownerUrl.encode(),
            repo = repo.encode(),
            repoUrl = repoUrl.encode()
        )
    }
}


