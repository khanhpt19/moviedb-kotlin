package com.example.moviedb.extension

import androidx.appcompat.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

fun SearchView.fromView(
    queryChangeAction: (String) -> Unit,
    querySubmitAction: (String) -> Unit
): Observable<String> {
    val subject = PublishSubject.create<String>()
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            querySubmitAction.invoke(query ?: return false)
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            queryChangeAction.invoke(newText ?: return false)
            subject.onNext(newText)
            return true
        }
    })
    return subject
}
