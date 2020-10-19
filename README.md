Hilt NavGraph View Models Issue
===

## Overview

This project demonstrates an issue with Hilt injecting SavedState via navGraphViewModels.  
Recommended way [1](https://github.com/google/dagger/issues/1935#issuecomment-646106066), [2](https://developer.android.com/training/dependency-injection/hilt-jetpack) to use `navGraphViewModels` with Hilt is:
```kotlin
val viewModel: ExampleViewModel by navGraphViewModels(R.id.my_graph) {
  defaultViewModelProviderFactory
}
```

## Issue

The issue has something to with restoring ViewModel state.  
In this project we have a nested navigation graph `R.id.nested` which consists of 2 fragments. Those 2 fragments shares `MainViewModel` and `MainViewModel2` which are completely similar and contains `LiveData<String>`.
The only difference is that first ViewModel initialized with fragment's `defaultViewModelProviderFactory` as recommended with Hilt, while second is initialized without passing custom factory, so uses backStackEntry's `defaultViewModelProviderFactory`.
```kotlin
    private val viewModel by navGraphViewModels<MainViewModel>(R.id.nested) {
        defaultViewModelProviderFactory
    }
    
    private val viewModel2 by navGraphViewModels<MainViewModel2>(R.id.nested)
 ```

First fragment in nested graph sets message to both viewmodels and then opens Second fragment.  
With `Don't keep activities` enabled, putting app to background and back, triggers app process death and restore.  
After process restore `MainViewModel` doesn't have value from First fragment and shows the default one, while `MainViewModel2` is restored correctly and displays proper message.

1. Enter message on FirstFragment and open SecondFragment.  
*On SecondFragment both viewmodels have correct values.*
2. Background app with `Don't keep activities` to trigger process death and come back.  
*On SecondFragment first viewmodel doesn't have restored value, second viewmodel has as expected.*
3. Press back to return on FirstFragment and open SecondFragment again  
*On SecondFragment both viewmodels have correct values.*
4. Background app with `Don't keep activities` to trigger process death and come back.  
*On SecondFragment both viewmodels contains correct value, even if you modify value on FirstFragment.*
5. Press back to return on FirstFragment, Background app with `Don't keep activities` and open SecondFragment.
*On SecondFragment both viewmodels have correct values.*
6. Background app with `Don't keep activities` to trigger process death and come back.  
*On SecondFragment first viewmodel doesn't have restored value, second viewmodel has as expected.*

<img src="https://github.com/silentnuke/HiltNavGraphViewModelsIssue/blob/master/demo/demo.gif" />
