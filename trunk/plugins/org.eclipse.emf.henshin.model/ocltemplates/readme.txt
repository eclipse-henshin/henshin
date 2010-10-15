Files in the "ocltemplates" folder are dedicated to generate code for checking/querying 
OCL constraints defined in the model. For examples please visit the article "Implementing 
Model Integrity in EMF with MDT OCL" to be found at  
http://www.eclipse.org/articles/article.php?file=Article-EMF-Codegen-with-OCL/index.html. 
Each template file has be extracted from the example project found at that website. 
However, "ValidatorClass.javajet" and "Class.javajet" were modified, on the one hand 
to modify the OCL namespace to be more Henshin related  and on the other hand to lift 
the template version from EMF2.3 to EMF2.5.

It seems to work :-)

Make sure that after reloading the GenModel, the following properties are still set:
(Hint: In the tree-based editor, click on the topmost element above the package and
open the properties view!)
* Model Plugin Variables : OCL_ECORE=org.eclipse.ocl.ecore
* Dynamic Templates : true
* Template Directory : org.eclipse.emf.henshin.model/ocltemplates

