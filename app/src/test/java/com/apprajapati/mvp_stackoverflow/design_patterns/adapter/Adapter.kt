package com.apprajapati.mvp_stackoverflow.design_patterns.adapter

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test


/* Adapter pattern

- Converts the interface of a class into another interface the client expects
- It can also convert data from one format to another

It is used extensively in Android - most notably in RecyclerView
 */

//Third party functionality

data class DisplayDataType(
    val index : Float,
    val data : String
)

class DataDisplay{
    fun displayData(data: DisplayDataType) {
        println(
            "data is ${data.index} : ${data.data}"
        )
    }
}

// Our data/ code
data class DatabaseData(
    val position: Int,
    val amount : Int
)

class DatabaseDataGenerator{
    fun generateData() : List<DatabaseData> {
        val list = arrayListOf<DatabaseData>()
        list.add(DatabaseData(33,22))
        list.add(DatabaseData(31,222))
        list.add(DatabaseData(3,212))
        return list
    }
}

interface DataConverter {
    fun convertData(data: List<DatabaseData>) : List<DisplayDataType>
}

class Adapter(val display: DataDisplay) : DataConverter {

    override fun convertData(data: List<DatabaseData>): List<DisplayDataType> {
        val returnList = arrayListOf<DisplayDataType>()
        for(i in data){
            val p = DisplayDataType(i.position.toFloat(), i.amount.toString())
            display.displayData(p)
            returnList.add(p)
        }

        return returnList
    }

}


class AdapterTest {

    @Test
    fun testAdapter(){
        val generator = DatabaseDataGenerator().generateData()
        val adapter = Adapter(DataDisplay())
        val convertData = adapter.convertData(generator)

        Assertions.assertThat(convertData.size).isEqualTo(3)
    }
}
