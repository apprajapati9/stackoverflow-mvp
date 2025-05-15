package com.apprajapati.mvp_stackoverflow.design_patterns.creational_patterns.factory_method

interface Animal {
    fun makeSound()
}

class Dog : Animal {
    override fun makeSound() {
        println("woof")
    }

}

class Cat : Animal {
    override fun makeSound() {
        println("meow")
    }
}

enum class AnimalType {
    DOG, CAT
}

object AnimalFactor {
    fun createAnimal(animalType: AnimalType): Animal {
        return when (animalType) {
            AnimalType.DOG -> Dog()
            AnimalType.CAT -> Cat()
        }
    }
}

fun main() {
    val dog = AnimalFactor.createAnimal(AnimalType.DOG)
    dog.makeSound()
}
