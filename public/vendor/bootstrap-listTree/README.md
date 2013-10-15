# jQuery / Twitter Bootstrap List Tree Plugin

Demo: http://jsfiddle.net/clayzermk1/QD8Hs/

## Overview
I needed a simple plugin to build a two-tier collapsible list with checkboxes.
I wanted it to fit well with Twitter's Bootstrap.
I couldn't find one that was simple enough.
I hope you enjoy =)
Feel free to send feedback.

Thanks to everyone at jQuery, bootstrap, underscore, and d3!

## Features
* Namespaced for jQuery.
* Twitter Bootstrap compatible (again namespacing).
* Expandable/collapsible parent nodes (click to expand/collapse).
* (Un)Checking a parent node will (un)check all child nodes.
* If at least one child is checked, the parent is checked (useful for identifying collapsed parents with active child nodes).
* JSON representation of selected nodes (preserves the order of the original data context).
* Update with new data on the fly.

## Requirements
Tested with:
* jQuery 1.7.2
* bootstrap 2.0.4
* underscore 1.3.3

The data model uses the d3.v2.js nest() ordering and naming conventions.

## Installation
You only _need_ the JS file to take advantage of the plugin.
The CSS file provides a default bootstrap look with rounded corners.

## Use
Simply create an empty containing element of your choice, give it the class "listTree" and invoke the plugin.
```javascript
$('.listTree').listTree(data, [options]);​
```

(Un)checking a header checkbox will (un)check all items below that header.

Clicking a header will collapse or expand the child items below that header.

## Options
Currently there are two options available to the user.

### startCollapsed (boolean)
Dictates whether the entire list should initialize collapsed.

Possible Values:
* ```true```
* ```false``` (Default)

### selected (Array)
Dictates which nodes should be selected by default.

Possible values:
* Not specified or data context - Selects every node (Default)
* A subset of the data context - Selects only the nodes matching the subset
* ```[]``` - Selects nothing

## Plugin Methods
All functions are memoized, to call the function invoke the plugin with the first argument as the method name. If you are calling a function that requires some data, pass that as the second argument.
```javascript
$('.listTree').listTree('update', someOtherDataObj, someOtherDataOptions);​
```

### init(context)
Initializes the plugin with a certain data object, "context". Called implicitly by the plugin.
```javascript
$('.listTree').listTree(someDataObj, someDataOptions);​
```

### destroy()
Destroys the plugin object.
```javascript
$('.listTree').listTree('destroy');​
```

### selectAll()
Selects (check) all checkboxes in the list.
```javascript
$('.listTree').listTree('selectAll');​
```

### deselectAll()
Deselects (uncheck) all checkboxes in the list.
```javascript
$('.listTree').listTree('deselectAll');​
```

### expandAll()
Expands all list headers.
```javascript
$('.listTree').listTree('expandAll');​
```

### collapseAll()
Collapses all list headers.
```javascript
$('.listTree').listTree('collapseAll');​
```

### update()
Updates the list with a new data object.
```javascript
$('.listTree').listTree('update', someOtherDataObj, someOtherDataOptions);​
```

## Copyright & License
Copyright 2012 Clay Walker
Licensed under GPLv2 ONLY