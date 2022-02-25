# Client Example fo MPDx Implementation

This Repository is to give an example on how to implement the MPDx Android Libraries for your application

## Gradle Implementation
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

Add core library to application gradle
```groovy
implementation "org.cru.mpdx.android:library:${deps.mpdxLib}"
    implementation "org.cru.mpdx.android:core:${deps.mpdxLib}"
```
Add appropriate Authentication library
```groovy
implementation "org.cru.mpdx.android:okta:${deps.mpdxLib}"
```
