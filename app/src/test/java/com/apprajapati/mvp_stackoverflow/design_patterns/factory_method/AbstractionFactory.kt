package com.apprajapati.mvp_stackoverflow.design_patterns.factory_method

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/*
    Abstraction factory method provides a way to access functionality without
    caring about implementation.
    The Abstract Factory Method is a design pattern that provides an interface for
    creating families of related or dependent objects without specifying their
    concrete classes. This pattern is particularly useful in scenarios
    where a system needs to be independent of how its objects are created,
    composed, and represented.

    It has one level of abstraction over the factory pattern shown in FactoryMethod.kt file.
    provides further separation of concerns

    The Abstract Factory Method is useful in scenarios where you want to:

    - Create families of related objects without tightly coupling your code to specific classes.
    - Manage multiple product families, allowing for easy swapping and changes in implementation.
    - Keep your system flexible and adaptable to future changes in product specifications.
 */

interface DataSource {
    fun printFactoryName(): String
}

class DatabaseDataSource : DataSource {
    override fun printFactoryName(): String = "Database Factory"
}

class NetworkDataSource : DataSource {
    override fun printFactoryName(): String = "Network Factory"
}

//This provides an additional layer of abstraction on top of concrete class of interface DataSource
abstract class DataSourceFactory {

    //concrete implementation needed for this method.
    abstract fun createDataSource() : DataSource

    companion object{
        //Reified - allows you to access the actual type of T param is resolved at runtime.
        //This is useful when you need to perform type checks, create instances, or use reflection based on the generic type parameter.
        inline fun  <reified T: DataSource> createFactory() : DataSourceFactory =
            when(T::class){
                DatabaseDataSource::class -> {
                    DBFactory()
                }
                NetworkDataSource::class -> {
                    NetworkFactory()
                }
                else -> {
                    throw IllegalArgumentException("No such class exists! Please provide correct one.")
                }
            }
    }
}

//Concrete class of abstract factory i.e specific factory -> Network Factory
class NetworkFactory : DataSourceFactory(){
    override fun createDataSource(): DataSource  = NetworkDataSource()
}

//Concrete class of abstract factory i.e specific factory -> Database Factory
class DBFactory : DataSourceFactory(){
    override fun createDataSource(): DataSource = DatabaseDataSource()

}

class AbstractionFactoryTest {
    @Test
    fun testAbstractFactory(){
        //abstract class don't need instantiation as well.
        val networkDataFactory = DataSourceFactory.createFactory<NetworkDataSource>()
        val dataSource = networkDataFactory.createDataSource()

        println("created datasource $dataSource")
        Assertions.assertThat(dataSource).isInstanceOf(NetworkDataSource::class.java)

        println(dataSource.printFactoryName())
        //prints factory name, shows how dataSource object is of interface yet prints method of given concrete class.
    }
}

/*
    Diagram
     ----> DataSource Interface
          -->DatabaseDataSource /NetworkDataSource  (both implements Datasource interface)
     ----> DataSourceFactory - Abstract factory - thus needed concrete implementation
        --> NetworkFactory / DBFactory - concrete implementations

    So in a way in this example,
        DatabaseFactory is an abstract class that has 2 methods
            1. createDataSource - returns DataSource interface instance
            2. createFactory method -  returns a concrete instance of Datasource i.e classes that implements Datasource
                       DatabaseDataSource and DatabaseDataSource implements DataSource

        Now concrete implementation of DatabaseFactory
             NetworkFactory
             DBFactory
        So both classes will have to provide concrete implementation of createDataSource functionality and return a datasource.

        A way to think about is that
            We don't know how database and network source are implemented
            Factories will enable us to get an object of our preferred data source

 */


//Example attempt. - incomplete.
//interface CurrencySymbol {
//    fun currency(): String
//}
//
//sealed class Countries
//
//class USAImpl : Countries(), CurrencySymbol {
//    override fun currency(): String = "USA"
//}
//
//class UK : Countries(), CurrencySymbol {
//    override fun currency(): String = "POUND"
//}
//
//abstract class CountryFactory {
//    abstract fun createFactory() : CurrencySymbol
//
//    companion object {
//        inline fun <reified T: CurrencySymbol> createCountryCurr() {
//
//        }
//    }
//}