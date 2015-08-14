package org.eclipse.emf.henshin.sam.paf;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.henshin.sam.paf.annotation.ResultDictEntry;

public abstract class AbstractConsumer<T> implements IConsumer<T> {

	protected Map<String, String> resultDictionary = null;
	
	private Map<String, Boolean> options;
	
	private Map<String, Boolean> getOptions() {
		if (this.options == null) {
			this.options = this.getFilterDispatcher().getOptionsFor(this.getClass());
		}
		return this.options;
	}
	
	protected void println(String s) {
		this.getFilterDispatcher().outputStream.println(s);
	}
	
	protected Boolean getOption(String name) throws RuntimeException{
		Boolean result = this.getOptions().get(name);
		if (result == null) {
			RuntimeException e = new UnknownOptionException("Consumer " + this.getName() + " does not register an option '" + name + "'");
			PAFActivator.getDefault().getLog().log(new Status(IStatus.ERROR, PAFActivator.PLUGIN_ID, "Configuration option '" + name + "' does not exist for consumer '" + this.getName() + "'.", e)); //$NON-NLS-1$
			throw e;
		} else {
			return result;
		}		
	}
	
	@Override
	public Map<String, String> getResult() {
	   if (this.resultDictionary == null) {
		   this.prepareResult();
	   }
	   return this.resultDictionary;
	}
	
	protected void prepareResult() {
		this.resultDictionary = new LinkedHashMap<String, String>();
		final Class<?> clazz = getClass();
		for (Field f : clazz.getDeclaredFields()) {
			ResultDictEntry rde = f.getAnnotation(ResultDictEntry.class);
			if (rde != null) {				
				Object val = null;
				boolean exception = false;
				try {
					if (f.isAccessible() == false)
						f.setAccessible(true);
					val = f.get(this);
				} catch (IllegalArgumentException e) {
					exception = true;
				} catch (IllegalAccessException e) {
					exception = true;
				}
				if (exception == false) {
					if (val == null) {
						val = "Property not set! (null)";
					}
					this.resultDictionary.put(rde.entryName(), val.toString());
				}
			}
		}
		Map<String, Boolean> options = this.getOptions();
		for (String key : options.keySet()) {
			if (key.contains(this.getClass().getSimpleName())) {
				this.resultDictionary.put(key.replaceAll(".*?:", ""), options.get(key).toString());
			}
		}
	}
	
	protected void addToResult(String key, String value) {
		if (this.resultDictionary == null) {
			   this.prepareResult();
		}
		this.resultDictionary.put(key, value);
	}
}