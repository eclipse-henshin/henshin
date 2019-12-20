package org.eclipse.emf.henshin.variability.configuration.ui.actions;

import org.eclipse.emf.henshin.variability.configuration.ui.parts.IContentView;
import org.eclipse.emf.henshin.variability.configuration.ui.providers.ConfigurationProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;

import configuration.Configuration;
import configuration.Favorite;

/**
 * This class allows loading favorited configurations.
 * 
 * @author Stefan Schulz
 *
 */
public class LoadFavoriteConfigurationAction extends Action {
	
	private Favorite favorite;
	private IContentView<Configuration> contentView;
	private ConfigurationProvider configurationProvider = ConfigurationProvider.getInstance();
	
	public LoadFavoriteConfigurationAction(Favorite favorite, IContentView<Configuration> contentView) {
		super(favorite.getName(), IAction.AS_RADIO_BUTTON);
		this.favorite = favorite;
		this.contentView = contentView;
	}
	
	@Override
	public void run() {
		Configuration configuration = configurationProvider.getConfiguration(favorite);
		contentView.setContent(configuration);
	}
	
	public Configuration getConfiguration() {
		return favorite;
	}
}
