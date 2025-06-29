/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.part;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.henshin.diagram.providers.HenshinMarkerNavigationProvider;
import org.eclipse.emf.henshin.diagram.providers.HenshinValidationProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.diagram.ui.OffscreenEditPartFactory;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation;

/**
 * @generated
 */
public class ValidateAction extends Action {

	/**
	 * @generated
	 */
	private IWorkbenchPage page;

	/**
	 * @generated
	 */
	public ValidateAction(IWorkbenchPage page) {
		setText(Messages.ValidateActionMessage);
		this.page = page;
	}

	/**
	 * @generated
	 */
	public void run() {
		IWorkbenchPart workbenchPart = page.getActivePart();
		if (workbenchPart instanceof IDiagramWorkbenchPart) {
			final IDiagramWorkbenchPart part = (IDiagramWorkbenchPart) workbenchPart;
			try {
				new WorkspaceModifyDelegatingOperation(new IRunnableWithProgress() {

					public void run(IProgressMonitor monitor) throws InterruptedException, InvocationTargetException {
						runValidation(part.getDiagramEditPart(), part.getDiagram());
					}
				}).run(new NullProgressMonitor());
			} catch (Exception e) {
				HenshinDiagramEditorPlugin.getInstance().logError("Validation action failed", e); //$NON-NLS-1$
			}
		}
	}

