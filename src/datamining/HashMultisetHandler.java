package datamining;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset.Entry;

/**
 * @author jwfergus
 *
 */
public class HashMultisetHandler {

	HashMultiset<String> hashMultiset;
	/**
	 * @param hashMultiset
	 */
	public HashMultisetHandler(HashMultiset<String> hashMultiset){
		this.hashMultiset = hashMultiset;
	}
	public HashMultisetHandler(){}
	
	/**
	 * @param <E>
	 * @param hashMultiset
	 * @param ascendingOrDescendingEnum
	 * @return
	 */
	public <E> List<Entry<E>> getSortedListFromHashMultiset(HashMultiset<E> hashMultiset, HashMultisetComparator ascendingOrDescendingEnum){
		List<Entry<E>> list = Lists.newArrayList(hashMultiset.entrySet());
		Collections.sort(list, ascendingOrDescendingEnum);
		return list;
	}
}
