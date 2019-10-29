# react-native-espider-engine

## Getting started

`$ npm install react-native-espider-engine --save`

### Mostly automatic installation

`$ react-native link react-native-espider-engine`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-espider-engine` and add `EspiderEngine.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libEspiderEngine.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.reactlibrary.EspiderEnginePackage;` to the imports at the top of the file
  - Add `new EspiderEnginePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-espider-engine'
  	project(':react-native-espider-engine').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-espider-engine/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-espider-engine')
  	```


## Usage
```javascript
import EspiderEngine from 'react-native-espider-engine';

// TODO: What to do with the module?
EspiderEngine;
```
