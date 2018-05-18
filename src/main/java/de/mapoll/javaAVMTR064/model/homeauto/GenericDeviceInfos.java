/*
 * *********************************************************************************************************************
 *
 * javaAVMTR064 - open source Java TR-064 API
 *===========================================
 *
 * Copyright 2018 Iridias (https://github.com/Iridias)
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 **********************************************************************************************************************
 */
package de.mapoll.javaAVMTR064.model.homeauto;

public class GenericDeviceInfos extends SpecificDeviceInfos {
	
	private final int index;
	
	private GenericDeviceInfos(final Builder b) {
		super(b);
		this.index = b.index;
	}
	
	public static class Builder extends SpecificDeviceInfos.Builder {
		private int index;
		
		public Builder setIndex(final int index) {
			this.index = index;
			return this;
		}
		
		public GenericDeviceInfos build() {
			return new GenericDeviceInfos(this);
		}
	}
	
	public int getIndex() {
		return index;
	}
	
	@Override
	public String toString() {
		return "GenericDeviceInfos{" +
				"index=" + index +
				"} " + super.toString();
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		
		GenericDeviceInfos that = (GenericDeviceInfos) o;
		
		return index == that.index;
	}
	
	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + index;
		return result;
	}
}
