package com.apprajapati.mvp_stackoverflow.design_patterns.memento

import org.junit.jupiter.api.Test


/* Memento design pattern : It is used to save and restore previous state without revealing
    implementation details.

   3 components are there:
     Memento - stores the state
     originator- create the states
     caretaker - decides to save or restore the state

Example: Text editor- undo/redo command.
 */

enum class STATE {
                 INIT,
    UNDO,
    REDO
}

data class Memento(val state: STATE)

class Originator(var state: STATE) {
    fun createMemento() = Memento(state)
    fun restoreMemento(memento: Memento) {
        state = memento.state
    }
}

class CareTaker {
    private val mementoList = arrayListOf<Memento>()

    fun saveState(state: Memento) {
        mementoList.add(state)
    }

    fun restore(index: Int): Memento {
        return mementoList[index]
    }

    fun popLast(): Memento{
        mementoList.removeLast()
        return mementoList.last()
    }

    fun reset(): Memento{
        val first = mementoList.get(0)
        mementoList.clear()
        mementoList.add(first)
        return first
    }

    fun allStates(){
        for(i in mementoList){
            println(i.state.toString())
        }
    }
}

class MementoTest{
    @Test
    fun testMemento(){
        val originator = Originator(STATE.INIT)
        val careTaker = CareTaker()

        careTaker.saveState(originator.createMemento())

        originator.state = STATE.UNDO
        careTaker.saveState(originator.createMemento())

        originator.state = STATE.REDO
        careTaker.saveState(originator.createMemento())

        println("current state: ${originator.state}")

        originator.restoreMemento(careTaker.restore(1))
        println("current restored state: ${originator.state}")

        originator.restoreMemento(careTaker.popLast())
        println("current restored state: ${originator.state}")

        originator.state = STATE.UNDO
        careTaker.saveState(originator.createMemento())

        originator.state = STATE.REDO
        careTaker.saveState(originator.createMemento())
        println("current states: ${careTaker.allStates()}")

        originator.restoreMemento(careTaker.reset())
        println("current restored state: ${originator.state}")

    }
}