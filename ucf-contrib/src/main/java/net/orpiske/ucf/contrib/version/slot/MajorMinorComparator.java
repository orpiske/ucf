/**
   Copyright 2013 Otavio Rodolfo Piske

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package net.orpiske.ucf.contrib.version.slot;

/**
 * This class implements the comparator for MAJOR.MINOR net.orpiske.ucf.contrib.version ranges. Hence
 * given two versions "1.1.0" and "1.2.0", each would be on their own net.orpiske.ucf.contrib.version.slot.
 * 
 * @author Otavio R. Piske <angusyoung@gmail.com>
 *
 */
public class MajorMinorComparator implements SlotComparator {

	private boolean compare(String[] v1, String[] v2) {
		
		if (v1[0].equals(v2[0])) {
			if (v1[1].equals(v2[1])) {
				return true;
			}
		}
		
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see net.orpiske.ssps.common.net.orpiske.ucf.contrib.version.net.orpiske.ucf.contrib.version.slot.SlotComparator#fits(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean fits(String v1, String v2) {
		String[] parts1;
		String[] parts2;
		
		if (v1 == null || v2 == null) {
			return false;
		}
		
		
		parts1 = v1.split("\\.");
		parts2 = v2.split("\\.");
		
		return compare(parts1, parts2);
	}

}
