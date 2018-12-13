fastlane documentation
================
# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```
xcode-select --install
```

Install _fastlane_ using
```
[sudo] gem install fastlane -NV
```
or alternatively using `brew cask install fastlane`

# Available Actions
## Android
### android info
```
fastlane android info
```
Print current versioning information
### android test
```
fastlane android test
```
Submit a new Debug Build to Crashlytics Beta
### android beta
```
fastlane android beta
```
Submit a new Beta Build to Crashlytics Beta
### android release
```
fastlane android release
```
Generates the production bundle and compresses it along with other usefull resources.

----

This README.md is auto-generated and will be re-generated every time [fastlane](https://fastlane.tools) is run.
More information about fastlane can be found on [fastlane.tools](https://fastlane.tools).
The documentation of fastlane can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
