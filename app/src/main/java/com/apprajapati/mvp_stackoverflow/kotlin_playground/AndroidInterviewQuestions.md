package com.apprajapati.mvp_stackoverflow.kotlin_playground

# Android interview questions

## Android lifecycle

### Q.1 Describe the activity lifecycle

The android activity lifecycle describes the different states an activity goes through
during its lifetime, from creation to destruction.

    OnCreate() - This is the first method called when activity is created. It is where you 
    initialize the activity, layout and restore any saved instance state if there's any.
    It is called once during the activity lifecycle unless the activity is destroyed and recreated. 

    onStart() -  the activity becomes visible to the user but is not yet interactive. 
    This is called after onCreate() and before onResume().

    onRestart() - if the activity is stopped and then restarted (e.g the user navigates 
    back to it), this method is called before onstart(). 

    onResume() - the activity is in the foreground and user can interact with it. This is
    where you resume any paused UI updates, animations or input listeners. 

    onPause() 0 This is called when the activity is partially obscured by another activity
    (e.g dialog). The activity is still visible bot not in focus. Used to pause operations
    like animations, sensor updates or saving data. 

    onStop - the activity is no longer visible to the user. You should release resources
    that are not needed while activity is stopped such as background tasks or heavy objects.

    ondestroy - This is called before the activity is fully destroyed and removed from memory. 

### Q2. How saving and restoring state works in Android?

1. `Viewmodel` objects
2. Saved instance states within the following contexts:
   Jetpack compose: `rememberSaveable`
   Views: `onSaveInstanceState()`
   ViewModels: `SavedStateHandle`.
3. Local storage to persist the UI state during app and activity transitions.

ViewModel:
---------------------
Survive config. change? ->      Yes

Survive system init death? ->      No

Survive user complete activity                           
dismissal/onFinish()        ->      No

Data limitations -> complex objects are fine, types limited by avail memory

Saved instance state:
---------------------
Survive config. change? ->      Yes

Survive system init death? ->      Yes

Survive user complete activity                           
dismissal/onFinish()        ->      No

Data limitations -> only for primitive types and simple, small objects such as String.

    `Note: Saved instnace state includes onSavedInstance() and rememberSaveable APIs, 
        and SavedStateHandle as part of ViewModels.`

- Use `ViewModel` to handle configuration changes.
    - Viewmodel retains the data in memory, which means it is cheaper to retrieve compared to disk
      or network
    - ViewModel is associated with an activity (or some other lifecycle owner) 0 and it stays in
      memory during a config change
      and system automatically associates the Viewmodel with the new activity instance that results
      from the config change.
    - ViewModels are destroyed when `finish()` method is called on activity or fragment.
    - ViewModels don't survive system-init process death. To reload data after that,
      use `SavedStateHandle` API. Alternatively if data is related to the UI and doesn't need to be
      held in the ViewModel, then use `onSaveInstanceState()` in the View system
      or `rememberSaveable`
      in jetpack compose.

- Use Saved instance state as backup to handle system init process death
    - The process of saving and restoring instances happen on Main Thread, so if long running
      serialization can cause dropped frames and visual stutter. Don't use saved instance state to
      store large amounts of data such as bitmaps, nor complex data structures that require lengthy
      serialization or deserialization.

    - The API to use depends on where the state is held and the logic that it requires. For state
      that is used in business logic, hold it in a `ViewModel` and save it using `SavedStateHandle`.
      For state that is used in UI logic, use the `onSaveInstanceState` API in the view system
      and `rememberSaveable` in compose.

- `rememberSaveable` for jetpack compose
    - Persist state across recomposition `remember`
    - Persist across config change `rememberSaveable`
    - Hoist the state for reusability and testability, so don't save the state in the composable
      itself. Try to make a composable stateless, a composable that doesn't hold any state. You can
      use `State hoisting` for that purpose. It is just a programming pattern where you move the
      state to the caller of a composable. (Watch video for example) Basically you are separating
      state to a diff composable and using lambda to update state.
    - Use viewmodel with Livedata and observeAsState
    - `rememberSaveable` will automatically save any value that can be saved in a bundle, if not,
      you can use `Parcelize` and `MapSaver`, you can read about them in the links given below.

```kotlin
var name: String by rememberSaveable { mutableStateOf("") }
```

- onSaveInstance(outState: Bundle?)
    - onSaveInstanceState is not called when use explicitly closes the activity or other cases when
      finish is called
    - To restore state, both `OnCreate` and `onRestoreInstanceState(state: Bundle)` method receive
      the same Bundle. If using OnRestoreInstanceState, then always call super.onRestore.

