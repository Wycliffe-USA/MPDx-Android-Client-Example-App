# Client Example fo MPDx Implementation

This Repository is to give an example on how to implement the MPDx Android Libraries for your application.  The best way to use this Repository is to create a Fork of it and adjust what is needed for your implementation.

## Gradle Implementation
### Project Gradle
Add our library from maven to project Gradle
```groovy
repositories {
      maven {
          url 'https://cruglobal.jfrog.io/cruglobal/list/maven-locals/'
          content {
              includeGroup 'org.ccci.gto.android'
              includeGroup 'org.ccci.gto.android.testing'
              includeGroup 'org.cru.mpdx.android'
          }
      }
  }
```
Add Dagger and Realm Dependencies
```groovy
dependencies {
      classpath "io.realm:realm-gradle-plugin:10.9.0"
      classpath "com.google.dagger:hilt-android-gradle-plugin:$dagger"
}
```

### Application Gradle
Add core library to application gradle
```groovy
implementation "org.cru.mpdx.android:library:${deps.mpdxLib}"
    implementation "org.cru.mpdx.android:core:${deps.mpdxLib}"
```
Add appropriate Authentication library
```groovy
implementation "org.cru.mpdx.android:okta:${deps.mpdxLib}"
```
Your application will need to implement Dagger Hilt
```groovy
implementation "com.google.dagger:hilt-android:$dagger"

kapt "com.google.dagger:dagger-compiler:$dagger"
kapt "com.google.dagger:hilt-compiler:$dagger"
```
## Code Implementation
### MPDXApp
When implementing code you will need to create a Application Class. This is required initialize realm and Dagger Hilt.
```kotlin
@HiltAndroidApp
public class MpdxApp extends Application 
```

### SplashActivity
You will need to create an Activity that will be an entry point into the MPDXApp.  Once you have verified you are logged in use the code below to start the application.
```kotlin
ModalActivity.launchActivity(this, UnlockFragment.create(deepLinkType, deepLinkId, deepLinkTime), true)
finish()
```

### Module
In the example you will see a lot Modules that are used to create providers for dagger which will be used in both your app and the libraries.  This Module have comments that will explain their purpose.
