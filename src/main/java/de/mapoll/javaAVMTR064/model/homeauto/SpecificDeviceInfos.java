package de.mapoll.javaAVMTR064.model.homeauto;

public class SpecificDeviceInfos {
	
	private final String deviceIdentifier;
	private final int deviceId;
	private final int functionBitMask;
	private final String firmwareVersion;
	private final String manufacturer;
	private final String productName;
	private final String deviceName;
	private final ConnectionStatus connectionStatus;
	private final EnabledStatus multimeterStatus;
	private final Validity multimeterValidity;
	private final int multimeterPower;
	private final int multimeterEnergy;
	private final EnabledStatus temperatureStatus;
	private final Validity temperatureValidity;
	private final int temperatureCelcius;
	private final int temperatureOffset;
	private final EnabledStatus switchStatus;
	private final Validity switchValidity;
	private final SwitchState switchState;
	private final SwitchTimeMode switchTimeMode;
	private final boolean switchKeyLock;
	private final EnabledStatus thermostatStatus;
	private final Validity thermostatValidity;
	private final int thermostatCurrentTemperature;
	private final ValveStatus thermostatTargetValveStatus;
	private final int thermostatTargetTemperature;
	private final ValveStatus thermostatReduceValveStatus;
	private final int thermostatReduceTemperature;
	private final ValveStatus thermostatComfortValveStatus;
	private final int thermostatComfortTemperature;
	
	SpecificDeviceInfos(final Builder b) {
		 this.deviceIdentifier = b.deviceIdentifier;
		 this.deviceId = b.deviceId;
		 this.functionBitMask = b.functionBitMask;
		 this.firmwareVersion = b.firmwareVersion;
		 this.manufacturer = b.manufacturer;
		 this.productName = b.productName;
		 this.deviceName = b.deviceName;
		 this.connectionStatus = b.connectionStatus;
		 this.multimeterStatus = b.multimeterStatus;
		 this.multimeterValidity = b.multimeterValidity;
		 this.multimeterPower = b.multimeterPower;
		 this.multimeterEnergy = b.multimeterEnergy;
		 this.temperatureStatus = b.temperatureStatus;
		 this.temperatureValidity = b.temperatureValidity;
		 this.temperatureCelcius = b.temperatureCelcius;
		 this.temperatureOffset = b.temperatureOffset;
		 this.switchStatus = b.switchStatus;
		 this.switchValidity = b.switchValidity;
		 this.switchState = b.switchState;
		 this.switchTimeMode = b.switchTimeMode;
		 this.switchKeyLock = b.switchKeyLock;
		 this.thermostatStatus = b.thermostatStatus;
		 this.thermostatValidity = b.thermostatValidity;
		 this.thermostatCurrentTemperature = b.thermostatCurrentTemperature;
		 this.thermostatTargetValveStatus = b.thermostatTargetValveStatus;
		 this.thermostatTargetTemperature = b.thermostatTargetTemperature;
		 this.thermostatReduceValveStatus = b.thermostatReduceValveStatus;
		 this.thermostatReduceTemperature = b.thermostatReduceTemperature;
		 this.thermostatComfortValveStatus = b.thermostatComfortValveStatus;
		 this.thermostatComfortTemperature = b.thermostatComfortTemperature;
	}
	
	public static class Builder {
		private String deviceIdentifier;
		private int deviceId;
		private int functionBitMask;
		private String firmwareVersion;
		private String manufacturer;
		private String productName;
		private String deviceName;
		private ConnectionStatus connectionStatus;
		private EnabledStatus multimeterStatus;
		private Validity multimeterValidity;
		private int multimeterPower;
		private int multimeterEnergy;
		private EnabledStatus temperatureStatus;
		private Validity temperatureValidity;
		private int temperatureCelcius;
		private int temperatureOffset;
		private EnabledStatus switchStatus;
		private Validity switchValidity;
		private SwitchState switchState;
		private SwitchTimeMode switchTimeMode;
		private boolean switchKeyLock;
		private EnabledStatus thermostatStatus;
		private Validity thermostatValidity;
		private int thermostatCurrentTemperature;
		private ValveStatus thermostatTargetValveStatus;
		private int thermostatTargetTemperature;
		private ValveStatus thermostatReduceValveStatus;
		private int thermostatReduceTemperature;
		private ValveStatus thermostatComfortValveStatus;
		private int thermostatComfortTemperature;
		
		public Builder setDeviceIdentifier(final String deviceIdentifier) {
			this.deviceIdentifier = deviceIdentifier;
			return this;
		}
		
