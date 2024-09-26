package com.apprajapati.mvp_stackoverflow.design_patterns.facade

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/* Facade pattern - provides a simple interface to a complex functionality
It sort of like dependency where class implementation will instantiate that class n use those functionality
 */

class SystemStore (private val filePath : String) {
    private val cache: HashMap<String, String>

    init {
        println("Reading data from file $filePath")
        cache = HashMap()
    }

    fun store(key: String, value : String) {
        cache[key] = value
    }

    fun read(key: String) = cache[key] ?: ""

    fun commit() = println("Storing cache data to file $filePath")
}

data class User(val login: String)

//Facade
class UserRepository {
    private val systemPref = SystemStore("C:\\users\\user.prefs")

    fun save(user: User){
        systemPref.store("USER_KEY", user.login)
        systemPref.commit()
    }

    fun findFirst(): User = User(systemPref.read("USER_KEY"))
}

class FacadeTest {
    @Test
    fun testFacade(){
        val userRepo = UserRepository()
        val user = User("John")

        userRepo.save(user)

        val getUser = userRepo.findFirst().login
        println("User found -> $getUser")

        Assertions.assertThat(getUser).isEqualTo("John")
    }
}