For
more [saving states in Android](https://developer.android.com/topic/libraries/architecture/saving-states)

For
more [saving states in Android](https://developer.android.com/guide/components/activities/activity-lifecycle)
For
more [rememberSaveable for compose](https://developer.android.com/develop/ui/compose/state#restore-ui-state)
For more [Watch this video](https://youtube.com/watch?v=mymWGMy9pYI)

### Q3. What is an intent?

An intent in Android is an abstract description of an operation to be performed. Intents are
typically used to start an activity, send a broadcast, or initiate a service. Intents can also pass
data between components, making them fundamental part of Android component based architecture.

- Explicit intent and Implicit intent

```kotlin
    //Explicit intent
val intent = Intent(this, TargetActivity::class.java)
startActivity(intent)

//Implicit intent
val implIntent = Intent(Intent.ACTION_VIEW)
implIntent.setData(Uri.parse("https://www.apprajapati.com"))
startActivity(implIntent)
```

### Q4. Serialization and Parcelable

Both are mechanisms used to pass data between different components (such as activities or
fragments).

Serialization: Process of converting objects into a byte stream, so that it can be easily store in a
file, sent over a network or transferred to a different process.

Deserialization: Opposite of Serialization. Converting a byte stream back into an object.

Parcelable is the recommended approach for Android applications due to its better performance in
most uses cases. However, if performance is not a concern, then Serializable might be easier to
implement.

### Q5. What is data class in Kotlin?

A type of class specifically designed to hold and work with holding data. It is a class itself but
internally Kotlin provides default implementation of object methods.

1. equals() - compares two instances of the class for equality based on their `properties`.
2. hashCode() - Generates a hash code based on the properties.

### Q6. What's the extension function in Kotlin?

Is a way to add new functionality to existing classes without modifying their code directly.

```kotlin
fun Int.isEven(): Boolean {
    return this % 2 == 0
}

val number = 12
number.isEven() 
```

### Q7. Four components of Android

- Activities
    - Represents a single screen in an app. It is an entry point for interacting with the user and
      it handles UI and user interactions

- Services
    - a component that runs in the background to perform long running operations or to handle tasks
      that don't need user interaction.
    - Playing music in the background or downloading files while the user is using another app.
    - Started service- initiated by the app to perform a task (downloading a file)
    - Bound service - provides functionalities to other components (e.g app might bind to a service
      to stare data between activities)

- Broadcast receivers
    - It listens for system wide broadcast messages (intents) or messages from other apps. These
      broadcasts can be system events, like when the device is charging or app-specific events.
    - It can listen for changes in network connectivity, battery status or incoming messages.
    - Usage: Used to respond to events such as onReceive() when a broadcast message is sent like a
      notification when WiFi is enabled.

- Content providers
    - manages access to structured data (such as databases or shared files) and allows apps to share
      data with one another securely.
    - The contacts app in Android uses content provider to make contact information available to
      other apps such as messaging or email apps
    - Apps can access data in another app through a specific URI(Uniform Resource Identifier) to
      fetch or modify the data.

### Q8. Difference between Coroutines and Thread?

Coroutine is the idea of suspendable computations i.e the idea that a function can suspend its
execution at some point and resume later on. A coroutine is an instance of a suspendable
computation.

- One of the benefits is writing non blocking code is essentially the same as writing blocking code.
- A coroutine is a lightweight thread like entity, but doesn't tie up a real thread. When launched,
  it doesn't start executing immediately on the main thread (unless specified), and it doesn't block
  the thread it runs on. Instead, the work inside the coroutine is split into smaller units of work
  that can be suspended and resumed at appropriate points.
- Thread is a unit of execution within a process or execution flow that has its own memory and
  resources and switching between
  threads involves more overhead, leading to higher resource consumption when dealing with many
  threads

Read more [Async programming in Kotlin ](https://kotlinlang.org/docs/async-programming.html)
Read more [Coroutines documentation](https://kotlinlang.org/docs/coroutines-guide.html)

### Q8.What’s State Hoisting?

State hoisting in Jetpack Compose refers to a design pattern where you “hoist” the state up to the
caller or parent composable, allowing the parent to control the state while the child composable
only focuses on displaying the UI. This concept is inspired by React’s state management approach.
The main goal of state hoisting is to separate concerns, keeping UI components stateless and
promoting reusability and easier testing.

In state hoisting:

    State is managed in the parent composable.
    Events or triggers (like onClick, onValueChange) are passed from the child back to the parent, which updates the state.
    The updated state is then passed back down as parameters to the child, creating a unidirectional data flow.

Please check Q2's video link to get more information on state-hoisting.
It offers following benefits:

1. Single source of Truth
2. Reusability
3. Separation of concerns
4. Improved testability
5. Unidirectional data flow
6. Better control over lifecycle

### Q9. What are side effects in Compose?

A side effect refers to any operation that affects state outside the scope of the composable
function or persists beyond its recomposition. Since composable functions are designed to be pure
functions that simply render UI based on the current state, side effects are used when you need to
perform actions outside the composable function's lifecycle, like updating shared state, triggering
one-time events or interacting with external resources.

1. LaunchedEffect: Used for launching coroutines or suspend functions in a composable
    - Allows you to start a coroutine in response to certain key state change. It runs within the
      composition and will cancel and restart if the specified key changes, making it useful for
      onetime or reactive tasks, such as fetching data or handling animations
   ```kotlin
    @Composable
    fun screen(userId: String) {
        LaunchedEffect(userId) {
            // Runs when `userId` changes, or when entering the composition
            fetchDataForUser(userId)
        }
    } 
   ```
2. SideEffect: Used to perform non restartable side effects
    - It is invoked every time a composable successfully recomposes. It is used for performing
      lightweight, non restartable actions like updating a mutable shared object or logging.
    ```kotlin
    @Composable
    fun composable(screenName: String) {
        SideEffect {
            // Runs after each recomposition, ideal for analytics or logging
            logScreenView(screenName)
        }
    }
    ```
3. DisposableEffect: Used for effects that need cleanup
    - Used for actions that require both setup and cleanup, such as registering a listener or
      resource that should be released when the composition leaves the screen or is recomposed. This
      API lets you define onDispose bloc, which will invoked when the composable function's
      lifecycle is ended up.
    ```kotlin
    @Composable
    fun composableListener(listener: SomeListener) {
        DisposableEffect(listener) {
            listener.register()  // Called when entering the composition
            onDispose {
                listener.unregister()  // Called when leaving the composition
            } 
        }
    }   
   ```

### Q10. Coroutines suspend - how it works?

Good resource on Kotlin - [Kotlin](https://kt.academy/article)

### Q11. Diff between MVI and MVVM?

The key difference between two architectures is that in MVVM, the ViewModel is responsible for both
user actions and updating the state of the View. In MVI, the intent is a separate layer that
represents user actions, making it easier to reason about app behavior and handle edge cases.

By adapting VMI, you can create a more reactive, predictable and testable app while reducing
coupling between layers and improving overall maintainability of your codebase.MVI allows you to
model user interactions as intents.

### Q12. ViewModel with dependencies, how to?

If `ViewModel` takes `SavedStateHandle` type as a dependency, then ViewModel provider factory
isn't needed. For other dependencies, you must provide factory as follows:

```kotlin
     //How to use below method to init viewmodel.
val model: CoinsListViewModel = ViewModelProvider(
    this,
    ViewModelFactory(GetCoinsUseCase())
)[CoinsListViewModel::class.java]

//For a ViewModel with a use case dependency.
class ViewModelFactory(private val useCase: GetCoinsUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoinsListViewModel::class.java)) {
            return CoinsListViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}
```

For more
info [ViewModel with dependency](https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-factories)

### Q13. Animation in Android - good watch

Watch [Get Animated- Android Dev Summit '18](https://www.youtube.com/watch?v=N_x7SV3I3P0)

### Q14. Launch modes in Android

`launchMode` is used to give instructions to the Android OS about how to launch an activity.

SingleTask :

- If activity doesn't exist in an already created task, then it starts the new activity in a new
  task with the Activity's new instance at the root of that Task
- If an activity exits in an already created Task, the Task is brought forward with the activity's
  last state restored and this Activity receives the new intent through the onNewIntent() method
- Example A,B,C,D,E activities, A -> B (Navigate from A to B). B -> C, C -> D, D -> E. When we
  launch activity C, the state of the stack will be A->B->C. Here activity C is the old instance
  only that gets the extras data through onNewIntent(). And Activities D and E get destroyed.

### Q15. What is Suspend vs Blocking functions?

Suspend functions are the ones that do not block the thread and let it continue the execution.
Blocking functions using runBlocking{} will not move forward until execution with delay or long
running task is done.

Regular functions are blocking functions.
For suspend functions use suspend keyword, mainly for IO bound tasks i.e. network, file reading,
delay and other async tasks.

Check SuspendVsBlocking.kt

### Q16. Internal visibility modifier in Kotlin

It is used to restrict the visibility of a class, function, property or object to the module in
which it is defined. A module in Kotlin is typically defined as a project or a set or related
project that are compiled together. In other words, the internal visibility modifier makes a member
accessible on within the same module.

Purpose:
Encapsulation
Module boundary
API design: when some classes or methods only meant for internal usage and should not be accessed by
consumers of the module.

### Q17. `lateinit` and `lazy` in Kotlin

`lateinit` - useful in a scenario when we don't want to initialize a variable at the time of
declaration and want to initialize at some later point in time, but also make sure that we
initialize before we use.

`lateinit` keyword will allow compiler to check and throw UninitializedPropertyAccessException if
not initialized during compile time. It also has a way to check if `lateinit` variable is
initialized or not by `isInitialized` property.

```kotlin
    private lateinit var name: String
if (this::name.isInitialized) {
    //access name
    TODO()
} else {
    //init or do something else
    TODO()
}
```

It can only be used with var keyword. Common sense because property is given value after which means
it must be assignable.

`lazy` in Kotlin is useful in scenarios where we want to create an object inside a class, but that
creation is expensive and might lead to a delay in the creation of that object that is dependent on
that expensive object.

`lazy` can only be used with val keyword hence read only property.

```kotlin

class Employee(val id: Int, val name: String)

class User {
    private val employee: Employee by lazy { Employee(122, "Ajay") }
}
```

In above example, Employee will get initialized only when it is accessed for the first time, else it
will not initialized. Subsequent access of the object will return the same object. Initialized once
and reuse object.

### Q18. launch vs async

They are both used to start a coroutine.

launch : Fire and forget
async : perform a task and return a result.

`launch` returns a `Job` and doesn't carry any resulting value whereas the `async` returns an
instance of `Deferred<T>` which has an `await()` function that returns the result of the coroutine
like we have future in Java in which we do future.get() to get the result.

```kotlin

val deferredJob = GlobalScope.async(Dispatchers.Default) {
    return@async 10
}

val result = deferredJob.await()
```

Check AsyncWork.kt file to check how async is also used to perform multiple tasks together.

If any exception comes inside the `launch` block, it crashes the application if we don't handle
whereas that is not the case with `async`. It is stored inside the resulting `Deferred` object and
will not be delivered anywhere else. It will silently dropped unless we handle it.

- When we want to make two network calls in parallel, we can use `async`.
- With `async`, use supervisorScope with the individual try-catch for each task, when you want to
  continue with other tasks if one or some of them have failed.
- with `async`, use CoroutineScope with the top level try-catch, when you do not want to continue
  with other tasks if any of them have failed.
- The major difference is that a coroutineScope will cancel whenever any of its children fail. If we
  want to continue with the other tasks even when one fails, we go with the supervisorScope. A
  supervisorScope won't cancel other children when one of them fails.

### Q19. What is multidex in Android?

Mainly it is used to overcome the method reference limit and support large codebases. It is used
when an app has more than 65536 method references, which is the limit of the DEF (Dalvik executable
format)

A DEX file contains compiled code that is executed by the Android Runtime Environment. If an app
exceeds the limit of method references then it cannot fit in a single dex file and we can use
MultiDex to solve the problem.

```kotlin
class MultiDexApp : MultiDexApplication()

//OR in gradle build file

android {
    defaultConfig {
        // ...
        multiDexEnabled = true
    }
}
```

### Q20. What is ViewModel in Android?

`ViewModel` holds the state of the UI and exposes the state to UI. It is mainly used to persist data
during the configuration/ orientation changes. ViewModel is a lifecycle aware component of android
ecosystem to hold the state of UI. It is attached to the lifecycle of an Activity, so whenever
`onFinish()` is triggered then and then only it will be cleared otherwise it will persist in memory.

### Q21. what is init block in Kotlin?

Usually OOP languages allows you to write code inside primary constructor and Kotlin has a concise
way of writing primary constructor in class declaration itself, so when we want to write additional
code inside primary constructor we can make use of the `init{...}` block where we can write
statements and expressions that must be done right after primary constructor.

```kotlin
class User(firstName: String, lastName: String) { // Primary constructor

    init {
        val fullName = "$firstName $lastName"
        println(fullName)
    }
}
```

A class can have more than one init block and in that case, blocks are executed in the same order as
they appear in the class body considering the properties if there are any in between.

### Q22. What is a coroutine?

A framework to manage concurrency in a more performant and simple way with its lightweight thread
which is written on top of the actual threading framework to get the most out of it by taking the
advantage of cooperative nature of functions. It is more like a framework to manage threads and use
them efficiently.

Good read on coroutines - [Coroutines](https://outcomeschool.com/blog/kotlin-coroutines)

### Q23. Dispatchers in Coroutines?

Dispatchers help coroutines in deciding the thread on which the task has to be done. We use
coroutines to perform certain tasks efficiently. Coroutines run the task on a particular thread.
Coroutines take the help of dispatchers in deciding the thread on which the task has to be done.
Dispatchers are like Schedules in RxJava.

Types

- Dispatchers.Default
    - We should use to perform CPU-intensive tasks
        - matrix multiplication
        - doing operations on bigger list in the memory like sorting, filtering, searching etc.
        - Applying the filter on the Bitmap present in the memory, Not for reading the image file
          present on the disk
        - Parsing the JSON available in the memory, not by reading the JSON file on the disk
        - Scaling the bitmap already present in the memory
- Dispatchers.IO
    - Use to perform disk or network IO related tasks
        - any network operations like making a network call
        - downloading a file from the server
        - Moving a file from one location to another on disk
        - reading/ writing from/to a file.
        - making database query
        - Loading the shared preference
- Dispatchers.Main
    - Use to run a coroutine on the main thread of Android. Mainly at places where we interact with
      the UI and perform small tasks.
        - Performing all UI related tasks
        - Any small task like any operation on a smaller list present in the memory
- Dispatchers.Unconfined
    - A coroutine dispatcher that is not confined to any specific thread. It runs on the thread on
      which it was started and resumed on the thread that resumed it.
    - We should use this when we don't care where the coroutine will be executed.

### Q24. CoroutineScope vs SupervisorScope

- CoroutineScope will cancel whenever any of its children fail. Use this scope with top level
  try-catch, when we don't want to continue with other tasks if any of them have failed.
- SupervisorScope won't cancel other children when one of them fails. Use this scope with try-catch
  for each task, when we want to continue with other tasks if one or MORE of them have failed.

### Q25. What is CoroutineContext in Kotlin?

- CoroutineContext is an interface that helps us define the context or the environment in which a
  coroutine executes, using various elements.
- It helps us define
    - Dispatcher - helps in deciding the thread on which the task has to be done.
    - Job - it represents the lifecycle of a coroutine, including its cancellation and completion
      states.
    - CoroutineName - it helps us providing a name for the coroutine, hence useful for debugging.
    - CoroutineExceptionHandler - used to handle uncaught exceptions in coroutines.

```kotlin
    import kotlin.coroutines.CoroutineContext

GlobalScope.launch(
    Dispatchers.IO +
            Job() +
            CoroutineName("Ajay") +
            CoroutineExceptionHandler { }
) {
    //add logic for a task
}

// To create a context
val coroutineContext: CoroutineContext = Dispatchers.IO +
        Job() +
        CoroutineName("Ajay") +
        CoroutineExceptionHandler { }

GlobalScope.launch(coroutineContext)
```

### Q26. Convert a callback to coroutine, what do we need?

1. We need to create a suspend function
2. Use `suspendCoroutine` to suspend execution of a block, and it provides `continuation` object to
   resume execution at a later point
3. use `continuation.resume(result)` when triggered success
4. use `continuation.resumeWithException(throwable)` to throw error
5. if you want to support cancellable, then you can use `suspendCancellableCoroutine`

Read more [Callback to coroutine](https://outcomeschool.com/blog/callback-to-coroutines-in-kotlin)

### Q27. What is Flow?

Flow is an async data stream (generally comes from a task) that emits values to the collector and
gets completed with or without an exception. Components of Flow are : Flow Builder, Operator and
Collector.

- Flow Builder: purpose of this is to doing a task and emitting items/values. We can think of this
  as a producer of data.
- Operator - These help you transform the data from one format to another.
- Collector - Collector collects the items emitted using the Flow Builder. Think of this as a
  consumer of data.

```kotlin
flow {                  //Flow builder
    (0..10).forEach {
        emit(it)
    }
}.map {                   //Operator
    it * it
}.collect {    //Collector
    println("$it")
}
```

`flowOn` operator is used to control the thread on which the task will be done. It is equivalent of
`subscribeOn()` in RxJava.

Types of flow builders

1. flowOf() - It is used to create a flow from a given/fixed set of items.
2. asFlow() - extension function that helps to convert type into flows, can be used on Collections
3. flow {} - create a flow using suspendable lambda block. This allows you to emit values using
   emit(). Builder function to construct arbitrary flows from sequential calls to the emit function
4. channelFlow{} - creates flow with the elements using send provided by the builder.

check `Flow.kt`, `TerminalOp.kt`, `StateFlow.kt` for examples.

### Q28. Cold flow vs Hot flow?

Cold Flow

- it emits data only when there is a collector
- it does not store data
- it cannot have multiple collectors

HotFlow

- it emits data even when there's no collector
- it can store data
- it can have multiple collectors
- `SharedFlow` and `StateFlow` are hot flows as they propagates items to multiple consumers.
  `StateFlow` is a special shareFlow that maintain the current state by .value property.
- Live video feed is the example of hot flows as all viewers will get to see exact same timeline of
  video being played.
- You can use `stateIn` and `shareIn` to convert cold flow `flow{}` to hot flow.

Flow builders : flow and callbackFlow
Cold and hot flows: sharedIn , stateFlow and ShareFlow

```kotlin

//SharedFlow
flow {
    repeat(100) {
        delay(3000)
        Log.d("Flow", "***************************** ")
        Log.d("Flow", "emitting  = $it ")
        emit(it)
    }
    Log.d("Flow", "emission done ")
}.shareIn(scope, SharingStarted.Eagerly, replay = 1)


//StateFlow
flow {
    repeat(100) {
        delay(3000)
        Log.d("Flow", "***************************** ")
        Log.d("Flow", "emitting  = $it ")
        emit(it)
    }
    Log.d("Flow", "emission done ")
}.stateIn(scope, SharingStarted.WhileSubscribed(), initialValue = 1)

//SharingStarted.Eagerly - sharing starts immediately and never stops
//SharingStarted.Lazily - Sharing is started when first subscriber appears and never stops.

//whileSubscribed - starts emitting/sharing only when subscribers turns from 0 to 1, and stop sharing when the number of subscribers turns from 1 to 0, making it similar to liveData, but added benefit is that repeatOnLifeCycle and flowWithLifeCycle. 
```

SharedFlow and StateFlow : Which one to choose ? (Both are hot flows)

- I need to have latest value at any point of time using .value : StateFlow.

- More than latest value using replay : SharedFlow.

- Need repeated values : SharedFlow.

- To convert any flow to a `StateFlow`, use the `stateIn` intermediate operator.

- Use `shareIn` function returns a `SharedFlow`, a hot flow that emits values to all consumers that
  collect from it. A `SharedFlow` is a highly configurable generalization of `StateFlow`

### Q29. What if we don't want to collect stream of data when UI is not visible?

```kotlin

//#1 you can convert to live data using asLiveData() and observe
val flowOfNumbers = flow {
    repeat(20) {
        delay(3000)
        emit(it)
    }
}
repository.flowOfNumbers.asLiveData()


//#2 collecting on lifecycle events
viewLifecycleOwner.lifecycleScope.launch {
    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        appState.collect {
            when (state) {
                APPSTATE.LOADING -> {
                    //green indicator somewhere in UI 
                }

                APPSTATE.IDLE -> {
                    //do not turn the indicator to green
                }
            }
        }
    }
}
// OR

viewLifecycleOwner.lifecycleScope.launch {
    appState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED).collect {
        when (it) {
            is APPSTATE.LOADING -> {

            }
            is APPSTATE.IDLE -> {
                //do not turn the indicator to green
            }
        }
    }
}
```

Alternatively we can also get a `job` from a flow and can explicitly call `job.cancel()` to stop the
flow, but you have to make sure to call these methods `onStart` and `onStop` to make sure flow is
collected and stopped accordingly. More better solution is to convert it to `liveData` using
`asLiveData()` terminal operator on flow which supports lifecycle awareness.

### Q30. StateFlow vs SharedFlow?

StateFlow

- Needs an init value and emits as soon as the collector starts collecting
- `val stateFlow = MutableStateFlow(0)`
- Only emits the last known value
- it has the value property, we can check the current value. It keeps a history of one value that we
  can get directly without collecting.
- Doesn't emit consecutive repeated values, it emits value only when it is distinct from the
  previous one.
- Similar to liveData except for the lifecycle awareness. We must use `repeatOnLifecycle` scope with
  Stateflow to add lifecycle awareness to it, then it will become exactly like livedata.
- In Android, `StateFlow` is a great fit for classes that need to maintain an observable mutable
  state
- Unlike a cold flow built using the `flow` builder, a `StateFlow` is hot: collecting from the flow
  doesn't trigger any producer code.

SharedFlow

- doesn't need init value so does not emit any value by default.
- `val sharedFlow = MutableSharedFlow<Int>()`
- can be configured to emit many previous values using the replay operator
- it doesn't have the value property
- it emits all the values and doesn't care about the distinct value from the previous item. It emits
  consecutive repeated values also.
- Not similar to LiveData.
- You can create a SharedFlow without using `shareIn`
- `MutableSharedFlow` also has a `subscriptionCount` property that contains the number of active
  collectors so that you can optimize your business logic accordingly. `MutableSharedFlow` also
  contains a `resetReplayCache` function if you don't want to replay the latest information sent to
  the flow.

When we make a network call, and if orientation changes, then `StateFlow` will store the latest/last
value and store it, so another network call will not be made. In case of `SharedFlow` nothing will
get collected here as SharedFlow doesn't store any data. Have to make a new network call.

In case of showing SnackBar, orientation change should not trigger snack bar message again and in
this case, `SharedFlow` is useful because it will not show SnackBar again as intended.

Read
more [SharedFlow vs StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)

### Q31. Zip, merge, combine in Flow?

Check CombineZipMergeStateFlow.kt file for examples.

Zip - runs both flows in parallel and gives the results of both tasks in a single callback when both
tasks are completed.

### Q32. How to remove duplicates from an array/list in kotlin?

1. toSet() / toMutableSet()
2. distinct()
3. toHashSet()

```kotlin
    val list = listOf(1, 1, 3, 2, 3, 1, 1, 2, 3).toSet()  //maintains the original order of items
val list2 = listOf(1, 1, 3, 2, 3, 1, 1, 2, 3).distinct() //maintains original order of items
val list3 = listOf(1, 1, 3, 2, 3, 1, 1, 2, 3).toHashSet() // sorts it and doesn't maintain order.

println(list)
println(list2)
println(list3)

/* output:
[1, 3, 2]
[1, 3, 2]
[1, 2, 3]
    
 */
```

### Q33. launchIn operator?

It is a shortcut operator for two operations. First `scope.launch` and then `collect`

```kotlin
import kotlin.coroutines.EmptyCoroutineContext

val scope = CoroutineScope(EmptyCoroutineContext)
flow.launchIn(scope)

//above line does the following
scope.launch {
    flow.collect {}
}
```

`collect` is a suspending function and launchIn isn't. It means collect suspends coroutine until it
is
finished and code executes sequentially. `launchIn` twice will execute code concurrently. It is
similar to having two `launch` blocks in runBlocking code.

check Flow.kt file for examples.

using launchIn, code can be more concise like below example

```kotlin

val flow = listOf(1, 2, 3, 4)
viewModelScope.launch {
    flow.collect {
        println(number)
    }
}

//using launchIn
flow.onEach {
    prinln(it)
}.launchIn(viewModelScope)
```

### Q34. map operator in flow, what does it do?

It transforms the data into another form.
`inline fun <T,R> Flow<T>.map(crossinline transform: suspend (T) -> R) : Flow<R>`

From that you can see that it takes T type of Flow and returns flow of type R, so it transforms
data. This is useful when you want to get data and transform that data to UIState in order to show
different states for UI based on data received.

For example, list of stocks is received from the API, now using map, you can map it to
UiState.Loading, UiState.Success or UiState.Error.

```kotlin
//simple example of mapping Int to string
flowOf(1, 2, 3, 4, 5).map {
    "Emission $it"
}.collect {
    println(it) // will print String value "Emission $it" 
}
```

Read on diff operators of Flow [Flow] (https://flowmarbles.com/#map)

### Q35. Basic intermediate operators for Flow?

1. `filter{}` - filters based on condition on each emission
2. `filterNot{}` - opposite of filter. Only gets values that doesn't meet that condition.
3. `map` and `mapNotNull{}` - transforms data and make sure its not null.
4. `filterIsInstance<Type>()` - makes sure that certain type is filtered.
5. `take(count)` - will only allow given count number of emissions from flow. i.e if flow emits from
   1 to 5 int values, then only take(3) will only print from 1 to 3.
6. `takeWhile(condition)` - similar to filter but major difference is takeWhile will cancel the
   upstream flow if predicate (condition) is false. Filter will go through all list and filter based
   on condition whereas takeWhile will cancel emissions if condition isn't met. Think of it like
   emit/take while this is true, after that stop.
7. `drop(count)` - drops the number of given count emissions, useful in case when not interested in
   some initial values and only subsequent values.
8. `dropWhile(predicate)` - Keep dropping emissions until the condition is met.
9. `transform` - it allows us to emit values
    ```kotlin
    flowOf(1,2,3,4,5).transform {
        emit(it)
        emit(it*10) 
    }.collect {
        print(it) 
    }
   //output
   //10,20,30,40,50
    ```
   The major diff is that using transform you can emit diff values too, not just transformed
   original ones. In the block of transform, you get type `FlowCollect<Type>` as `it` instead of
   Type that we get in map i.e int.
10. `withIndex()` - collect will have data with index value. Data will be printed like
    `IndexedValue(index=1, value=10)` as we collect.
11. `distinctUntilChanged()` - only prints distinct values, same values will be ignored. i.e if we
    have a flow of (1,1,2,3,4,1), then values printed will be (1,2,3,4,1). Last 1 will be printed
    because it is distinct from the last value.
12. `cancellable()` will ensure that flow is active using `ensureActive()` and if not, flow is
    cancelled.

### A36. How to handle exception in Flows?

You can surround flow collect statements with try-catch or you can also use `catch()` operator.
`onCompletion` will also return cause which will be null if there's no exception. But all exceptions
are travelled up from collect so surrounding collect{} block, we can handle exceptions.

`try-catch` is a traditional way of handling it and using `catch()` operator is a declarative way of
handling exceptions in flows.
`catch()` block gives throwable which we can use to know what type of exception is thrown. Important
to note that `catch()` handles exceptions from upstream, so ideal position to place this operator is
right above `collect()` so all exceptions thrown upstream can be caught in `catch()` block.

The best way to handle exceptions happening in collect block is to have `onEach` block and then have
`catch` block, it will make sure that all emissions are without exceptions and ready to be
collected.

i.e if exception is thrown in map{} operator of flow and catch block is above that map{}, then that
exception will not be handled. Map{} block must be above catch block.

Also in `catch` block we can emit different value to handle the exception. You can also use that
block as a fallback flow. When we have two sources of data, in case of one fails, we can use
different flow and emit/emitAll inside `catch` block.

```kotlin
private fun stockFlow(): Flow<String> = flow {
    emit("Apple")
    emit("Google")
    emit("Netflix")
}

val stockFlow = stockFlow()

stockFlow.onCompletion {
    if (it == null) { // cause as it
        println("Flow completed successfully")
    } else {
        println("Flow completed exceptionally with cause $it")
    }
}.onEach {
    throw Exception("Exception thrown")
    println("Collected $it")
}.catch { //throwable as it
    println("Handled exception $it")
}.collect {
    println(it)
}
```

### A37. how to recollect flow in case of exception?

Using `retry` which will give us exception cause as it, we can perform retry of producer code.
`retryWhen` offers attempt and exception, so based on attempts, we can write more better logic for
delays in subsequent retrying of flow when it fails.

```kotlin
flow<String> {

}.retryWhen { //to params are offered, cause, attempt ->  in this lambda function
    //it as cause
    delay(1000 * (attempt + 1)) // attempt
    cause is Exception // Predicate for retryWhen
}
```

### Q38. What problems we might face when using Flow instead of LiveData?

1. Flow producer continues to run when the app is in the background
2. Activity receives emissions and renders UI when it is in the background
3. Multiple collectors create multiple flows
4. Configuration change restarts the flow

We can get a `job` from lifecycleScope.launch in which we are collecting flow, and then cancelling
the job using `job.cancel()` in onStop; how ever, that is a lot of boilerplate code. We can use
`repeatOnLifecycle` as follows.

Alternatively we can also use `.flowWithLifecycle` to collect flow.

Solution for problem 1 and 2.
=============================

```kotlin
lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewmodel.flow.collect {
            //collect flow here, such as uiState.
        }
    }
}

//In fragment, you use it like this in onViewCreated
viewLifecycleOwner.lifecycleScope.lauch {
    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        //collect flow here.
    }
}

// Using flowWithLifecycle
lifecycleScope.launch {
    viewmodel.flowOfUiState
        .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
        .collect {
            //collect and render state.
        }
}
```

===== Cold flow =====

- it becomes active on collection
- it becomes inactive on cancellation of the collecting coroutine
- emit individual emissions to every collector, so if you have two launch blocks collecting, then
  both gets all values from flow.

```kotlin
// #2 - inactive on cancellation
fun coldFlow() = flow {
        emit(1)
        delay(1000)

        emit(2)
        delay(1000)

        emit(3)
    }

suspend fun main(): Unit = coroutineScope {
    var job = launch {
        coldFlow().onCompletion {
            println("Flow of collector 1 completed")
        }.collect {
            print("collected $it")
        }
    }
    delay(1500) //delay is less than time needed for flow completion
    job.canelAndJoin()

    //OUTPUT - it will become inactive after emitting 2, value 3 will not be emitted.
}
```

======= Hot Flow =======

- are active regardless of whether there are collectors
- stay active even when there is no more collector
- emissions are shared between all collectors

Check HotFlow.kt for example.

Solution for problem #3 and #4
==============================
You must use `SharedFlow` to mitigate the issue 3 and 4, and you can use that by using `shareIn`
operator.
Signature of SharedFlow
`fun <T> Flow<T>.shareIn(scope: CoroutineScope, started: SharingStarted, replay: Int = 0) : SharedFlow<T>`

```kotlin
//converting cold flow into hot flow
val flowOfUiState: Flow<UiState> = repository.latestStocklist
    .map {
        UiState.Success(it)
    }.onStart {
        emit(UiState.Loading)
    }.shareIn(
        scope = viewmodelScope, // for viewModels
        started = SharingStarted.WhileSubscribed, // emission only happens when there's a collector
        replay = 1
    )
```

The above solution will make sure that multiple collectors will not make network calls to get
values. One network call and all collectors will get the values as it is `sharedFlow`.
Due to `WhileSubscribed`, configuration change will cause the flow to not have any collector and
stop the flow and flow will be restarted again with a network call. To fix this issue, you should
pass `stopTimeoutMillis` which will wait for given time before stopping as follows:
`SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)`

Without passing `replay`, configuration change will have no state and thus could show blank screen,
so you should pass `replay` option to indicate that flow should emit its most recent emission on a
new flow collector. Think of it like, how many emissions are replayed? when there's a new collector.

### Q39. When to use SharedFlow and StateFlow?

SharedFlow has no initial value, StateFlow does.
Replay cache is customizable in SharedFlow, StateFlow is fixed to 1.
emission of subsequent equal values is possible in SharedFlow, no in Stateflow.
StateFlow will only emit changes that are new, not same as before.

- Whenever we want to use a hot flow, use a `StateFlow` by default. Only for special requirements,
  switch to `SharedFlow`.
- `StateFlows` are more efficient when used for state
- `StateFlows` provide convenient option to read and write its value in a non-suspending fashion by
  synchronously accessing the .value property.

### Q40. What are channels?

Deferred (withheld or delayed for or until a stated time) values provide a convenient way to
transfer a single value between coroutines. Channels provide a way to transfer a stream of values.

Unlike a queue, a channel can be closed to indicate that no more elements are coming. On the
receiver side it it is convenient to use a regular for loop to receive elements from channel.

```kotlin
val channel = Channel<Int>
launch {
    for (x in 1..5) channel.send(x * x)
    channel.close()  // to indicate done sending
}
repeat(5) { println(channel.receive()) }
```

Use of channels:

- Use channels to stream data asynchronously between coroutines. Such as streaming sensor data or
  real-time chat applications
- Employ channels for communication between the main UI thread and background coroutines. This
  ensures that time consuming tasks like database operations or file downloads are executed off the
  UI thread, preventing UI freeze.
- Use channels for communication and coordination between concurrently running coroutines. Channels
  can facilitate the exchange of information between parallel tasks, promoting efficient and
  synchronised execution.
- Implement a lightweight event bus using channels to facilitate communication between different
  components in an application. This is useful for decoupling components and allowing them to react
  to specific events.
- Leverage channels to control the flow of data between coroutines. This can be helpful when
  managing the rate at which data is processed to avoid overwhelming system resources.
-

### Q41. Side effects in jetpack compose?

The purpose of side effects in Jetpack compose is to allow for the execution of non-UI related
operations that change the state of the app outside of a composable function in a controlled and
predictable manner. Side effects such as updating a database or making a network call, should be
kept separate from the UI rendering logic to improve the performance and maintainability of code.

`SideEffect` is a composable function that allows to execute a side effect when its parent
composable
is recomposed. A side effect is an operation that does not affect the UI directly, such as logging,
analytics or updating external state. This function is useful for executing operations that do not
depend on the composable's state or props. Triggers when parent composable is recomposed.

- Logging and analytics
- Performing one time initialization such as loading data from a file or initializing a library.

`LaunchedEffect` is a composable function that executes a side effect in a separate coroutine scope.
This function is useful for executing operations that can take a long time, such as network calls or
animations, without blocking the UI thread. `key` parameter in `LaunchedEffect` is used to identify
the instance and prevent it from being recomposed unnecessarily.

- fetching data from a network
- image processing, updating database

`DisposableEffect` is a composable function that executes a side effect when parent composable is
first rendered and disposes of the effect when the composable is removed from the UI hierarchy. This
function is useful for managing resources that need to be cleaned up when composable is no longer in
use, such as event listeners and animations. Triggers on first composition or key change.

- adding and removing even listeners
- Starting and stopping animations
- bind and unbind sensor resources such as Camera, Location managers
- managing database connections

### Q42. Scope functions?

`run, let, apply, also, with`, all these functions are used for switching the scope of the current
function or variable. They are used to keep things that belong together in one place (mostly
initializations)

`run` lets you return anything you want and re-scopes the variable it is used on to `this`.
`apply` will let you initialize variables of a particular object or instance. It is useful as a
replacement for the Builder pattern, and if you want to re-use certain configurations.

`let` mostly used to avoid null checks, but also can be used as a replacement for run.
`also` use it when you want to use `apply`, but don't want to shadow `this` object.

### Q43. Composable functions?

1. Composable functions can execute in any order based on priorities.
2. Composable functions can run in parallel.
3. Recomposition skips as much as possible and it is optimistic.
4. Composable functions might run frequently.

### Q43. How Android handles touches?

- Each user touch event is wrapped up as a MotionEvent
- Describe's user's location
    - ACTION_DOWN,
    - ACTION_UP,
    - ACTION_MOVE
    - ACTION_POINTER_DOWN
    - ACTION_POINTER_UP
    - ACTION_CANCEL
- Event metadata is included
    - Touch location
    - Number of pointers (fingers)
    - Event time
- A gesture is defined as beginning with ACTION_DOWN and ending with ACTION_UP

1. Event start at the Activity with `dispatchTouchEvent()`, first method that will get called, if
   you want to check, we can implement and monitor them how it works. Shouldn't change anything
   because it traverse down through view hierarchy towards the children views.
2. Event flow top down through views
    - Parents (ViewGroups) dispatch event to their children
    - can intercept events any time
3. Events flow down the chain ( and back up) until consumed
    - Views must declare interest by consuming ACTION_DOWN
    - Further events not delivered for efficiency
4. Any unconsumed events end at the Activity with onTouchEvent
5. Optional external `onTouchListener` can intercept touches on any View/ViewGroup

Activity's `onTouchEvent` is the last place/method to consume a touch on screen and if children or
viewGroups consume that event, then it will not be visited so not the Activity is not the place to
monitor touch events.

#### FOR VIEWS

- Activity.dispatchTouchEvent()
    - always first to be called
    - sends event to root view attached to Window
    - onTouchEvent()
        - called if no views consume the event
        - always last to be called
- View.dispatchTouchEvent()
    - sends event to listener first, if exists
        - View.OnTouchListener.onTouch()
    - if consumed, process the touch itself
        - View.onTouchEvent()

#### FOR VIEWGROUPS

- ViewGroup.dispatchTouchEvent()
    - Check onInterceptTouchEvent(), primary use case is scrolling
        - check if it should supersede children
    - For each child view, in reverse order they were added
        - if touch is relevant (inside view), child.dispatchTouchEvent()
        - if not handled by previous, dispatch to next view
    - Handle touch directly (same as view)
- Touch interception onInterceptTouchEvent returns true
    - Passes ACTION_CANCEL to active child
    - all future events handled directly by ViewGroup
- Child view can call requestDisallowTouchIntercept() to block onInterceptTouchEvent() for the
  duration of the current gesture.
    - flag is reset by fragment on each new gesture (ACTION_DOWN)

Example of method calls if you have a view inside of FrameLayout and it doesn't consume touch event.
If finger comes down and no view is interested in consuming that touch event.

--> ACTION_DOWN:

1. Activity.dispatchTouchEvent()
2. ViewGroup.dispatchTouchEvent()     // FrameLayout
3. View.dispatchTouchEvent()       // View -- Travel back up
4. View.onTouchEvent //View
5. ViewGroup.onTouchEvent()    //FrameLayout
6. Activity.onTouchEvent()

--> ACTION_MOVE/UP:

1. Activity.dispatchTouchEvent()
2. Activity.onTouchEvent()

In case, View is interested and consumes the event, this would be the call order

--> ACTION_DOWN:

1. Activity.dispatchTouchEvent()
2. ViewGroup.dispatchTouchEvent()
3. View.dispatchTouchEvent()
4. View.onTouchEvent()  //Interested and consumed up event.

--> ACTION_UP/MOVE:

1. Activity.dispatchTouchEvent()
2. ViewGroup.dispatchTouchEvent()
3. View.dispatchTouchEvent()
4. View.onTouchEvent()  //Interested and consumed up event.

Resource [How touch system works in Android](https://youtube.com/watch?v=EZAoJU-nUyI)

#### Custom touch handling

- Handling touch events
    - Subclass override `onTouchEvent()`
    - provide an `onTouchListener`
- consuming events
    - Return true with ACTION_DOWN to show interest
        - even if you aren't interested in ACTION_DOWN, return true
    - For other events, returning true simply stops further processing
- useful constants available in ViewConfiguration
    - getScaledTouchSlop()
        - distance move events might vary before they should be considered drag
    - getScaledMinimumFlingVelocity()
        - speed at which the system considers a drag to be a fling
    - getScaledPagingTouchSlop()
        - touch slop used for a horizontal paging gesture (i.e ViewPager)
    - Display values scaled for each device's density


- Forward touch events i.e clicking on button clicks another one
    - call target's dispatchTouchEvent()
    - avoid calling target's onTouchEvent() directly
- Stealing touch events (ViewGroup)
    - subclass to override onInterceptTouchEvent()
    - return true when you want to take over for the ViewGroup
        - All subsequent events for the current gesture will come to your onTouchEvent directly
        - onInterceptTouchEvent() will no longer be called for each event (one shot redirection)
    - any current target will receive ACTION_CANCEL

Custom Touch handling Warnings

- call through to super whenever possible
    - View.onTouchEvent() does a lot of state management (pressed, checked, etc) that you will lose
      if you capture every touch
- protect ACTION_MOVE with slop checks
- Always handle ACTION_CANCEL
    - container views with action (like scrolling) will steal events and you will likely need to
      reset state
    - remember after CANCEL, you will get nothing else.
- Don't intercept events until you are ready to take them all
    - intercept cannot be reversed until the next gesture.

### Q Can you have a Fragment without view?

### Q How would you pass an intent from one app to another app?

### Q What is Mutex and what is the use case of that?

### Q Passing viewmodel for jetpack screen/ composable function

### Q Navigation in jetpack compose.

### Q Jetpack compose and implement login using Fingerprint?




