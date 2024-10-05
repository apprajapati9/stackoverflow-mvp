package com.apprajapati.mvp_stackoverflow.kotlin_playground.kotlin_langauge

import java.util.Collections


fun main() {
    val es = Drink(name = "Espresso", coffeeBeans = 3, water = 1)
    val am = Drink(name = "Americano", coffeeBeans = 2, water = 3)
    val la = Drink(name = "Latte", coffeeBeans = 2, milk = 2, water = 2)

    //extension of API where other types of drinks can be accepted.
    val softDrink = SoftDrinks(name = "Carbonated water", defaultPrice = 4)

    //Screen cannot and should not be able to add drinks or modify drinks
    val screen = Screen()

    val admin = Admin()
    admin.setupListener(screen) //Screen will just listen to changes if admin updates any value.

    //Admin can add coffee and change contents.
    admin.addCoffee(es)
    admin.addCoffee(am)
    admin.addCoffee(la)
    admin.addDrink(softDrink)

    println("Available drinks: -> ")
    screen.showDrinks()
    screen.showCoffeeContents()

    println()

    println("Select a drink you want from 1 to ${screen.getCount()}")
    val input = readlnOrNull()
    val selection = input?.toInt()
    var nameOfSelection = ""
    if (selection!! > screen.getCount() && selection > 0) {
        println("Sorry, select from 1 to ${screen.getCount()}")
    } else {
        nameOfSelection = screen.getSpecificDrinkName(selection - 1)
        println("Your selection -> $nameOfSelection")
    }

    println("Updating value of $nameOfSelection's Ingredients -> beans to 5, milk to 5000 and water to 2000")

    //Hardcoded for simplicity, this can be obtained from user.
    admin.updateCoffeeContents(selection, 5, 5000, 2000)


    println()


    screen.showDrinks()
    screen.showCoffeeContents()

    println()

    //Uncomment to prevent program from asking to add coffee value until replied No.
    promptForAddMoreCoffee(admin, screen)
}


/*
 Problem;
Object Oriented Design -
Design a coffee maker machine class There is a coffee maker with a screen. We need to add three ingredients into the machine: coffee beans, water and milk.
There are three types of drinks we can make, below are the default recipes:

Espresso: cost 3 coffee beans and 1 water

Americano: cost 2 coffee beans and 3 water

Latte: cost 2 coffee beans, 2 milk and 2 water

When a user comes, on the screen we show available drinks. After the user chooses a drink, the user will be able to customize the amount of ingredients. (For example, after choosing Espresso, the user can change from default to 4 coffee beans and 1 water)

The admin is able to refill the ingredients. The admin and the users interact with the machine via the screen. In the future, we might can support more drink types.

Please design a class with public APIs to represent the coffee maker, which will be called by the screen.
 */

interface UpdateScreen {
    fun updateListOfProducts(products: List<Machine>)
}

//Admin will be able to addCoffee or any other type of drink if needed.
class Admin(private val drinksList: MutableList<Machine> = mutableListOf()) {

    private lateinit var updateScreenListener: UpdateScreen

    fun setupListener(listener: UpdateScreen) {
        updateScreenListener = listener
    }

    fun addCoffee(coffee: CoffeeMaker) {
        drinksList.add(coffee)
        updateScreenListener.updateListOfProducts(drinksList.toImmutableList())
    }

    fun addDrink(drink: SoftDrinksMaker) {
        drinksList.add(drink)
        updateScreenListener.updateListOfProducts(drinksList.toImmutableList())
    }

    fun updateCoffeeContents(position: Int, beans: Int, milk: Int, water: Int) {

        val currentPosition = position - 1

        if (drinksList[currentPosition] is CoffeeMaker) {
            val coffee = drinksList[position - 1] as CoffeeMaker
            if (coffee.drinkType() == DrinkType.COFFEE) { //Making sure if adding these to coffee only.
                coffee.addMilk(milk)
                coffee.addWater(water)
                coffee.addBeans(beans)

                drinksList.removeAt(currentPosition)
                drinksList.add(currentPosition, coffee)
                updateScreenListener.updateListOfProducts(drinksList.toImmutableList())
            }
        } else {
            println("Can only change Coffee value.")
        }

    }

}

