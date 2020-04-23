package dataStructure;

import java.math.BigInteger;
import java.util.Iterator;

public class BitArray implements Iterable<Boolean> {

	private static final int ALL_ONES = 0xFFFFFFFF;
	private static final int SOME_ONES = 0x0000001F;
	private static final int HELPER = 5;
	private static final int BITS_PER_INT = 32;
	private final int size;
	private final int[] bits;

	public BitArray(int size) {
		this.size = size;
		int dataLength = (size & SOME_ONES) == 0 ? size >> HELPER : (size >> HELPER) + 1;	//ceiling
		bits = new int[dataLength];
	}

	public boolean getBit(int pos) {
		if (!validPosition(pos))
			throw new ArrayIndexOutOfBoundsException("Index " + pos + " out of bounds for length " + size);
		return (bits[pos >> HELPER] & (1 << (pos & SOME_ONES))) != 0;
	}

	public void setBit(int pos, boolean b) {
		if (!validPosition(pos))
			throw new ArrayIndexOutOfBoundsException("Index " + pos + " out of bounds for length " + size);
		int word = bits[pos >> HELPER];
		int posBit = 1 << (pos & SOME_ONES);
		if (b)
			word |= posBit;
		else
			word &= (ALL_ONES - posBit);
		bits[pos >> HELPER] = word;
	}

	public boolean validPosition(int position) {
		return position >= 0 && position < size;
	}

	public int size() {
		return size;
	}

	public String getValue() {
		BigInteger result = BigInteger.ZERO;
		for (int i = bits.length - 1; i >= 0; i--) {
			result = result.shiftLeft(BITS_PER_INT);
			result = result.add(new BigInteger(bits[i] + ""));
		}
		return result + "";
	}

	@Override
	public Iterator<Boolean> iterator() {
		return new BitIterator();
	}

	private class BitIterator implements Iterator<Boolean> {

		private int current;

		@Override
		public boolean hasNext() {
			return current != size;
		}

		@Override
		public Boolean next() {
			return getBit(current++);
		}

	}

}
