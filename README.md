# mpdx-android
MPDx Android app - POSSIBLE Mobile version

## Running Checks

For running the quick checks, PMD and Checkstyle, execute the following from the root directory of the repo:
```
./gradlew app:pmd app:checkstyle
```
To run the full gamut for checks, including lint, execute the following from the root directory of the repo:
```
./gradlew app:check
```
Note: Running all of the checks with app:check will take awhile to complete. This command will build all variants of the project, checking each one. It is recommended to let the build server run this task after opening a PR.

## Architecture tenants
### Data Layer
* TODO

### Presentation layer
* ViewModels
  * Name: CurrentTaskViewModel, ContactViewModel, etc.
  * Exposes view logic from domain models that are passed in from Presenter
  * Data Binding is used to bind a ViewModel to XML layout
  * Any user interactions should be routed to the appropriate Presenter
* Presenters
  * Name: CurrentTaskPresenter, ContactPresenter, etc.
  * Request and retreive data from Repository to pass onto ViewModel
  * Handle any user interactions for navigation or state change
* Note: We should never have any view logic in XML layouts in the form of DataBinding operators. Consider exposing a ViewModel method for this logic instead.
  
## Multidex
This project utilizes multidexing that could slow down build times if your minSdk < 21. To speed things up you can put `customMinSdk=21` in `local.properties`. This file is not checked into VCS and won't be seen by Jenkins. If you use an API not supported before API 21 then your local build will be fine but Jenkins will fail.
  
