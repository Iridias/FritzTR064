package de.mapoll.javaAVMTR064.model.dect;

public class SpecificDectEntry {
	
	private final String id;
	private final boolean active;
	private final String name;
	private final String model;
	private final boolean updateAvailable;
	private final UpdateStatus updateStatus;
	private final String updateInfo;
	
	SpecificDectEntry(final Builder b) {
		this.id = b.id;
		this.active = b.active;
		this.name = b.name;
		this.model = b.model;
		this.updateAvailable = b.updateAvailable;
		this.updateStatus = b.updateStatus;
		this.updateInfo = b.updateInfo;
	}
	
	public static class Builder {
		private String id;
		private boolean active;
		private String name;
		private String model;
		private boolean updateAvailable;
		private UpdateStatus updateStatus;
		private String updateInfo;
		
		public Builder setId(final String id) {
			this.id = id;
			return this;
		}
		
		public Builder setActive(final boolean active) {
			this.active = active;
			return this;
		}
		
		public Builder setName(final String name) {
			this.name = name;
			return this;
		}
		
		public Builder setModel(final String model) {
			this.model = model;
			return this;
		}
		
		public Builder setUpdateAvailable(final boolean updateAvailable) {
			this.updateAvailable = updateAvailable;
			return this;
		}
		
		public Builder setUpdateStatus(final UpdateStatus updateStatus) {
			this.updateStatus = updateStatus;
			return this;
		}
		
		public Builder setUpdateInfo(final String updateInfo) {
			this.updateInfo = updateInfo;
			return this;
		}
		
		public SpecificDectEntry build() {
			return new SpecificDectEntry(this);
		}
	}
	
	public String getId() {
		return id;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public String getName() {
		return name;
	}
	
	public String getModel() {
		return model;
	}
	
	public boolean isUpdateAvailable() {
		return updateAvailable;
	}
	
	public UpdateStatus getUpdateStatus() {
		return updateStatus;
	}
	
	public String getUpdateInfo() {
		return updateInfo;
	}
	
	@Override
	public String toString() {
		return "SpecificDectEntry{" +
				"id='" + id + '\'' +
				", active=" + active +
				", name='" + name + '\'' +
				", model='" + model + '\'' +
				", updateAvailable=" + updateAvailable +
				", updateStatus=" + updateStatus +
				", updateInfo='" + updateInfo + '\'' +
				'}';
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		SpecificDectEntry that = (SpecificDectEntry) o;
		
		if (active != that.active) return false;
		if (updateAvailable != that.updateAvailable) return false;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (model != null ? !model.equals(that.model) : that.model != null) return false;
		if (updateStatus != that.updateStatus) return false;
		return updateInfo != null ? updateInfo.equals(that.updateInfo) : that.updateInfo == null;
	}
	
	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (active ? 1 : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (model != null ? model.hashCode() : 0);
		result = 31 * result + (updateAvailable ? 1 : 0);
		result = 31 * result + (updateStatus != null ? updateStatus.hashCode() : 0);
		result = 31 * result + (updateInfo != null ? updateInfo.hashCode() : 0);
		return result;
	}
}