	/**
	 * @generated
	 */
	public static void runValidation(View view) {
		try {
			if (HenshinDiagramEditorUtil.openDiagram(view.eResource())) {
				IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.getActiveEditor();
				if (editorPart instanceof IDiagramWorkbenchPart) {
					runValidation(((IDiagramWorkbenchPart) editorPart).getDiagramEditPart(), view);
				} else {
					runNonUIValidation(view);
				}
			}
		} catch (Exception e) {
			HenshinDiagramEditorPlugin.getInstance().logError("Validation action failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	public static void runNonUIValidation(View view) {
		Display display = PlatformUI.getWorkbench().getDisplay();
		Shell shell = Optional.ofNullable(display.getActiveShell())
				.or(() -> Arrays.stream(display.getShells()).findFirst()).orElseThrow();
		DiagramEditPart diagramEditPart = OffscreenEditPartFactory.getInstance()
				.createDiagramEditPart(view.getDiagram(), shell);
		runValidation(diagramEditPart, view);
	}

	/**
	 * @generated
	 */
	public static void runValidation(DiagramEditPart diagramEditPart, View view) {
		final DiagramEditPart fpart = diagramEditPart;
		final View fview = view;
		TransactionalEditingDomain txDomain = TransactionUtil.getEditingDomain(view);
		HenshinValidationProvider.runWithConstraints(txDomain, new Runnable() {

			public void run() {
				validate(fpart, fview);
			}
		});
	}

	/**
	 * @generated
	 */
	private static Diagnostic runEMFValidator(View target) {
		if (target.isSetElement() && target.getElement() != null) {
			return new Diagnostician() {

				public String getObjectLabel(EObject eObject) {
					return EMFCoreUtil.getQualifiedName(eObject, true);
				}
			}.validate(target.getElement());
		}
		return Diagnostic.OK_INSTANCE;
	}

	/**
	 * @generated
	 */
	private static void validate(DiagramEditPart diagramEditPart, View view) {
		IFile target = view.eResource() != null ? WorkspaceSynchronizer.getFile(view.eResource()) : null;
		if (target != null) {
			HenshinMarkerNavigationProvider.deleteMarkers(target);
		}
		Diagnostic diagnostic = runEMFValidator(view);
		createMarkers(target, diagnostic, diagramEditPart);
		IBatchValidator validator = (IBatchValidator) ModelValidationService.getInstance()
				.newValidator(EvaluationMode.BATCH);
		validator.setIncludeLiveConstraints(true);
		if (view.isSetElement() && view.getElement() != null) {
			IStatus status = validator.validate(view.getElement());
			createMarkers(target, status, diagramEditPart);
		}
	}

	/**
	 * Previously generated code, was fixed to avoid NPE
	 * 
	 * @param target
	 * @param validationStatus
	 * @param diagramEditPart
	 */
	private static void createMarkers(IFile target, IStatus validationStatus, DiagramEditPart diagramEditPart) {
		if (validationStatus.isOK()) {
			return;
		}
		final IStatus rootStatus = validationStatus;
		List<IConstraintStatus> allStatuses = new ArrayList<>();
		HenshinDiagramEditorUtil.LazyElement2ViewMap element2ViewMap = new HenshinDiagramEditorUtil.LazyElement2ViewMap(
				diagramEditPart.getDiagramView(),
				collectTargetElements(rootStatus, new HashSet<>(), allStatuses));
		for (IConstraintStatus status : allStatuses) {
			View view = HenshinDiagramEditorUtil.findView(diagramEditPart, status.getTarget(), element2ViewMap);
			if (view != null) {
				Resource eResource = view.eResource();
				if (eResource != null) {
					addMarker(diagramEditPart.getViewer(), target, eResource.getURIFragment(view),
							EMFCoreUtil.getQualifiedName(status.getTarget(), true), status.getMessage(),
							status.getSeverity());
				}
			}
		}
	}

	/**
	 * @generated
	 */
	private static void createMarkers(IFile target, Diagnostic emfValidationStatus, DiagramEditPart diagramEditPart) {
		if (emfValidationStatus.getSeverity() == Diagnostic.OK) {
			return;
		}
		final Diagnostic rootStatus = emfValidationStatus;
		List<Diagnostic> allDiagnostics = new ArrayList<>();
		HenshinDiagramEditorUtil.LazyElement2ViewMap element2ViewMap = new HenshinDiagramEditorUtil.LazyElement2ViewMap(
				diagramEditPart.getDiagramView(),
				collectTargetElements(rootStatus, new HashSet<>(), allDiagnostics));
		for (Diagnostic diagnostic : emfValidationStatus.getChildren()) {
			List<?> data = diagnostic.getData();
			if (data != null && !data.isEmpty() && data.get(0) instanceof EObject) {
				EObject element = (EObject) data.get(0);
				View view = HenshinDiagramEditorUtil.findView(diagramEditPart, element, element2ViewMap);
				addMarker(diagramEditPart.getViewer(), target, view.eResource().getURIFragment(view),
						EMFCoreUtil.getQualifiedName(element, true), diagnostic.getMessage(),
						diagnosticToStatusSeverity(diagnostic.getSeverity()));
			}
		}
	}

	/**
	 * @generated
	 */
	private static void addMarker(EditPartViewer viewer, IFile target, String elementId, String location,
			String message, int statusSeverity) {
		if (target == null) {
			return;
		}
		HenshinMarkerNavigationProvider.addMarker(target, elementId, location, message, statusSeverity);
	}

	/**
	 * @generated
	 */
	private static int diagnosticToStatusSeverity(int diagnosticSeverity) {
		if (diagnosticSeverity == Diagnostic.OK) {
			return IStatus.OK;
		} else if (diagnosticSeverity == Diagnostic.INFO) {
			return IStatus.INFO;
		} else if (diagnosticSeverity == Diagnostic.WARNING) {
			return IStatus.WARNING;
		} else if (diagnosticSeverity == Diagnostic.ERROR || diagnosticSeverity == Diagnostic.CANCEL) {
			return IStatus.ERROR;
		}
		return IStatus.INFO;
	}

	/**
	 * @generated
	 */
	private static Set<EObject> collectTargetElements(IStatus status, Set<EObject> targetElementCollector,
			List<IConstraintStatus> allConstraintStatuses) {
		if (status instanceof IConstraintStatus) {
			IConstraintStatus constraintStatus = (IConstraintStatus) status;
			targetElementCollector.add(constraintStatus.getTarget());
			allConstraintStatuses.add(constraintStatus);
		}
		if (status.isMultiStatus()) {
			for (IStatus child : status.getChildren()) {
				collectTargetElements(child, targetElementCollector, allConstraintStatuses);
			}
		}
		return targetElementCollector;
	}

	/**
	 * @generated
	 */
	private static Set<EObject> collectTargetElements(Diagnostic diagnostic, Set<EObject> targetElementCollector,
			List<Diagnostic> allDiagnostics) {
		List<?> data = diagnostic.getData();
		EObject target = null;
		if (data != null && !data.isEmpty() && data.get(0) instanceof EObject) {
			target = (EObject) data.get(0);
			targetElementCollector.add(target);
			allDiagnostics.add(diagnostic);
		}
		if (diagnostic.getChildren() != null && !diagnostic.getChildren().isEmpty()) {
			for (Diagnostic child : diagnostic.getChildren()) {
				collectTargetElements(child, targetElementCollector, allDiagnostics);
			}
		}
		return targetElementCollector;
	}
}
