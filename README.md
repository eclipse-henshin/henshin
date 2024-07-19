**Henshin** is an in-place model transformation language for the [Eclipse Modeling Framework](https://eclipse.dev/modeling/emf/) (EMF). It supports direct transformations of EMF model instances (endogenous transformations), as well as generating instances of a target language from given instances of a source language (exogenous transformations). Its main features are:

**Basic transformation definition and execution**

* [Rule](https://github.com/eclipse-henshin/henshin/wiki/GraphicalEditor#editing-transformation-rules)-based transformation paradigm with [units](https://github.com/eclipse-henshin/henshin/wiki/Units) for managing control flow of rules
* [Graphical](https://github.com/eclipse-henshin/henshin/wiki/GraphicalEditor) and [textual syntax](https://github.com/eclipse-henshin/henshin/wiki/Textual-Editor), based on a transformation [meta-model](https://github.com/eclipse-henshin/henshin/wiki/Transformation-Meta-Model)
* Native support for endogenous transformations; support of exogenous transformations via [traces](https://github.com/eclipse-henshin/henshin/wiki/Trace-Model)
* Efficient in-place execution of transformations using a dedicated [interpreter](https://github.com/eclipse-henshin/henshin/wiki/Interpreter) with debugging support

**Analysis**

* A [performance profiler](https://github.com/eclipse-henshin/henshin/wiki/Performance_Profiler) to identify slow spots
* Support for [conflict and dependency analysis](https://github.com/eclipse-henshin/henshin/wiki/Conflict-and-Dependency-Analysis)
* [State space analysis](https://github.com/eclipse-henshin/henshin/wiki/State-Space-Tools) for verification


**Advanced rule definition**

* Support for [rule variants](https://github.com/eclipse-henshin/henshin/wiki/Variant-Management)
* Support for [automated rule generation](https://github.com/eclipse-henshin/henshin/wiki/Rule-Generation)
* Support for [generating application conditions from OCL constraints](https://github.com/eclipse-henshin/henshin/wiki/OCL2AC)

**Integration with other tools**

* Integration with [Xtext](https://github.com/eclipse-henshin/henshin/wiki/Xtext-Adapter)
* Support for [massive parallel rule execution](https://github.com/eclipse-henshin/henshin/wiki/Code-Generator-for-Giraph) using Apache Giraph


**Resources**

* [Official website](http://www.eclipse.org/modeling/emft/henshin)
* [Installation instructions](https://github.com/eclipse-henshin/henshin/wiki/Installation-instructions)
* [Getting started](https://github.com/eclipse-henshin/henshin/wiki/Getting-started)
* [Wiki](https://github.com/eclipse-henshin/henshin/wiki) with comprehensive documentation
* [Examples](https://github.com/eclipse-henshin/henshin/wiki/Examples)
* [FAQ](https://github.com/eclipse-henshin/henshin/wiki/FAQ)
* [Release Notes](https://github.com/eclipse-henshin/henshin/wiki/Release-Notes)
* [Projects that use Henshin](https://github.com/eclipse-henshin/henshin/wiki/Projects)
