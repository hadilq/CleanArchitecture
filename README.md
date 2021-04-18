# Guidomia

This is a sample app to show best practices up to authors's knowledge by trying to
stay close to standard technologies for a highly scalable, maintainable, and testable
Android app.

## Technologies

 - Clean Architecture
 - Public/Impl modules to handle Dependency Inversion Principle on module level
 - Anvil to handle Inversion of Control
 - Dagger to handle Dependency Injection
 - Single Activity for faster initialization of pages
 - AndroidX
 - MVVM
 - Kotlin Coroutines
 - KotlinX Serialization
 - Room database
 - JUnit 5
 - Mockk

### Dagger

Defined scopes in Dagger
 - AppScope
 - SingleActivityScope
 - FragmentScope
 - RetainScope

