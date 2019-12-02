package com.universodoandroid.starwarsjetpack.local.mapper

import com.universodoandroid.starwarsjetpack.data.entities.Person
import com.universodoandroid.starwarsjetpack.local.entity.PersonEntity
import java.util.*

internal object PersonMapper {

    fun toData(person: Person) =
        PersonEntity(
            id = UUID.randomUUID().toString(),
            birthYear = person.birthYear,
            created = person.created,
            edited = person.edited,
            eyeColor = person.eyeColor,
            gender = person.gender,
            hairColor = person.hairColor,
            height = person.height,
            homeworld = person.homeworld,
            mass = person.mass,
            name = person.name,
            skinColor = person.skinColor,
            url = person.url
        )

    fun fromData(personEntity: PersonEntity) =
        Person(
            id = personEntity.id,
            birthYear = personEntity.birthYear,
            created = personEntity.created,
            edited = personEntity.edited,
            eyeColor = personEntity.eyeColor,
            gender = personEntity.gender,
            hairColor = personEntity.hairColor,
            height = personEntity.height,
            homeworld = personEntity.homeworld,
            mass = personEntity.mass,
            name = personEntity.name,
            skinColor = personEntity.skinColor,
            url = personEntity.url
        )

    fun dataEntitiesToEntities(dataEntities: List<PersonEntity>) = dataEntities.map(
        PersonMapper::fromData
    )

    fun entitiesToDataEntities(entities: List<Person>) = entities.map(PersonMapper::toData)


}