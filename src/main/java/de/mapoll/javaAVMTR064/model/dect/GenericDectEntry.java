package de.mapoll.javaAVMTR064.model.dect;

public class GenericDectEntry extends SpecificDectEntry {
	
	private final int index;
	
	private GenericDectEntry(final Builder b) {
		super(b);
		this.index = b.index;
	}
	
	public static class Builder extends SpecificDectEntry.Builder {
		private int index;
		
		public Builder setIndex(final int index) {
			this.index = index;
			return this;
		}
		
		public GenericDectEntry build() {
			return new GenericDectEntry(this);
		}
	}
	
	public int getIndex() {
		return index;
	}
	
	@Override
	public String toString() {
		return "GenericDectEntry{" +
				"index=" + index +
				"} " + super.toString();
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		
		GenericDectEntry that = (GenericDectEntry) o;
		
		return index == that.index;
	}
	
	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + index;
		return result;
	}
}
