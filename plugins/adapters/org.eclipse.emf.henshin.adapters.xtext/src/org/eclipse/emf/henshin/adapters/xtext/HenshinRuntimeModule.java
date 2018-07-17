package org.eclipse.emf.henshin.adapters.xtext;

import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.resource.generic.AbstractGenericResourceRuntimeModule;

public class HenshinRuntimeModule extends AbstractGenericResourceRuntimeModule {

	@Override
	protected String getLanguageName() {
		return "org.eclipse.emf.henshin.presentation.HenshinEditorID";
	}

	@Override
	protected String getFileExtensions() {
		return "henshin";
	}
		
	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return HenshinQualifiedNameProvider.class;
	}
}