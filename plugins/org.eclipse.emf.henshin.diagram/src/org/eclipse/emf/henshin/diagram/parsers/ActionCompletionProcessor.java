package org.eclipse.emf.henshin.diagram.parsers;

import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.jface.contentassist.IContentAssistSubjectControl;
import org.eclipse.jface.contentassist.ISubjectControlContentAssistProcessor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

/**
 * Action completion processor.
 * @author Christian Krause
 */
public class ActionCompletionProcessor implements IContentAssistProcessor, ISubjectControlContentAssistProcessor {

	private final IContextInformation[] NO_CONTEXTS = { };
	private final char[] PROPOSAL_ACTIVATION_CHARS = { 'c','d','f','n','m' };
	private ICompletionProposal[] NO_COMPLETIONS = { };

	/**
	 * Context rule.
	 */
	private Rule rule;

	/**
	 * Default constructor.
	 */
	public ActionCompletionProcessor() {
	}

	/**
	 * Alternate constructor for specifying a context rule.
	 * @param rule The rule.
	 */
	public ActionCompletionProcessor(Rule rule) {
		this.rule = rule;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.contentassist.ISubjectControlContentAssistProcessor#computeCompletionProposals(org.eclipse.jface.contentassist.IContentAssistSubjectControl, int)
	 */
	@Override
	public ICompletionProposal[] computeCompletionProposals(IContentAssistSubjectControl control, int offset) {
		return computeCompletionProposals(control.getDocument(), offset);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeCompletionProposals(org.eclipse.jface.text.ITextViewer, int)
	 */
	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		return computeCompletionProposals(viewer.getDocument(), offset);
	}
	
	private ICompletionProposal[] computeCompletionProposals(IDocument document, final int offset) {
		try {
			String edit = document.get(0, offset);
			for (Action.Type type : Action.Type.values()) {
				final String name = type.toString();
				if (name.startsWith(edit)) {
					return new ICompletionProposal[] {
						new ICompletionProposal() {
							@Override
							public void apply(IDocument document) {
								document.set(name);
							}
							@Override
							public Point getSelection(IDocument document) {
								return null;
							}
							@Override
							public String getAdditionalProposalInfo() {
								return null;
							}
							@Override
							public String getDisplayString() {
								return name;
							}
							@Override
							public Image getImage() {
								return null;
							}
							@Override
							public IContextInformation getContextInformation() {
								return null;
							}
						}
					};
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NO_COMPLETIONS;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getCompletionProposalAutoActivationCharacters()
	 */
	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return PROPOSAL_ACTIVATION_CHARS;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#computeContextInformation(org.eclipse.jface.text.ITextViewer, int)
	 */
	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) { 
		return NO_CONTEXTS;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationAutoActivationCharacters()
	 */
	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getErrorMessage()
	 */
	@Override
	public String getErrorMessage() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.text.contentassist.IContentAssistProcessor#getContextInformationValidator()
	 */
	@Override
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.contentassist.ISubjectControlContentAssistProcessor#computeContextInformation(org.eclipse.jface.contentassist.IContentAssistSubjectControl, int)
	 */
	@Override
	public IContextInformation[] computeContextInformation(IContentAssistSubjectControl control, int offset) {
		return null;
	}

}
