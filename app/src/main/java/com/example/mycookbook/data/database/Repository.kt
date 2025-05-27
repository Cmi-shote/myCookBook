package com.example.mycookbook.data.database

import com.example.mycookbook.data.LocalDataSource

class Repository(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {

    val remote = remoteDataSource
    val local = localDataSource

}