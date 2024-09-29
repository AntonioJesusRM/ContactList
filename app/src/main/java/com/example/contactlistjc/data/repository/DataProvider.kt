package com.example.contactlistjc.data.repository

import com.example.contactlistjc.data.repository.local.LocalDataSource
import javax.inject.Inject

class DataProvider @Inject constructor(
    private val localDataSource: LocalDataSource
) : DataSource {
}