package normchan.mj.game.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SortedList<T extends Comparable<? super T>> extends ArrayList<T> {

	@Override
	public void add(int index, T element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(T e) {
		boolean retVal = super.add(e);
		Collections.sort(this);
		
		return retVal;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		throw new UnsupportedOperationException();
	}

}
