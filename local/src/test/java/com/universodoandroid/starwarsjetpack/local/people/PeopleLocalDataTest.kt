package com.universodoandroid.starwarsjetpack.local.people

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.universodoandroid.starwarsjetpack.data.people.datastore.PeopleLocalData
import com.universodoandroid.starwarsjetpack.local.cache.CachePreferences
import com.universodoandroid.starwarsjetpack.local.cache.CacheType
import com.universodoandroid.starwarsjetpack.local.people.data.PeopleDataMock.getPersonData
import com.universodoandroid.starwarsjetpack.local.people.data.PeopleDataMock.getPersonEntity
import com.universodoandroid.starwarsjetpack.local.people.data.PeopleLocalDataImpl
import com.universodoandroid.starwarsjetpack.local.people.database.PeopleDatabase
import com.universodoandroid.starwarsjetpack.local.people.mapper.PersonDataMapper
import com.universodoandroid.starwarsjetpack.local.people.mapper.PersonEntityMapper
import com.universodoandroid.starwarsjetpack.local.people.mapper.identifier.IdentifierGenerator
import com.universodoandroid.starwarsjetpack.local.people.mapper.imgs.DefaultPeopleImages
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test

class PeopleLocalDataTest {

    private val peopleDatabase = mock<PeopleDatabase>()
    private val cachePreferences = mock<CachePreferences>()

    private val identifierGenerator = IdentifierGenerator()
    private val defaultPeopleImages = DefaultPeopleImages()
    private val entityMapper = PersonEntityMapper(identifierGenerator, defaultPeopleImages)
    private val dataMapper = PersonDataMapper()

    private val peopleLocalData: PeopleLocalData =
        PeopleLocalDataImpl(peopleDatabase, cachePreferences, dataMapper, entityMapper)

    @Test
    fun `getPeople should return all people in database`() {
        val people = listOf(getPersonEntity("1"), getPersonEntity("2"))
        val expectedPeopleData = listOf(getPersonData("1"), getPersonData("2"))

        whenever(peopleDatabase.loadPeople()).thenReturn(Single.just(people))

        peopleLocalData.getPeople()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue(expectedPeopleData)
    }

    @Test
    fun `getPerson should return a unique person in database`() {
        val expectedId = "1"
        val personId = "1"
        val person = getPersonEntity(expectedId)
        val expectedData = getPersonData(expectedId)

        whenever(peopleDatabase.loadPerson(personId)).thenReturn(Single.just(person))

        peopleLocalData.getPerson(personId)
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue(expectedData)
    }

    @Test
    fun `savePeople should save people in database`() {
        whenever(peopleDatabase.savePeople(any())).thenReturn(Completable.complete())

        peopleLocalData.savePeople(listOf())
            .test()
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun `deleteData should delete all people database data`() {
        whenever(peopleDatabase.deleteData()).thenReturn(Completable.complete())

        peopleLocalData.deleteData()
            .test()
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun `registerCache should register status of people cache`() {
        peopleLocalData.registerCache(true)

        verify(cachePreferences).registerCache(CacheType.PEOPLE_CACHE, true)
    }

    @Test
    fun `wasCached should register status of people cache`() {
        whenever(cachePreferences.wasCached(CacheType.PEOPLE_CACHE)).thenReturn(true)

        val wasCached = peopleLocalData.wasCached()

        assertEquals(wasCached, true)
    }
}