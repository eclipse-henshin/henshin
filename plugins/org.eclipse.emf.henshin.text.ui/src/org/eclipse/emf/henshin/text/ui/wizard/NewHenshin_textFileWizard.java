package org.eclipse.emf.henshin.text.ui.wizard;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.henshin.presentation.ImportPackagesWizardPage;
import org.eclipse.emf.henshin.text.henshin_text.EPackageImport;
import org.eclipse.emf.henshin.text.henshin_text.Henshin_textFactory;
import org.eclipse.emf.henshin.text.henshin_text.Model;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class NewHenshin_textFileWizard extends Wizard implements INewWizard{
	
	private ISelection selection;
	private IWorkbench workbench;
	private ProjectSelectionPage projectSelectionPage;
	private ImportPackagesWizardPage importEPackagesPage;

	
	public void init(IWorkbench workbench, IStructuredSelection selection){
		this.selection=selection;
		this.workbench=workbench;
		setWindowTitle("New Henshin Text file");
		setNeedsProgressMonitor(true);
		
	}
	
	public void addPages(){
		projectSelectionPage=new ProjectSelectionPage(selection);
		addPage(projectSelectionPage);
		importEPackagesPage=new ImportPackagesWizardPage("importPackages");
		addPage(importEPackagesPage);
	}
	

	public boolean canFinish(){
		if(importEPackagesPage.getPackageURIs().size()>0){
			return true;
		}
		return false;
	}

	
	public boolean performFinish() {
		final String containerName = projectSelectionPage.getContainerName();
		final String fileName = projectSelectionPage.getFileName();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource iresource = root.findMember(new Path(containerName));
		IContainer container = (IContainer) iresource;
		final IFile file = container.getFile(new Path(fileName));
		//add XTextNature to Project
		   try {
			if (!container.getProject().hasNature("org.eclipse.xtext.ui.shared.xtextNature")) {
			      IProjectDescription description = container.getProject().getDescription();
			      String natureList[]=new String[description.getNatureIds().length+1];
			      System.arraycopy(description.getNatureIds(), 0, natureList, 0, description.getNatureIds().length);
			      natureList[natureList.length-1]="org.eclipse.xtext.ui.shared.xtextNature";
			      description.setNatureIds(natureList);
			      container.getProject().setDescription(description, null);
				   }
		} catch (CoreException e) {
			MessageDialog.openError(getShell(), "Error", "Error adding Xtext nature to Project.");
			return false;
			
		}
		
			IRunnableWithProgress operation = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException {
					ResourceSet resourceSet = new ResourceSetImpl();
					URI fileURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
					Resource resource = resourceSet.createResource(fileURI);
					Model model=Henshin_textFactory.eINSTANCE.createModel();
					for(URI ePackageURI:importEPackagesPage.getPackageURIs()){
						EObject object = resourceSet.getEObject(ePackageURI, true);
						if(object instanceof EPackage) {
							
						if(!projectContainsEcore(((EPackage)object).eResource().getURI(),container.getProject())){
							ResourceSet metaResourceSet = new ResourceSetImpl();
							metaResourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new  XMLResourceFactoryImpl());
							String ecorePath=container.getFullPath()+"/"+((EPackage)object).getName()+".ecore";
							Resource metaResource = metaResourceSet.createResource(URI.createPlatformResourceURI(ecorePath,true));
							if(EPackage.Registry.INSTANCE.getEPackage(((EPackage)object).getNsURI())!=null){
								metaResource.getContents().add((EPackage)object);
							}else{
								EPackage newEPackage = EcoreFactory.eINSTANCE.createEPackage();
								newEPackage=EcoreUtil.copy(((EPackage)object));
								metaResource.getContents().add(newEPackage);
								object=newEPackage;
							}
							try {
							     metaResource.save(null);
							  } catch (IOException e) {
								  MessageDialog.openError(getShell(), "Error", "Error saving "+((EPackage)object).getName()+".ecore.");
								  e.printStackTrace();
							   }
							resource.getResourceSet().getResources().add(metaResource);
					//		resource.getResourceSet().getPackageRegistry().put(((EPackage)object).getNsURI(),((EPackage)object));
						}
						EPackageImport eimport=Henshin_textFactory.eINSTANCE.createEPackageImport();
						eimport.setRef((EPackage)object);
						model.getEPackageimports().add(eimport);
						}
					}
					
					resource.getContents().add(model);
					Map<Object, Object> options = new HashMap<Object, Object>();
					options.put(XMLResource.OPTION_ENCODING, "UTF-8");
					try {
						resource.save(options);
						
					} catch (IOException e) {
						MessageDialog.openError(getShell(), "Error", "Error saving "+file.getName()+".");
						e.printStackTrace();
					}
			
				}
				};
				try {
					getContainer().run(true, false, operation);
					IWorkbenchPage page = this.workbench.getActiveWorkbenchWindow().getActivePage();
					IEditorDescriptor descriptor = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
					try {
						
						page.openEditor(new FileEditorInput(file), descriptor.getId());
					} catch (PartInitException e) {
						MessageDialog.openError(getShell(), "Error", "Cannot open "+file.getName()+"!");
						e.printStackTrace();
					}
					
				} catch (InterruptedException e) {
					return false;
				} catch (InvocationTargetException e) {
					Throwable realException = e.getTargetException();
					MessageDialog.openError(getShell(), "Error",realException.getMessage());
					return false;
				}
		   
		
		return true;
	}
	

	
	
	/**
	 * Prüft ob eine Resource bereits in einem Projekt existiert
	 * @param ecoreUri URI der zu überprüfenden Resource
	 * @param project Das zu durchsuchende Projekt
	 * @return true wenn Resource im Projekt existiert
	 */
	private boolean projectContainsEcore(URI ecoreUri,IProject project ){
		if(ecoreUri.segmentsList().size()>1){
			if(!project.getName().equals(ecoreUri.segmentsList().get(1))){
				return false;
			}
			String path="";
			for(int i=2;i<ecoreUri.segmentsList().size();i++){
				path=path+"/"+ecoreUri.segmentsList().get(i);
			}
			if(project.findMember(path)!=null){
				return true;
			}

		}
		return false;
	}

}

