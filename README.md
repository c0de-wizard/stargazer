<H2 align="center">
Github Stargazer.
</H2>

<div align="center">
    <a href = "https://github.com/c0de-wizard/stargazer/actions/workflows/android-release-build.yml">
          <img src = "https://github.com/c0de-wizard/stargazer/actions/workflows/android-release-build.yml/badge.svg" />
    </a>
    <a href = "https://codecov.io/gh/c0de-wizard/stargazer">
          <img src = "https://codecov.io/gh/c0de-wizard/stargazer/branch/main/graph/badge.svg" />
    </a>
    <a href = "https://developer.android.com/jetpack/androidx/versions/all-channel?authuser=1#march_24_2021">
      <img src = "https://img.shields.io/badge/Jetpack%20Compose-1.0.0%20beta03-brightgreen" />
    </a>
</div>

## Description

Stargazer is a simple android project that consumes Github's Api & loads a list of repositories from Square's
organization. Ui is completely written in Jetpack Compose.

### Screenshots

<table>
  <td>
    <p align="center">
      <img src="https://github.com/c0de-wizard/github-stargazer/blob/main/art/RepoList.png?raw=true" alt="Trenidng Repos Screen" width="250"/>
    </p>
  </td>
  <td>
    <p align="center">
      <img src="https://github.com/c0de-wizard/github-stargazer/blob/main/art/SearchScreen.png?raw=true" alt="Search Screen" width="250"/>
    </p>
  </td>
  <td>
    <p align="center">
      <img src="https://github.com/c0de-wizard/github-stargazer/blob/main/art/SettingsScreen.png?raw=true" alt="Settings Screen" width="250"/>
    </p>
  </td>
  <td>
    <p align="center">
      <img src="https://github.com/c0de-wizard/github-stargazer/blob/main/art/DarkTheme.png?raw=true" alt="Dark Theme" width="250"/>
    </p>
  </td>
</tr>
</table>

## Requirements

* [Android Studio Arctic Fox](https://developer.android.com/studio/preview?authuser=1)
* JDK 11
* [Android SDK](https://developer.android.com/studio/index.html)
* Latest Android SDK Tools and build tools.

## Architecture

The architecture of the project follows the principles of Clean Architecture. This project demonstrates two Architecture
implementations: **MVI** pattern.

<img src="https://github.com/c0de-wizard/github-stargazer/blob/main/art/ArchitectureDiagram.png?raw=true" width="250" />

Here's how the sample project implements it:

### Core

This layer contains core classes shared between modules. It includes:

- Application dagger interfaces, Scope annotations
- Generic classes eg Interactor, which enforces domain interactor implementation when extended.
- Common extension classes

### Repository (Data Layer)

This layer is responsible for fetching data. It contains cache and remote implementation.

For this case, I choose to have it as a separate module since the api is not changing that much but for a bigger
application, I would ideally move this to the respective module. It's easier to manage changes that way.

### Features

This layer is where we create different feature modules. Browse Repository & Bookmark Repository. Structure breakdown:

- **Injection**: Each feature has it's own component with that injects the dependencies it needs. This allows each
  feature to have lean subcomponents
- **Domain**: This layer orchestrates the flow of data from Data Layer to the ui layer. It also has mappers that
  transform objects to the required type. eg Entity -> ViewModelData
- **UI**: This is responsible displaying data.
- **Navigation**: Each feature is responsible for defining it's own route on the NavHost. This prevents us
  from having a god class with all the routes.

### Navigation

This module is responsible for creating routes withing NavHost using factories. Each feature module has it's own navigation factory
and is responsible for defining the route.

## Code style

To maintain the style and quality of the code, are used the bellow static analysis tools. All of them use properly
configuration and you find them in the project root directory `.{toolName}`.

| Tools | Check command | Fix command |
|-------|---------------|-------------|
| [detekt](https://github.com/arturbosch/detekt) | `./gradlew detekt` | - |
| [ktlint](https://github.com/pinterest/ktlint) | `./gradlew ktlint` | `./gradlew ktlintFormat` |
| [spotless](https://github.com/diffplug/spotless) | `./gradlew spotlessCheck` | `./gradlew spotlessApply`
| [lint](https://developer.android.com/studio/write/lint) | `./gradlew lint` | - |

## Libraries Used

* [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started)
* [JetPack](https://developer.android.com/jetpack)

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) UI related data holder, lifecycle
  aware.
- [Room Persistence]() - construct a database using the abstract layer.
- LiveData - notify domain layer data to views.
- Lifecycle - dispose of observing data when lifecycle state changes.
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Replacement for shared preference.
- [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - For loading & displaying data from local storage and network.

* [Material-Components](https://github.com/material-components/material-components-android) - Material design
  components.
* [Dark/Light Theme](https://developer.android.com/guide/topics/ui/look-and-feel/darktheme) - Support dark/light themes
* [Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
  + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for
    asynchronous.
* [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
* [Moshi](https://github.com/square/moshi) - Moshi is a modern JSON library for Android and Java
* [Timber](https://github.com/JakeWharton/timber) - logging.
* [Truth](https://github.com/google/truth) - Unit Testing
* [Turbine](https://github.com/cashapp/turbine) - Testing coroutines
* [Dagger Hilt](https://dagger.dev/hilt) - dependency injection.
* [Kotlin Gradle DSL](https://guides.gradle.org/migrating-build-logic-from-groovy-to-kotlin)
* [Jacoco](https://github.com/vanniktech/gradle-android-junit-jacoco-plugin) Test coverage

### TODO

- [ ] Cleaup navigation actions.

### License
```
Copyright 2021 Thomas Kioko.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
