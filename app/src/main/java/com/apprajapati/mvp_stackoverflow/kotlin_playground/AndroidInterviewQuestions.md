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

That’s a common symptom of not understanding Android’s launch modes — and it can seriously mess up
your user flow.

Let’s break them down with real-world relevance 👇

💡 First, What is a Task?
In Android, a task is a stack of activities that users interact with while performing a certain job.

🧠 Think of it like a tab in Chrome —
Each task has its own activity stack. Hitting the back button pops the current activity from that
stack.

🟢 1. standard – The Default Mode
✅ A new instance is always created, even if one already exists.

🧭 Best for: Screens like product pages, forms, or anything that can exist multiple times.
android:launchMode="standard"

Example: User taps the same notification twice → 2 activity instances on the stack.

🟡 2. singleTop – Avoid Duplicates on Top
✅ If the activity is already at the top, no new instance is created.
✅ Instead, onNewIntent() is called.

🧭 Best for: Notifications, deep links, or repeated navigation to the same screen.
android:launchMode="singleTop"

Example: You’re already on Home → Tap the Home notification → Same instance reused.

🔵 3. singleTask – One Instance Per Task
✅ If the activity exists in the task, it’s brought to the front.
🚨 All activities above it are cleared.

🧭 Best for: Dashboards, main entry points, login redirects.
android:launchMode="singleTask"

Example: Login → Profile → Dashboard
Tap a dashboard notification → Clears login and profile, shows only dashboard.

🔴 4. singleInstance – Lives in Its Own Task
✅ Only one instance exists, in a separate task.

🧭 Best for: Use cases like incoming calls or PiP where isolation is needed.
android:launchMode="singleInstance"

Think: It opens in its own private Chrome tab — no other screens allowed in that tab.

⚠️ In Jetpack Compose Apps
Most modern apps use a single-activity architecture, so launch modes primarily matter for:
✅ Preventing duplicate launches (from notifications or deep links)
✅ Managing app resume behavior

In addition to using launch modes, you can also augment or override behavior at runtime using intent
flags like FLAG_ACTIVITY_NEW_TASK, FLAG_ACTIVITY_CLEAR_TOP, etc.

👉 No need to set launch modes unless you have a specific use case (like preventing duplicate
activity instances from notifications or deep links). For many apps, the default standard launch
mode works perfectly fine.

💬 Final Tip
If you’re seeing weird back stack behavior, check your launch modes. Misusing them can cause a messy
UX — and frustrated users.

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

