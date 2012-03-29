package org.eclipse.emf.henshin.statespace.tuples;

import java.util.List;
import java.util.ArrayList;

import org.eclipse.emf.ecore.EObject;

class EcoreColumnSorter {
	
	public enum ColumnType {
		EMPTY, TYPE, ATTRIBUTE, REFERENCE;
	};
	
	public static class ColumnInfo {
		
		// column type:
		public final ColumnType type;
		
		// Object position in the containment tree:
		public final int y,x;
		
		// Index of the attribute / reference:
		public final int index;
		
		public ColumnInfo(ColumnType type, int y, int x, int index) {
			this.type = type;
			this.y = y;
			this.x = x;
			this.index = index;
		}
		
	};
	
	private List<ColumnInfo> infos = new ArrayList<ColumnInfo>();
	
	public EcoreColumnSorter() {
	}
	
	public void setColumnInfo(int column, ColumnInfo info) {
		while (infos.size()<=column) {
			infos.add(new ColumnInfo(ColumnType.EMPTY, 0, 0, 0));
		}
		infos.set(column, info);
	}
	
	public void sortColumns(Tuple tuple, EObject[][] layers) {
		
	}
	
}
