package com.apprajapati.mvp_stackoverflow.design_patterns.singleton

/*
Singleton pattern is used when you need to have a single point of access to resources
Examples :
    Network manager - i.e Retrofit instance
    Database access
    Logging
    Utility class/es


 */

//Java like structure of Singleton in Kotlin
class SingletonJava private constructor() {
    private object HOLDER{
        val INSTANCE = SingletonJava()
    }
    companion object {
        val instance : SingletonJava by lazy { HOLDER.INSTANCE }
    }
}


/*
    Kotlin makes it really easy to create a singleton class by using simply object keyboard
    This is all you need in order to create a singleton because it will allow only
    one instance to exist of this class.
 */
object Singleton {

}
