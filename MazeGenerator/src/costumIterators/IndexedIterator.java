package costumIterators;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public abstract class IndexedIterator implements Iterable<BufferedImage>{

	protected abstract boolean validIndex(int index);
	
	public abstract BufferedImage get(int index) ;
	
	@Override
	public Iterator<BufferedImage> iterator() {
		return new MyIterator();
	}


	private class MyIterator implements Iterator<BufferedImage>{

		private int i = 0;
		
		@Override
		public boolean hasNext() {
			return validIndex(i);
		}

		@Override
		public BufferedImage next() {
			return get(i++);
		}
		
	}
	
}