		public Builder setDeviceId(final int deviceId) {
			this.deviceId = deviceId;
			return this;
		}
		
		public Builder setFunctionBitMask(final int functionBitMask) {
			this.functionBitMask = functionBitMask;
			return this;
		}
		
		public Builder setFirmwareVersion(final String firmwareVersion) {
			this.firmwareVersion = firmwareVersion;
			return this;
		}
		
		public Builder setManufacturer(final String manufacturer) {
			this.manufacturer = manufacturer;
			return this;
		}
		
		public Builder setProductName(final String productName) {
			this.productName = productName;
			return this;
		}
		
		public Builder setDeviceName(final String deviceName) {
			this.deviceName = deviceName;
			return this;
		}
		
		public Builder setConnectionStatus(final ConnectionStatus connectionStatus) {
			this.connectionStatus = connectionStatus;
			return this;
		}
		
		public Builder setMultimeterStatus(final EnabledStatus multimeterStatus) {
			this.multimeterStatus = multimeterStatus;
			return this;
		}
		
		public Builder setMultimeterValidity(final Validity multimeterValidity) {
			this.multimeterValidity = multimeterValidity;
			return this;
		}
		
		public Builder setMultimeterPower(final int multimeterPower) {
			this.multimeterPower = multimeterPower;
			return this;
		}
		
		public Builder setMultimeterEnergy(final int multimeterEnergy) {
			this.multimeterEnergy = multimeterEnergy;
			return this;
		}
		
		public Builder setTemperatureStatus(final EnabledStatus temperatureStatus) {
			this.temperatureStatus = temperatureStatus;
			return this;
		}
		
		public Builder setTemperatureValidity(final Validity temperatureValidity) {
			this.temperatureValidity = temperatureValidity;
			return this;
		}
		
		public Builder setTemperatureCelcius(final int temperatureCelcius) {
			this.temperatureCelcius = temperatureCelcius;
			return this;
		}
		
		public Builder setTemperatureOffset(final int temperatureOffset) {
			this.temperatureOffset = temperatureOffset;
			return this;
		}
		
		public Builder setSwitchStatus(final EnabledStatus switchStatus) {
			this.switchStatus = switchStatus;
			return this;
		}
		
		public Builder setSwitchValidity(final Validity switchValidity) {
			this.switchValidity = switchValidity;
			return this;
		}
		
		public Builder setSwitchState(final SwitchState switchState) {
			this.switchState = switchState;
			return this;
		}
		
		public Builder setSwitchTimeMode(final SwitchTimeMode switchTimeMode) {
			this.switchTimeMode = switchTimeMode;
			return this;
		}
		
		public Builder setSwitchKeyLock(final boolean switchKeyLock) {
			this.switchKeyLock = switchKeyLock;
			return this;
		}
		
		public Builder setThermostatStatus(final EnabledStatus thermostatStatus) {
			this.thermostatStatus = thermostatStatus;
			return this;
		}
		
		public Builder setThermostatValidity(final Validity thermostatValidity) {
			this.thermostatValidity = thermostatValidity;
			return this;
		}
		
		public Builder setThermostatCurrentTemperature(final int thermostatCurrentTemperature) {
			this.thermostatCurrentTemperature = thermostatCurrentTemperature;
			return this;
		}
		
		public Builder setThermostatTargetValveStatus(final ValveStatus thermostatTargetValveStatus) {
			this.thermostatTargetValveStatus = thermostatTargetValveStatus;
			return this;
		}
		
		public Builder setThermostatTargetTemperature(final int thermostatTargetTemperature) {
			this.thermostatTargetTemperature = thermostatTargetTemperature;
			return this;
		}
		
		public Builder setThermostatReduceValveStatus(final ValveStatus thermostatReduceValveStatus) {
			this.thermostatReduceValveStatus = thermostatReduceValveStatus;
			return this;
		}
		
		public Builder setThermostatReduceTemperature(final int thermostatReduceTemperature) {
			this.thermostatReduceTemperature = thermostatReduceTemperature;
			return this;
		}
		
		public Builder setThermostatComfortValveStatus(final ValveStatus thermostatComfortValveStatus) {
			this.thermostatComfortValveStatus = thermostatComfortValveStatus;
			return this;
		}
		
		public Builder setThermostatComfortTemperature(final int thermostatComfortTemperature) {
			this.thermostatComfortTemperature = thermostatComfortTemperature;
			return this;
		}
		
		public SpecificDeviceInfos build() {
			return new SpecificDeviceInfos(this);
		}
	}
	
