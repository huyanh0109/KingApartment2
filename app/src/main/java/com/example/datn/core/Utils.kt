package com.example.datn.core

import com.example.datn.model.ResultApartment

class Utils {
    companion object {
        fun sortList(list: List<ResultApartment>): ArrayList<ResultApartment> {
            val listSorted = ArrayList<ResultApartment>()
            listSorted.addAll(list.sortedBy { it.distance })
            return listSorted
        }
    }
}