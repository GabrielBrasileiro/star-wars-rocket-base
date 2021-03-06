package com.universodoandroid.starwarsjetpack.local.people.database

import com.universodoandroid.starwarsjetpack.local.people.database.entity.PersonEntity
import io.reactivex.Completable
import io.reactivex.Single

internal interface PeopleDatabase {
    fun loadPeople(): Single<List<PersonEntity>>
    fun loadPerson(id: String): Single<PersonEntity>
    fun savePeople(people: List<PersonEntity>): Completable
    fun deleteData(): Completable
}