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

#### Q12. ViewModel with dependencies, how to?

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

#### Q13. 