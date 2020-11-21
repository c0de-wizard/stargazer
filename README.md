Github Stargazer
--------------------
Stargazer is a simple android project that consumes Github's Api & loads a list of repositories from Square's organization.

## Requirements

* JDK 1.8
* [Android SDK](https://developer.android.com/studio/index.html)
* Latest Android SDK Tools and build tools.

## Environment setup

First off, you require the latest Android Studio 4.1 (or newer) to be able to build the app.


* Start Android Studio
* Select "Open Project" and select the generated root Project folder
* You may be prompted with "Unlinked gradle project" -> Select "Import gradle project" and select
the option to use the gradle wrapper
* You may also be prompted to change to the appropriate SDK folder for your local machine
* Once the project has compiled -> run the project!
``

## Architecture

The architecture of the project follows the principles of Clean Architecture. Here's how the sample project implements it:

### Core

This layer contains core classes shared between modules. It includes:
- Application dagger interfaces, Scope annotations
- Generic classes eg Interactor, which enforces domain interactor implementation when extended.
- Common extension classes

### Repository (Data Layer)
This layer is responsible for fetching data. It contains cache and remote implementation.

### Features
This layer is where we create different feature modules. Browse Repository & Bookmark Repository. Structure breakdown:
- Injection: Each feature has it's own component with that injects the dependencies it needs. This allows each feature to have lean subcomponents
- Domain: This layer orchestrates the flow of data from Data Layer to the ui layer. It also has mappers that transform objects to the required type. eg Entity -> ViewModelData
- UI: This is responsible displaying data.
 
### Navigator
This is a dumb layer that simply acts as a route between modules using intent actions.


### TODO

- [x] Create base architecture.
- [x] Create base architecture.
- [x] Implement repository logic: 
	- [x] Retrofit.
	- [x] Room.
- [x] Load and display repositories.
- [x] Bookmark repo.
- [x] Cleanup: Create common module: Contains shared UI classes.
