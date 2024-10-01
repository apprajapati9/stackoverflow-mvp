package com.apprajapati.mvp_stackoverflow.design_patterns

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.log

/* Observer pattern - Widely used in Android.
- Defines a subscription mechanism
- Notify multiple objects simultaneously

 */

interface EventListener {
    fun update(eventType: String?, file: File?)
}

class EventManager(vararg operations: String) {
    private val subscribers = hashMapOf<String, ArrayList<EventListener>>()

    init {
        for( operation in operations){
            subscribers[operation] = arrayListOf()
        }
    }

    fun subscribe(eventType: String?, listener: EventListener){
        val users = subscribers[eventType]
        users?.add(listener)
    }

    fun removeSubscribe(eventType: String?, listener: EventListener){
        val users = subscribers[eventType]
        users?.remove(listener)
    }

    fun notifySubscribers(type : String?, fileName : File?){
        val subscribers = subscribers[type]
        subscribers?.let {
                for(sub in it){
                    sub.update(type, fileName)
                }
        }
    }
}

class Editor{
    var events  = EventManager("open", "save")

    private var file: File? = null

    fun openFile(filePath: String) {
        file = File(filePath)
        events.notifySubscribers("open", file)
    }

    fun saveFile(){
        file?.let {
            events.notifySubscribers("save", file)
        }
    }
}

class EmailNotificationListener(private val email: String) : EventListener {

    override fun update(eventType: String?, file: File?) {
        println("Email to $email, someone has performed '${eventType?.uppercase()}' operation on file ${file?.name}")
    }

}

class LogOpenListener(fileName: String) : EventListener {
    override fun update(eventType: String?, file: File?) {
        println("Save to log $file, someone has performed '${eventType?.uppercase()}' operation on file ${file?.name}")
    }
}

class ObserverTest {
    @Test
    fun testObserverPattern(){
        val editor = Editor()
        val logListener = LogOpenListener("path/to/file/ajay.txt")
        val emailListener = EmailNotificationListener("test@ajay.com")

        //multiple OPEN operation so everyone with that operation will be notified
        editor.events.subscribe("open", logListener)
        editor.events.subscribe("open", emailListener)

        editor.events.subscribe("save", emailListener)

        editor.openFile("test.txt")
        editor.saveFile()
    }
}