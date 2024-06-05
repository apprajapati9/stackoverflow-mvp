package com.apprajapati.mvp_stackoverflow.kotlin_playground

class Screen(val drinkList: MutableList<CoffeeMaker> = mutableListOf()) {

    fun addDrink(type: CoffeeMaker) {
        drinkList.add(type)
    }

    fun showDrinks() {
        println()
        drinkList.forEach {
            println(it.getCoffeeName())
        }
    }

    fun showCoffeeContents() {
        println()
        drinkList.forEach {
            it.showContents()
        }
    }

    fun getCount(): Int {
        return drinkList.size
    }

    fun getSpecificDrinkName(position: Int): String {
        if (position <= drinkList.size) {
            return drinkList.get(position).getCoffeeName()
        } else {
            return "Drink doesn't exist!"
        }
    }
}

interface CoffeeMaker {
    fun addWater(water: Int)
    fun addMilk(milk: Int)
    fun addBeans(beans: Int)
    fun showContents()
    fun getCoffeeName(): String
}

class Drink(
    private var name: String,
    private var coffeeBeans: Int = 3,
    private var milk: Int = 0,
    private var water: Int = 1
) : CoffeeMaker {

    override fun addWater(w: Int) {
        water = w;
    }

    override fun addMilk(m: Int) {
        milk = m;
    }

    override fun addBeans(beans: Int) {
        coffeeBeans = beans
    }

    override fun showContents() {
        println("Coffe name= $name uses $coffeeBeans coffee beans, $milk milk, and $water water")
    }

    override fun getCoffeeName(): String {
        return name;
    }
}

fun main() {
    val es = Drink(name = "Espresso", coffeeBeans = 3, water = 1)
    val am = Drink(name = "Americano", coffeeBeans = 2, water = 3)
    val la = Drink(name = "Latte", coffeeBeans = 2, milk = 2, water = 2)


    val screen = Screen()
    screen.addDrink(es)
    screen.addDrink(am)
    screen.addDrink(la)

    println("Availble drinks: -> ")
    screen.showDrinks()

    println()

    println("Select a drink you want from 1 to ${screen.getCount()}")
    var input = readLine()
    val selection = input?.toInt()
    if (selection!! > screen.getCount()) {
        println("Sorry, select from 1 to ${screen.getCount()}")
    } else {
        println("Your selection -> ${screen.getSpecificDrinkName(selection - 1)}")
    }


    es.showContents()
    es.addMilk(5000)
    es.showContents()

    println()


    do {
        println("<<-------MAKE CUSTOM COFFEE  ------>> ")
        println("Enter coffee name and 3 numbers separated by , (comma)")
        input = readLine()
        val coffeInfo = input!!.toString()

        if (coffeInfo.contains(",") && coffeInfo.split(",").size == 4) {

            val newCoffee = coffeInfo.split(",")
            screen.addDrink(
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
        val answer = readLine()

    } while (answer != null && answer == "yes")


}