Resource playlist [Flow basics](https://youtube.com/watch?v=ZX8VsqNO_Ss)
When to use what? [Hot flow vs Cold flow](https://youtube.com/watch?v=M8YtV47kaqA)

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

## FOR VIEWS

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

## FOR VIEWGROUPS

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

## Custom touch handling

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

### Q44. Why avoid GlobalScope?

GlobalScope is a poor practice and it should be avoided. 🚫
Let me explain why, and what should we use instead.

GlobalScope is just an empty placeholder for a lack of scope. It has no job, so it cannot be
cancelled. It is an object, so it cannot be configured or overridden for testing. Those are its key
problems.

Whatever we start on a scope, like viewModelScope or lifecycleScope on Android, is associated to
some view, and cancelled when this view is destroyed.
❗GlobalScope is never cancelled, not even if our application is destroyed, what leads to memory
leaks. ❗

// Memory leak!
fun getMoviesGenreList() = GlobalScope.launch {
_movieGenreUiState.value = movieGenresUseCase()
}

// OK
fun getMoviesGenreList() = viewModelScope.launch {
_movieGenreUiState.value = movieGenresUseCase()
}

To test a coroutine we need to start it in a test scope. That is why a scope should be injected, so
we can override it for testing.
❗GlobalScope cannot be overridden, so its coroutines have limited testability. ❗

Most scopes are configured. They can be configured with a dispatcher or an exception handler.
GlobalScope does not allow that.

single { CoroutineScope(SupervisorJob() + Dispatchers.Default) }

🚫 So in general, we should avoid using GlobalScope.
✅ On Android views or view models we should use scopes from KTX, on other classes we should use an
injected scope. Even if we do not need to use any advantages of a custom scope now, it is better to
use it, to be able to use it later.

### Q45 Can you have a Fragment without view?

Headless fragments, have one really useful feature, they can be retained by the FragmentManager
across configuration changes. Since they do not have any UI related to them, they do not have to be
destroyed and rebuilt again when the user rotates the device for example. In order to activate this
behavior, one just has to set the retained flag of the Fragment when it is initialized. This can be
done in the onCreate method of the fragment.
`setRetainInstance(true)` in OnCreate method.

Understand retain of Fragment better
here [Stackoverflow](https://stackoverflow.com/questions/11182180/understanding-fragments-setretaininstanceboolean)

More
info [Headless fragment](https://stackoverflow.com/questions/11531648/what-is-the-use-case-for-a-fragment-with-no-ui)

### Q46. How to make your composable lifecycle aware?

Composable lifecycle only consists of 3 stages.

1. Enter composition
2. recomposition
3. leave the composition

If you want to make composables aware of activity's lifecycle then do the following.

1. get the lifecycleOwner reference
   `val lifecycleOwner = LocalLifecycleOwner.current`

2. Use `DisposableEffect` to act on that and capture events

```kotlin
DisposableEffect(lifecycleOwner) {

    val lifecycleEvents = LifecycleEventObserver { source, event ->
        when (event) {
            Lifecycle.Event.ON_CREATE -> TODO()
            Lifecycle.Event.ON_START -> TODO()
            Lifecycle.Event.ON_RESUME -> TODO()
            Lifecycle.Event.ON_PAUSE -> TODO()
            Lifecycle.Event.ON_ON_DESTROY -> TODO()
            Lifecycle.Event.ON_ON_ANY -> TODO()
        }
    }

    lifecycleOwner.lifecycle.addObserver(lifecycleEvents) //This will now trigger based on activity's lifecycle events

    onDispose {
        lifecycleOwner.lifecycle.removeObserver(lifecycleEvents)
    }
} 
```

Using above steps, you can capture lifecycle method triggered and act or execute a piece of code
based on lifecycle of activity, making your composable lifecycle aware.

### Q47 what are the ways to load initial data in app?

Watch video for information [WAYS to load initial data](https://www.youtube.com/watch?v=mNKQ9dc1knI)

### Q48. Guide to improving compose performance.

1. Avoid premature optimizations
2. Initial composition time. It is not something you can escape from. Layout performance and
   Recomposition
3. In compose, we go through 3 steps 1 composition 2 Layout and then 3 Draw
   with recomposition, you are skipping composition phase but that could trigger as well depending
   on the stability of types of data. Stability allows us to skip execution of a composable body. It
   is immutability.

how to generate compiler metrics / report to see how many compose functions are skippable.

```kotlin
kotlinOptions {
    freeCompilerArgs += [
        "-P",
        "plugin:...kotlin:reportsDestination=" + buildDir.absolutePath + "/compose_metrics"
    ]
}
```

More information [Detailed talk on Compose performance](https://youtube.com/watch?v=h1xTtTl0k7Q)

### Q49. Working with files in Android

`File("path/fileName.txt")` points to a file at runtime, from java.io.File
exists() - check whether a given file exists or not.
createNewFile() - create a file with fileName.txt
mkdirs() - makes entire path with folders, including fileName.txt thus should have separate fileName
if you want to create a file. i.e two folders will be created "path" and "fileName.txt", to create
file, use `createNewFile` method on specific file name like File("name.txt")

`listFiles()?.forEach` to get all files in a current directory if file path is for example
File(".")

just for knowledge purpose:
drwxr-xr-x

1. d - directory, - regular file, l symbolic link, c character device
2. rwx - owner or user permission  (r-read,w-write,x-execute)
3. r-x - group
4. r-x - others

rwx - Read (100) binary 4 decimal, write (010) binary 2 decimal, execute (001) binary 1 decimal
4+2+1 = 7, 777 means public file to everyone including user.

FileInputStream = when we want to read from a file.
FileOutputStream = when we want to write to a file.
ByteArrayOutputStream = could be used for in data memory processing. i.e processing an image,
applying some filter or drawing something on the image.

### Q50. Ways to map Error handling class in Android

Usually you can do the following:

```kotlin
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(data: T? = null, message: String) : Resource<T>(data, message)
}
```

Better way to map is following

```kotlin

sealed interface Error  //just an interface

//Generic error handling.. 

typealias RootError = Error

sealed interface Result<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : Result<D, E>
    data class Error<out D, out E : RootError>(val error: E) : Result<D, E>
}

//USE CASE

enum class PasswordError : Error {
    TOO_SHORT,
    NO_UPPERCASE,
    NO_DIGIT
}
class UserDataValidator {
    fun validatePass(pass: String): Result<Unit, PasswordError> {
        if (pass.length < 8) {
            return Result.Error(PasswordError.TOO_SHORT)
        }
        val hasUppercase = pass.any { it.isUpperCase() }
        if (!hasUppercase) return Result.Error(PasswordError.NO_UPPERCASE)

        val hasDigit = pass.any { it.isDigit() }
        if (!hasDigit) return Result.Error(PasswordError.NO_DIGIT)

        return Result.Success(Unit)
    }
}

//You get type safety in error messages this way too and specific error message rather than string messages as Result.Error
class UserViewMode(private val userDataValidator: UserDataValidator) : ViewModel() {

    fun onLogin(pass: String) {
        when (val result = userDataValidator.validatePass(pass)) {
            is Result.Error -> {
                when (result.error) {
                    PasswordError.NO_DIGIT -> {

                    }
                    PasswordError.TOO_SHORT -> {

                    }
                    PasswordError.NO_UPPERCASE -> {

                    }
                }
            }
            else -> {

            }
        }
    }
}
```

Resource [Watch](https://youtube.com/watch?v=MiLN2vs2Oe0)

### Q51. Gestures in Compose

`clickable` modifier allows easy detection of a click, but it also provides accessibility
features and displays visual indicators when tapped( such as ripples).
There are less commonly used gesture detectors that offer more flexibility on a lower level like
PointerInputScope.detectTagGestures or PointerInputScope.detectDragGestures but don't include the
extra features.
All gesture recognizers

- detectTagGestures()
- detectDragGestures()
- detectVerticalDragGestures()
- detectHorizontalDragGestures()
- detectDragGesturesAfterLongPress()
- detectTransformGestures() - multi touch gestures - pinch, zoom, rotate

Gesture modifiers

- Modifier.clickable {} - just tap gesture
- Modifier.combinedClickable( onClick = .., onLongClick = ..., onDoubleClick = ... )
- Modifier.draggable(_) to detect horizontal and vertical drag gestures
- Modifier.scrollable() = includes logic for scrolling and flinging
- Modifier.transformable() = multi touch gestures.

How do we choose between recognizers and modifiers? = modifiers unlike recognizers does more than
just detecting gestures. In source code of modifier.clickable, it adds support for accessibility,
keyboard support, indication (to show ripples). In general, always use gesture modifiers and use
gesture recognizers only if there are good reasons to use.

Jetpack Compose provides different levels of abstraction for handling gestures. On the top level is
component support. Composables like Button automatically include gesture support. To add gesture
support to custom components, you can add gesture modifiers like clickable to arbitrary composables.
Finally, if you need a custom gesture, you can use the pointerInput modifier.

As a rule, build on the highest level of abstraction that offers the functionality you need. This
way, you benefit from the best practices included in the layer. For example, Button contains more
semantic information, used for accessibility, than clickable, which contains more information than a
raw pointerInput implementation. When it fits your use case, prefer gestures included in components,
as they include out-of-the-box support for focus and accessibility, and they are well-tested

There are many modifiers to handle different types of gestures:

- Handle taps and presses with the clickable, combinedClickable, selectable , toggleable, and
  triStateToggleable modifiers.
- Handle scrolling with the horizontalScroll, verticalScroll, and more generic scrollable modifiers.
- Handle dragging with the draggable and swipeable modifier.
- Handle multi-touch gestures such as panning, rotating, and zooming, with the transformable
  modifier.

Link to detailed documentation and
video [Check](https://developer.android.com/develop/ui/compose/touch-input/pointer-input/understand-gestures)

### Q52. When to use sealed interface/class?

`Sealed class` is used when you want to define a closed type hierarchy where all subclasses are
known
at compile time.

- You need to represent a closed set of types. All subclasses of a sealed class must be declared in
  the same file or package. This ensures that the hierarchy is exhaustive and cannot be extended
  outside the file or module. This makes when expressions exhaustive.
- You need state or properties. Sealed classes can have properties, constructors and state, making
  them ideal for modeling data with shared behavior or attributes.
- You want to enforce a specific structure: Sealed classes are often in scenarios like representing
  states e.g Loading, Success and Error or expressions e.g Add or Subtract.

```kotlin
sealed class Result<T : Any> {
    data class Success<T : Any>(val data: T) : Result<T>()
    data class Error<T : Any>(val error: String, val data: T? = null) : Result<T>()
    data object Loading : Result<T>()
}
```

`Sealed interface` is used when you want to define a closed set of types that implement a common
interface. It is useful when

- You want to define a contract. Sealed interfaces are ideal for defining a common API or behavior
  that multiple types can implement, while restricting the set of implementers.
- You need multiple inheritance. Unlike sealed classes, a type can implement multiple sealed
  interfaces, allowing for more flexible hierarchies.
- You want to avoid state or properties. Interfaces cannot hold state, so if you only need to define
  behavior or contracts, a sealed interface is a better choice.
- You want to enforce a closed set of implementers. Like sealed classes, interfaces restrict the set
  of implementers to those declared in the same file or module

Sealed class:

1. Can have state and properties
2. single inheritance (kotlin classes)
3. modeling data with shared state

Sealed interfaces:

1. Cannot have state or properties
2. multiple inheritance
3. defining contracts or behavior

Classes and interfaces in Kotlin are not only used to represent a set of operations or data; we can
also use classes and inheritance to present hierarchies through polymorphism. You can model a
network response using the following ways

```kotlin
//interface
interface Response
class Success(val data: String) : Response
class Failure(val exception: Throwable) : Response

//or using an abstract class
abstract class Response
class Success(val data: String) : Response
class Failure(val exception: Throwable) : Response

val result: Reponse = getSomeData()
when (result) {
    is Success -> handleSuccess(result.data)
    is Failure -> handleFailure(result.exception)
    else -> {} //This is required in case of interface and abstract class 
}
```

`The problem is that when a regular interface or abstract class is used, there is no guarantee that its defined subclasses are all possible subtypes of this interface or abstract class.`
Someone might define another class and make it implement Response. Someone might also implement an
object expression that implements Response.

A hierarchy whose subclasses are not known in advance is known as a non-restricted hierarchy. For
Response, we prefer to define a restricted hierarchy which we can do by using a sealed modifier
before a class or an interface.

When we use the `sealed` modifier before a class, it makes this class abstract already, so we don't
use the abstract modifier.

Few requirements for sealed classes:

- They need to be defined in the same package and module where the sealed class or interface is,
- They can't be local or defined using object expression

This means that when you use the sealed modifier, you control which subclasses a class or interface
has. The users of your library or module cannot add their own direct subclasses, thus making the
hierarchy of subclasses restricted.

[Read more](https://kt.academy/article/kfde-sealed)

Enum vs Sealed : Enum classes are used to represent a set of values. Sealed classes or interfaces
represent a set of subtypes that can be made with classes or object declarations. This is a
significant difference. A class is more than a value. It can have many instances and can be a data
holder.
Think of Response, if it were an enum class, it couldn't hold value or error. Sealed subclasses can
each store different data, whereas an enum is just a set of values.

```kotlin
//Sealed class use cases

sealed class MathOperation
class Plus(val left: Int, val right: Int) : MathOperation()
class Minus(val left: Int, val right: Int) : MathOperation()
class Times(val left: Int, val right: Int) : MathOperation()
class Divide(val left: Int, val right: Int) : MathOperation()

sealed interface Tree
class Leaf(val value: Any?) : Tree
class Node(val left: Tree, val right: Tree) : Tree

sealed interface Either<out L, out R>
class Left<out L>(val value: L) : Either<L, Nothing>
class Right<out R>(val value: R) : Either<Nothing, R>

sealed interface AdView
object FacebookAd : AdView
object GoogleAd : AdView
class OwnAd(val text: String, val imgUrl: String) : AdView
```

### Q53. in and out keyword in generics of Kotlin

Checkout `VarianceInKotlin.kt` and `Animals.kt` file in this project for the example.

Good to know:: All parameter types in Kotlin function types are contravariant, as the name of the in
variance modifier suggests. All return types in Kotlin function types are covariant, as the name of
the out variance modifier suggests.

in-positions = used as a parameter type.

Covariance (out modifier) is perfectly safe with public out-positions, therefore these positions are
not limited. This is why we use covariance (out modifier) for types that are produced or only
exposed, and the out modifier is often used for producers or immutable data holders. Thus, List has
the covariant type parameter, but MutableList must have the invariant type parameter.

Out-positions do not get along with contravariant type parameters (in modifier). If Producer type
parameters were contravariant, we could up-cast Producer<Amphibious> to Producer<Nothing> and then
expect produce to produce literally anything, which this method cannot do. That is why contravariant
type parameters cannot be used in public out-positions.
You cannot use contravariant type parameters (in modifier) in public out-positions, such as a
function result or a read-only property type.

```kotlin
class Box<in T>(
    val value: T // Compilation Error
) {

    fun get(): T = value // Compilation Error
        ?: error("Value not set")
}

//it is fine when these elements are private
class Box2<in T>(
    private val value: T
) {

    private fun get(): T = value
        ?: error("Value not set")
}

public interface Continuation<in T> {
    public val context: CoroutineContext
    public fun resumeWith(result: Result<T>)
}
```

This way, we use contravariance (in modifier) for type
parameters that are only consumed or accepted. A well-known example is
kotlin.coroutines.Continuation:

Read-write property types are invariant, so public read-write properties support neither covariant
nor contravariant types.

Every Kotlin type parameter has some variance:

- The default variance behavior of a type parameter is invariance. If, in Box<T>, type parameter T
  is invariant and A is a subtype of B, then there is no relation between Box<A> and Box<B>.
- The out modifier makes a type parameter covariant. If, in Box<T>, type parameter T is covariant
  and A is a subtype of B, then Box<A> is a subtype of Box<B>. Covariant types can be used in public
  out-positions.
- The in modifier makes a type parameter contravariant. If, in Box<T>, type parameter T is
  contravariant and A is a subtype of B, then Cup is a subtype of Cup. Contravariant types can be
  used in public in-positions.

In Kotlin, it is also good to know that:

- Type parameters of List and Set are covariant (out modifier). So, for instance, we can pass any
  list where List<Any> is expected. Also, the type parameter representing the value type in Map is
  covariant (out modifier). Type parameters of Array, MutableList, MutableSet, and MutableMap are
  invariant (no variance modifier).
- In function types, parameter types are contravariant (in modifier), and the return type is
  covariant (out modifier).
- We use covariance (out modifier) for types that are only returned (produced or exposed).
- We use contravariance (in modifier) for types that are only accepted (consumed or set).

1: This is also called mixed-site variance.

[Read more on variance in Kotlin](https://kt.academy/article/ak-variance)
[Variance limitations](https://kt.academy/article/ak-variance-limitations)

### Q54. How to handle network check in Android?

[Check internet connection](https://www.droidcon.com/2025/04/25/avoid-redundant-network-checks-in-android-smart-offline-aware-api-handling/)

### What is a type-safe navigation in compose navigation?

### Q How would you pass an intent from one app to another app?

### Q What is Mutex and what is the use case of that?

### Q Passing viewmodel for jetpack screen/ composable function

### Q Navigation in jetpack compose.

### Q Jetpack compose and implement login using Fingerprint?

### helpful key shortcuts in Android studio for increasing productivity

- Ctrl+Alt+T - Surround expression with
- Ctrl+shift+backspace - last edited place
- Ctrl+V --> Ctrl+shift+V = paste / paste history clipboard
- Ctrl + backspace , ctrl + delete - backward /forward deleting
- ctrl + shift + U -> capital or not,
- alt+ctrl+l -format code
- ctrl + e -> recently opened /used files
- alt+inert -> generate auto methods
- ctrl + q -> see documentation,
- ctrl + p -> params of function,
- ctrl + b -> jump to def
- alt + f1 -> quickly open current file in file tree
- ctrl + d -> next occurrence to skip and move next F3

#other important settings to optimize and remove cache
org.gradle.configureondemand=true
org.gradle.daemon=true
org.gradle.caching=true
kotlin.incremental=true
org.gradle.configuration-cache.parallel=true

