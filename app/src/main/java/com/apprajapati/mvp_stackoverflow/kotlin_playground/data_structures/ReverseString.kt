package com.apprajapati.mvp_stackoverflow.kotlin_playground.data_structures

fun main() {

    val str = "ajay"

    println(str.reversed())

    val builder = StringBuilder(str)
    println("original  $builder")

    for (i in 0..<builder.length / 2) {
        val position = builder.length - 1 - i

        val temp = builder[i]
        builder[i] = builder[position]
        builder[position] = temp

    }

    println("reversed $builder")
}

/*
#include <iostream>
#include <string>

using namespace std;

int main()
{
    string str = "Hello World" ; //5/2 = 1
    cout << "length " << str.length() / 2 << endl;

    for(int i = 0; i < str.length() / 2; i++) {
        int position = str.length() - 1 - i; // last

        cout <<"position " << position << endl;

        char temp = str[i]; // H
        str[i] = str[position]; //
        str[position] = temp;
    }

    cout << str << endl;

    return 0;
}
 */