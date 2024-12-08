package com.apprajapati.mvp_stackoverflow.kotlin_playground.kotlin_langauge

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.reflect.KProperty

/*
    In this example, type T will be an Object and it is called Type erasure because Type information gets erased and Object is used instead. And when accessing that information it is casted to appropriate type. (Look at bytecode for more information).

     public static final Object secondItemOf(@NotNull List list) {  //Lost type information.
      Intrinsics.checkNotNullParameter(list, "list");
      return list.get(1);
     }

     when accessing, returned type is casted like this:

      String[] var1 = new String[]{"ajay", "2", "3"};
      List strings = CollectionsKt.listOf(var1);
      String str2 = (String)secondItemOf(strings); //CAST to String
      System.out.println(str2);
      Integer[] var3 = new Integer[]{1, 3, 4};
      List listInts = CollectionsKt.listOf(var3);
      int secondInt = ((Number)secondItemOf(listInts)).intValue(); // CAST to Number
      System.out.println(secondInt);
 */
fun <T> secondItemOf(list: List<T>): T {
    return list[1]
}

//To get var literal name using KProperty.
fun <T> getVarName(property: KProperty<T>): String {
    return property.name
}

data class User(val name: String? = "Ajay", val age: Int = 25) {
    //{"name":"user1","age":12}, to use toString instead of toJson.
    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("{")
        builder.append("\"")
        builder.append(getVarName(::name))
        builder.append("\"")
        builder.append(":")

        if (name is String) {
            builder.append("\"")
            builder.append(name)
            builder.append("\"")
        }

        builder.append(",")

        builder.append("\"")
        builder.append(getVarName(::age))
        builder.append("\"")
        builder.append(":")
        builder.append(age)
        builder.append("}")

        return builder.toString()
    }
}

//Solution for type erasure
inline fun <reified T> secondItemOfList(list: List<Any>): T {
    return list[1] as T
}


//Second example for type erasure, mainly when converting string to Json at runtime.
fun <T> Gson.fromJson(json: String): T { // T will be replaced with Object
    return fromJson(json, object : TypeToken<T>() {}.type)
}

inline fun <reified T> Gson.fromJsonReify(json: String): T { // T will be replaced with Object
    return fromJson(json, object : TypeToken<T>() {}.type)
}


fun main() {

    val strings = listOf("ajay", "2", "3")
    val str2 = secondItemOf(strings)

    println(str2)

    val listInts = listOf(1, 3, 4)
    val secondInt = secondItemOf(listInts)

    println(secondInt)

    val list = listOf(11, 12, 122)
    val reifiedNum = secondItemOfList<Int>(list)

    println(reifiedNum)

    val users = listOf(User(), User("A", 30), User("B"))
    val secondUser = secondItemOfList<User>(users)

    println(secondUser)

    println("----Testing with Gson----")

    val user1 = User("user1", 12)
    val user2 = User("user2", 14)
    val user3 = User("user3", 17)

    println(Gson().toJson(user1))
    println(user1.toString())

    val allUsers = listOf(user1, user2, user3)

    val gotUser = Gson().fromJsonReify<User>(Gson().toJson(user1))
    println(gotUser.age)

//    val user = Gson().fromJson<User>(user1.toString()) // This will throw an error.
//    println(user.age)

}