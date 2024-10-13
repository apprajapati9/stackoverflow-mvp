package com.apprajapati.mvp_stackoverflow.design_patterns.creational_patterns.factory_method

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

/*
    Factory method provides a way to access functionality without caring about implementation

    This could provide us with
        Separation of concerns
        allows testability
 */


sealed class Country {
    object Canada : Country()
}

object Spain : Country()
class Greece(val place: String) : Country()
data class USA(val capital : String) : Country()
//class UAE : Country()

class Currency (val code : String)

object CurrencyFactory {
    fun currencyForCountry(country: Country) : Currency {
        return when(country){
            is Spain -> Currency("EUR")
            is Greece -> Currency("EUR")
            is USA -> Currency("USD")
            is Country.Canada -> Currency("CAD")
        }
    }
}

class FactoryMethodTest {

    @Test
    fun currencyTest(){
        val greekCurrency = CurrencyFactory.currencyForCountry(Greece("GRE")).code
        println("Greek currency $greekCurrency")

        val usaCurr = CurrencyFactory.currencyForCountry(USA("GRE")).code
        println("USA currency $usaCurr")

        Assertions.assertThat(greekCurrency).isEqualTo("EUR")
        Assertions.assertThat(usaCurr).isEqualTo("USD")

    }
}
