package datamining;

import java.util.Comparator;

import com.google.common.collect.*;
import com.google.common.collect.Multiset.Entry;
import com.google.common.primitives.Ints;

/**
 * @author jwfergus
 *
 */
public enum HashMultisetComparator implements Comparator<HashMultiset.Entry<?>> {
	/**
	 * 
	 */
	ASCENDING {
		@Override
		public int compare(Entry<?> first, Entry<?> second) {
			return Ints.compare(first.getCount(), second.getCount());
		}
	},
	/**
	 * 
	 */
	DESCENDING {
		@Override
		public int compare(Entry<?> first, Entry<?> second) {
			return Ints.compare(second.getCount(), first.getCount());
		}
	}

}
