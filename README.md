# Ti.FloatingWidget

This Axway Tiranium module realize the Android version of [Floating Widget](https://www.simplifiedcoding.net/android-floating-widget-tutorial/).

## Permission

If you want to draw a Floating Widget in your application then your application mus have the SYSTEM\_ALERT\_WINDOW permission. Now you already know it that after Android Marshmallow we need to ask permissions at run time.

## Usage

```javascript
const FW = require('ti.floatingwidget');
var fwview;
const content = Ti.UI.createView({
		width : 100,
		height : 100,
		backgroundColor : 'orange'
});
FW.requestPermission();
FW.onResult = function(e) {
	if (e.result == FW.RESULT_OK) {
		fwView = FW.createView(content);
	}	
}


// ...
fwView.destroy();

```
