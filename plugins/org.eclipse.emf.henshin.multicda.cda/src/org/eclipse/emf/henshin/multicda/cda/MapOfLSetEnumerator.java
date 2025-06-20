package org.eclipse.emf.henshin.multicda.cda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

/**
 * Courtesy of tskuzzy @ StackOverflow:
 * https://stackoverflow.com/questions/8173862/map-of-sets-into-list-of-all-combinations
 * @author strueber
 *
 */
public class MapOfLSetEnumerator {
    // method called to generate combinations using map, putting the combinations in list
    public static <K,V> void combinations( Map<K,Set<V>> map, List<Map<K,V>> list ) {
		recurse(map, new ArrayList<K>(map.keySet()).listIterator(), new HashMap<>(), list);
    }

    // helper method to do the recursion
    private static <K,V> void recurse( Map<K,Set<V>> map, ListIterator<K> iter, Map<K,V> cur, List<Map<K,V>> list ) {
            // we're at a leaf node in the recursion tree, add solution to list
        if( !iter.hasNext() ) {
            Map<K,V> entry = new HashMap<K,V>();

            for( K key : cur.keySet() ) {
                entry.put( key, cur.get( key ) );
            }

            list.add( entry );
        } else {
            K key = iter.next();
            Set<V> set = map.get( key );

            for( V value : set ) {
                cur.put( key, value );
                recurse( map, iter, cur, list );
                cur.remove( key );
            }

            iter.previous();
        }
    }

    public static void main( String[] args ) {
		Map<Integer, Set<Integer>> map = new HashMap<>();
		map.put(1, new HashSet<>(Set.of(11, 12)));
		map.put(2, new HashSet<>(Set.of(21, 22, 23)));
		map.put(3, new HashSet<>(Set.of(31)));
		List<Map<Integer, Integer>> list = new ArrayList<>();
        combinations( map, list );

        for( Map<Integer,Integer> combination : list ) {
            System.out.println( combination );
        }
    }
}