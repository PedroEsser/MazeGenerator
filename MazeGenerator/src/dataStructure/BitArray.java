package dataStructure;

import java.math.BigInteger;
import java.util.Iterator;

public class BitArray implements Iterable<Boolean> {

	public static final int ALL_ONES = 0xFFFFFFFF;
	private final int size;
	private final int[] bits;
	private static final int BITSPERINT = 32;
	
	public BitArray(int size) {
		this.size = size;
		int dataLength = size % BITSPERINT == 0 ? size/BITSPERINT : size/BITSPERINT+1;
		bits = new int[dataLength];
	}
	
	public boolean getBit(int pos) {
		if(pos<0)
			throw new ArrayIndexOutOfBoundsException("Position can't be negative! " + pos);
        return (bits[pos / BITSPERINT] & (1 << (pos % BITSPERINT))) != 0;
    }
	
	public void setBit(int pos, boolean b) {
		if(pos<0)
			throw new ArrayIndexOutOfBoundsException("Position can't be negative! " + pos);
		int word = bits[pos / BITSPERINT];
		int posBit = 1 << (pos % BITSPERINT);
        if (b) 
            word |= posBit;
        else 
            word &= (ALL_ONES - posBit);
        bits[pos / BITSPERINT] = word;
    }
	
	public boolean validPosition(int position) {
		return position >= 0 && position < size;
	}
	
	public int size() {
		return size;
	}
	
	public String getValue() {
		BigInteger result = BigInteger.ZERO;
		for(int i = bits.length - 1 ; i >= 0 ; i--){
			result = result.shiftLeft(BITSPERINT);
			result = result.add(new BigInteger(bits[i]+""));
		}
		return result + "";
	}

	@Override
	public Iterator<Boolean> iterator() {
		return new BitIterator();
	}
	
	private class BitIterator implements Iterator<Boolean>{

		private int current;
		
		@Override
		public boolean hasNext() {
			return current!=size;
		}

		@Override
		public Boolean next() {
			return getBit(current++);
		}
		
	}
	
}
