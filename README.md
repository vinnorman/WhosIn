# WhosIn

This is a personal project I made to demonstrate an app in full Jetpack Compose, using Material 3, as well as applying strong architectural practices. 


## Overview

The aim of this project is as a showcase of Jetpack Compose, a clear and modern looking UI, and aiming for a clean architecture. 

First a short disclaimer: this is not a perfect production-grade project - note the total absence of tests. This was not the goal. The goal is to demonstrate what can be done in Material 3 and Jetpack Compose, and to aim for an architecture which has a sensible separation of concerns. For example
 * **Minimal use of modules**: Rather than a total module explosion as is often the case, this is a simple separation of the data layer, and the auth management, into self-contained modules. The rationale here is the app should not be concerned with the inner workings of getting the data or authentication, and so by separating these into modules we force the abstraction for the app module. For auth, the only public class is the `AuthManager`, and everything else is internal. In the data module, the Repositories are public, but everything else is internal.
 * **Thinking in screens**: Having attended droidCon London for the past 2 years, and seeing top speakers discuss state, and where to put logic (business logic, UI logic), I have been thinking very much in terms of "Screens", with a single ViewModel as the business logic holder for the screen, but allowing UI logic to exist elsewhere, either within composables or small UIState Holders.
 * **Working CI/CD**: This is a public repo on GitHub, and I am using GitHub actions, and fastlane, to deploy it to the Play Store. All sensitive values like json keys, passwords, keystores, have been removed from the repo and are held as repository secrets. The result is a very clean and simple production pipeline, to both publish Work In Progress features (PRs) to Firebase App Distribution, as well as publish versions to the play store, live for users, upon tag.

This project was rattled together very quickly, in a matter of weeks, however the result is a fully functional app, published to the play store.

## Screenshots

<img src="screenshot%201.webp" alt="drawing" width="400"/>

<img src="screenshot%202.webp" alt="drawing" width="400"/>

<img src="screenshot%203.webp" alt="drawing" width="400"/>

<img src="screenshot%204.webp" alt="drawing" width="400"/>



