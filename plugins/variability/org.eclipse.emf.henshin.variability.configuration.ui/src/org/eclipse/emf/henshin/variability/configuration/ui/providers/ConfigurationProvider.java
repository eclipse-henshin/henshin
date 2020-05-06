package org.eclipse.emf.henshin.variability.configuration.ui.providers;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.wrapper.TransactionalVariabilityFactory;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityRule;

import configuration.Configuration;
import configuration.ConfigurationFactory;
import configuration.Favorite;
import configuration.Feature;
import configuration.impl.ConfigurationFactoryImpl;

/**
 * This class helps managing configurations.
 * 
 * @author Stefan Schulz
 *
 */
public class ConfigurationProvider {

	private final static String REGEX = "def\\((.*?)\\)";

	private Map<Rule, Configuration> currentConfigurations = new HashMap<Rule, Configuration>();
	private Map<Rule, Set<Favorite>> favoriteConfigurations = new HashMap<Rule, Set<Favorite>>();
	private static ConfigurationProvider vpProvider = new ConfigurationProvider();
	ConfigurationFactory fac = ConfigurationFactory.eINSTANCE;

	public static ConfigurationProvider getInstance() {
		return vpProvider;
	}

	public Configuration getConfiguration(Rule rule) {
		Configuration result = null;

		if (currentConfigurations.containsKey(rule)) {
			result = currentConfigurations.get(rule);
		} else {
			result = createConfiguration(rule);
		}

		return result;
	}
	
	public Configuration getConfiguration(Favorite favorite) {
		Configuration result = null;
		
		result = fac.createConfiguration(favorite);
		result.setRule(favorite.getRule());
		currentConfigurations.put(result.getRule(), result);

		return result;
	}

	public Favorite addConfigurationToFavorites(Rule rule, String name, Configuration configuration) {
		if (favoriteConfigurations.get(rule) == null) {
			favoriteConfigurations.put(rule, new LinkedHashSet<Favorite>());
		}
		Favorite favorite = fac.createFavorite(configuration);
		favorite.setRule(rule);
		favorite.setName(name);

		favoriteConfigurations.get(rule).add(favorite);
		return favorite;
	}
	
	private Favorite findMatchingFavorite(Configuration configuration) {
		Favorite result = null;
		Set<Favorite> ruleFavorites = favoriteConfigurations.get(configuration.getRule());

		if (ruleFavorites != null) {
			for (Favorite favorite : ruleFavorites) {
				if (favorite.getFeatures().size() == configuration.getFeatures().size()) {
					int matches = 0;
					 /* 
					  * TODO: This needs to be improved
					  */
					for (int i = favorite.getFeatures().size() - 1; i >= 0; i--) {
						Feature favVP = favorite.getFeatures().get(i);
						Feature conVP = configuration.getFeatures().get(i);

						if (conVP.getName().equals(favVP.getName()) && conVP.getBinding() == favVP.getBinding()) {
							matches++;
						}
					}
					if (matches == configuration.getFeatures().size()) {
						result = favorite;
						break;
					}
				}
			}
		}
		return result;
	}
	
	public void clearFavorites(Configuration configuration) {
		Set<Favorite> favorites = favoriteConfigurations.get(configuration.getRule());
		if (favorites != null) {
			favorites.clear();
		}
	}
	
	public void removeConfigurationFromFavorites(Configuration configuration) {
		Favorite favorite = findMatchingFavorite(configuration);
		
		if(favorite != null) {
			favoriteConfigurations.get(configuration.getRule()).remove(favorite);
		}
	}

	public boolean isFavorite(Configuration configuration) {
		return findMatchingFavorite(configuration) != null;
	}
	
	public Favorite findFavorite(Configuration configuration) {
		return findMatchingFavorite(configuration);
	}

	public void addFavorites(Rule rule, Set<Favorite> favoritesSet) {
		favoriteConfigurations.put(rule, favoritesSet);
	}

	public Set<Favorite> getFavorites(Rule rule) {
		return favoriteConfigurations.get(rule);
	}

	private Configuration createConfiguration(Rule rule) {
		Configuration result = null;

		if (rule != null) {
			VariabilityRule varRule = TransactionalVariabilityFactory.INSTANCE.createVariabilityRule(rule);
			ConfigurationFactory fac = ConfigurationFactoryImpl.init();
			result = fac.createConfiguration();
			result.setRule(varRule);
			String featureConstraint = varRule.getFeatureConstraint();
			EList<Feature> variabilityPoints = result.getFeatures();

			if(varRule.getFeatures() != null) {
				for(String variabilityPointName : varRule.getFeatures()) {
					Feature vp = fac.createFeature();
					vp.setName(variabilityPointName);
					variabilityPoints.add(vp);
				}
			} else if (featureConstraint != null && !featureConstraint.isEmpty()) {
				Matcher match = Pattern.compile(REGEX).matcher(featureConstraint);
				while (match.find()) {
					for (int i = 1; i <= match.groupCount(); i++) {
						Feature vp = fac.createFeature();
						vp.setName(match.group(i));
						variabilityPoints.add(vp);
					}
				}
			}
			currentConfigurations.put(rule, result);
		}

		return result;
	}
}