	public String getDeviceIdentifier() {
		return deviceIdentifier;
	}
	
	public int getDeviceId() {
		return deviceId;
	}
	
	public int getFunctionBitMask() {
		return functionBitMask;
	}
	
	public String getFirmwareVersion() {
		return firmwareVersion;
	}
	
	public String getManufacturer() {
		return manufacturer;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public String getDeviceName() {
		return deviceName;
	}
	
	public ConnectionStatus getConnectionStatus() {
		return connectionStatus;
	}
	
	public EnabledStatus getMultimeterStatus() {
		return multimeterStatus;
	}
	
	public Validity getMultimeterValidity() {
		return multimeterValidity;
	}
	
	public int getMultimeterPower() {
		return multimeterPower;
	}
	
	public int getMultimeterEnergy() {
		return multimeterEnergy;
	}
	
	public EnabledStatus getTemperatureStatus() {
		return temperatureStatus;
	}
	
	public Validity getTemperatureValidity() {
		return temperatureValidity;
	}
	
	public int getTemperatureCelcius() {
		return temperatureCelcius;
	}
	
	public int getTemperatureOffset() {
		return temperatureOffset;
	}
	
	public EnabledStatus getSwitchStatus() {
		return switchStatus;
	}
	
	public Validity getSwitchValidity() {
		return switchValidity;
	}
	
	public SwitchState getSwitchState() {
		return switchState;
	}
	
	public SwitchTimeMode getSwitchTimeMode() {
		return switchTimeMode;
	}
	
	public boolean isSwitchKeyLock() {
		return switchKeyLock;
	}
	
	public EnabledStatus getThermostatStatus() {
		return thermostatStatus;
	}
	
	public Validity getThermostatValidity() {
		return thermostatValidity;
	}
	
	public int getThermostatCurrentTemperature() {
		return thermostatCurrentTemperature;
	}
	
	public ValveStatus getThermostatTargetValveStatus() {
		return thermostatTargetValveStatus;
	}
	
	public int getThermostatTargetTemperature() {
		return thermostatTargetTemperature;
	}
	
	public ValveStatus getThermostatReduceValveStatus() {
		return thermostatReduceValveStatus;
	}
	
	public int getThermostatReduceTemperature() {
		return thermostatReduceTemperature;
	}
	
	public ValveStatus getThermostatComfortValveStatus() {
		return thermostatComfortValveStatus;
	}
	
	public int getThermostatComfortTemperature() {
		return thermostatComfortTemperature;
	}
	
	@Override
	public String toString() {
		return "GenericDeviceInfos{" +
				", deviceIdentifier='" + deviceIdentifier + '\'' +
				", deviceId=" + deviceId +
				", functionBitMask=" + functionBitMask +
				", firmwareVersion='" + firmwareVersion + '\'' +
				", manufacturer='" + manufacturer + '\'' +
				", productName='" + productName + '\'' +
				", deviceName='" + deviceName + '\'' +
				", connectionStatus=" + connectionStatus +
				", multimeterStatus=" + multimeterStatus +
				", multimeterValidity=" + multimeterValidity +
				", multimeterPower=" + multimeterPower +
				", multimeterEnergy=" + multimeterEnergy +
				", temperatureStatus=" + temperatureStatus +
				", temperatureValidity=" + temperatureValidity +
				", temperatureCelcius=" + temperatureCelcius +
				", temperatureOffset=" + temperatureOffset +
				", switchStatus=" + switchStatus +
				", switchValidity=" + switchValidity +
				", switchState=" + switchState +
				", switchTimeMode=" + switchTimeMode +
				", switchKeyLock=" + switchKeyLock +
				", thermostatStatus=" + thermostatStatus +
				", thermostatValidity=" + thermostatValidity +
				", thermostatCurrentTemperature=" + thermostatCurrentTemperature +
				", thermostatTargetValveStatus=" + thermostatTargetValveStatus +
				", thermostatTargetTemperature=" + thermostatTargetTemperature +
				", thermostatReduceValveStatus=" + thermostatReduceValveStatus +
				", thermostatReduceTemperature=" + thermostatReduceTemperature +
				", thermostatComfortValveStatus=" + thermostatComfortValveStatus +
				", thermostatComfortTemperature=" + thermostatComfortTemperature +
				'}';
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		SpecificDeviceInfos that = (SpecificDeviceInfos) o;
		
		if (deviceId != that.deviceId) return false;
		if (functionBitMask != that.functionBitMask) return false;
		if (multimeterPower != that.multimeterPower) return false;
		if (multimeterEnergy != that.multimeterEnergy) return false;
		if (temperatureCelcius != that.temperatureCelcius) return false;
		if (temperatureOffset != that.temperatureOffset) return false;
		if (switchKeyLock != that.switchKeyLock) return false;
		if (thermostatCurrentTemperature != that.thermostatCurrentTemperature) return false;
		if (thermostatTargetTemperature != that.thermostatTargetTemperature) return false;
		if (thermostatReduceTemperature != that.thermostatReduceTemperature) return false;
		if (thermostatComfortTemperature != that.thermostatComfortTemperature) return false;
		if (deviceIdentifier != null ? !deviceIdentifier.equals(that.deviceIdentifier) : that.deviceIdentifier != null) return false;
		if (firmwareVersion != null ? !firmwareVersion.equals(that.firmwareVersion) : that.firmwareVersion != null) return false;
		if (manufacturer != null ? !manufacturer.equals(that.manufacturer) : that.manufacturer != null) return false;
		if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
		if (deviceName != null ? !deviceName.equals(that.deviceName) : that.deviceName != null) return false;
		if (connectionStatus != that.connectionStatus) return false;
		if (multimeterStatus != that.multimeterStatus) return false;
		if (multimeterValidity != that.multimeterValidity) return false;
		if (temperatureStatus != that.temperatureStatus) return false;
		if (temperatureValidity != that.temperatureValidity) return false;
		if (switchStatus != that.switchStatus) return false;
		if (switchValidity != that.switchValidity) return false;
		if (switchState != that.switchState) return false;
		if (switchTimeMode != that.switchTimeMode) return false;
		if (thermostatStatus != that.thermostatStatus) return false;
		if (thermostatValidity != that.thermostatValidity) return false;
		if (thermostatTargetValveStatus != that.thermostatTargetValveStatus) return false;
		if (thermostatReduceValveStatus != that.thermostatReduceValveStatus) return false;
		return thermostatComfortValveStatus == that.thermostatComfortValveStatus;
	}
	
	@Override
	public int hashCode() {
		int result = deviceIdentifier != null ? deviceIdentifier.hashCode() : 0;
		result = 31 * result + deviceId;
		result = 31 * result + functionBitMask;
		result = 31 * result + (firmwareVersion != null ? firmwareVersion.hashCode() : 0);
		result = 31 * result + (manufacturer != null ? manufacturer.hashCode() : 0);
		result = 31 * result + (productName != null ? productName.hashCode() : 0);
		result = 31 * result + (deviceName != null ? deviceName.hashCode() : 0);
		result = 31 * result + (connectionStatus != null ? connectionStatus.hashCode() : 0);
		result = 31 * result + (multimeterStatus != null ? multimeterStatus.hashCode() : 0);
		result = 31 * result + (multimeterValidity != null ? multimeterValidity.hashCode() : 0);
		result = 31 * result + multimeterPower;
		result = 31 * result + multimeterEnergy;
		result = 31 * result + (temperatureStatus != null ? temperatureStatus.hashCode() : 0);
		result = 31 * result + (temperatureValidity != null ? temperatureValidity.hashCode() : 0);
		result = 31 * result + temperatureCelcius;
		result = 31 * result + temperatureOffset;
		result = 31 * result + (switchStatus != null ? switchStatus.hashCode() : 0);
		result = 31 * result + (switchValidity != null ? switchValidity.hashCode() : 0);
		result = 31 * result + (switchState != null ? switchState.hashCode() : 0);
		result = 31 * result + (switchTimeMode != null ? switchTimeMode.hashCode() : 0);
		result = 31 * result + (switchKeyLock ? 1 : 0);
		result = 31 * result + (thermostatStatus != null ? thermostatStatus.hashCode() : 0);
		result = 31 * result + (thermostatValidity != null ? thermostatValidity.hashCode() : 0);
		result = 31 * result + thermostatCurrentTemperature;
		result = 31 * result + (thermostatTargetValveStatus != null ? thermostatTargetValveStatus.hashCode() : 0);
		result = 31 * result + thermostatTargetTemperature;
		result = 31 * result + (thermostatReduceValveStatus != null ? thermostatReduceValveStatus.hashCode() : 0);
		result = 31 * result + thermostatReduceTemperature;
		result = 31 * result + (thermostatComfortValveStatus != null ? thermostatComfortValveStatus.hashCode() : 0);
		result = 31 * result + thermostatComfortTemperature;
		return result;
	}
}