//Responsibility to only show data, not modify.
class Screen : UpdateScreen {

    //Immutable list of products to show for the screen
    private var drinksList = listOf<Machine>()

    fun showDrinks() {
        println()
        drinksList.forEach {
            println(it.getProductName())
        }
    }

    fun showCoffeeContents() {
        println()
        drinksList.forEach {
            it.showContents()
        }
    }

    fun getCount(): Int {
        return drinksList.size
    }

    fun getSpecificDrinkName(position: Int): String {
        return if (position <= drinksList.size) {
            drinksList[position].getProductName()
        } else {
            "Drink doesn't exist!"
        }
    }

    //Screen will get updated automatically when admin changes data.
    override fun updateListOfProducts(products: List<Machine>) {
        drinksList = products
    }
}

interface Machine {
    fun showContents()
    fun getProductName(): String

    fun drinkType(): DrinkType
}

enum class DrinkType {
    SOFT_DRINK,
    COFFEE
}

interface CoffeeMaker : Machine {
    fun addWater(water: Int)
    fun addMilk(milk: Int)
    fun addBeans(beans: Int)
}

interface SoftDrinksMaker : Machine {
    fun name(drinkName: String)
    fun setPrice(price: Int)
}


class Drink(
    private var name: String,
    private var coffeeBeans: Int = 3,
    private var milk: Int = 0,
    private var water: Int = 1,
) : CoffeeMaker {

    override fun addWater(water: Int) {
        this.water = water
    }

    override fun addMilk(milk: Int) {
        this.milk = milk
    }

    override fun addBeans(beans: Int) {
        coffeeBeans = beans
    }

    override fun showContents() {
        println("Coffee name= $name uses $coffeeBeans coffee beans, $milk milk, and $water water")
    }

    override fun getProductName(): String {
        return name
    }

    override fun drinkType(): DrinkType {
        return DrinkType.COFFEE
    }
}

class SoftDrinks(private var name: String, private var defaultPrice: Int) : SoftDrinksMaker {

    override fun name(drinkName: String) {
        name = drinkName
    }

    override fun setPrice(price: Int) {
        defaultPrice = price
    }

    override fun showContents() {
        println("$name costs $defaultPrice amount!")
    }

    override fun getProductName(): String {
        return name
    }

    override fun drinkType(): DrinkType {
        return DrinkType.SOFT_DRINK
    }

}


//This function will prompt user to add more Coffee to the list.
//Input format when testing: coffeeName,12,12,12  (String,Int,Int,Int) no spaces.
fun promptForAddMoreCoffee(admin: Admin, screen: Screen) {

    do {
        println("<<-------MAKE CUSTOM COFFEE  ------>> ")
        println("Enter coffee name and 3 numbers separated by , (comma)")
        val input = readlnOrNull()
        val coffeeInfo = input!!.toString()

        if (coffeeInfo.contains(",") && coffeeInfo.split(",").size == 4) {

            val newCoffee = coffeeInfo.split(",")
            admin.addCoffee(
                Drink(
                    newCoffee[0],
                    newCoffee[1].toInt(),
                    newCoffee[2].toInt(),
                    newCoffee[3].toInt()
                )
            )
            println("you created a new coffee named ${newCoffee[0]}")
        } else {
            println("Please enter a valid information separated by , in order specified.")
        }

        println()
        println("---------Showing available drinks ---> ")
        screen.showDrinks()


        println("---------Showing contents of all drinks ---> ")
        screen.showCoffeeContents()

        println()

        println("Do you want to make a custom coffee? Enter yes or no")
        val answer = readlnOrNull()

    } while (answer != null && answer == "yes")

}

fun <T> List<T>.toImmutableList(): List<T> {
    return Collections.unmodifiableList(toMutableList())
}