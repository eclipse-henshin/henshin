**We are currently migrating Henshin's documentation from [Eclipse Wiki](https://wiki.eclipse.org/Henshin) to this place. Links on this page will still lead to Eclipse Wiki - please ignore the deprecation warnings there.**

**Henshin** is an in-place model transformation language for the [Eclipse Modeling Framework](https://wiki.eclipse.org/Eclipse_Modeling_Framework) (EMF). It supports direct transformations of EMF model instances (endogenous transformations), as well as generating instances of a target language from given instances of a source language (exogenous transformations). Its main features are:

**Basic transformation definition and execution**

* [Rule](https://wiki.eclipse.org/Henshin/Graphical_Editor#Editing_Transformation_Rules)-based transformation paradigm with [units](https://wiki.eclipse.org/Henshin/Units) for managing control flow of rules
* [Graphical](https://wiki.eclipse.org/Henshin/Graphical_Editor) and [textual syntax](https://wiki.eclipse.org/Henshin/Textual_Editor), based on a transformation [meta-model](https://wiki.eclipse.org/Henshin/Transformation_Meta-Model)
* Native support for endogenous transformations; support of exogenous transformations via [traces](https://wiki.eclipse.org/Henshin/Trace_Model)
* Efficient in-place execution of transformations using a dedicated [interpreter](https://wiki.eclipse.org/Henshin/Interpreter) with debugging support

Analysis

* A [performance profiler](https://wiki.eclipse.org/Henshin/Performance_Profiler) to identify slow spots
* Support for [conflict and dependency analysis](https://wiki.eclipse.org/Henshin/Conflict_and_Dependency_Analysis)
* [State space analysis](https://wiki.eclipse.org/Henshin/State_Space_Tools) for verification


Advanced rule definition

* Support for [rule variants](https://wiki.eclipse.org/Henshin/Variant_Management)
* Support for [automated rule generation](https://wiki.eclipse.org/Henshin/Rule_Generation)
* Support for [generating application conditions from OCL constraints](https://wiki.eclipse.org/Henshin/OCL2AC)

Integration with other tools

* Integration with [Xtext](https://wiki.eclipse.org/Henshin/Xtext_Adapter)
* Support for [massive parallel rule execution](https://wiki.eclipse.org/Henshin/Code_Generator_for_Giraph) using Apache Giraph

Resources

* [Official website](http://www.eclipse.org/modeling/emft/henshin)
* [Installation instructions](https://wiki.eclipse.org/Henshin/Installation_instructions)
* [Getting started](https://wiki.eclipse.org/Henshin/Getting_started)
* [Examples](https://wiki.eclipse.org/Henshin/Examples)
* [FAQ](https://wiki.eclipse.org/Henshin/FAQ)
* [Release Notes](https://wiki.eclipse.org/Henshin/Release_Notes)
* [Projects that use Henshin](https://wiki.eclipse.org/Henshin/Projects)
