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
 * Implements the default net.orpiske.ucf.contrib.version.slot comparator. That means that it considers any
 * greater, no matter if in the same major or minor, as an upgrade. In other 
 * words: there's no net.orpiske.ucf.contrib.version.slot.
 * 
 * @author Otavio R. Piske <angusyoung@gmail.com>
 *
 */
public class DefaultComparator implements SlotComparator {

	/*
	 * (non-Javadoc)
	 * @see net.orpiske.ssps.common.net.orpiske.ucf.contrib.version.net.orpiske.ucf.contrib.version.slot.SlotComparator#fits(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean fits(String v1, String v2) {
		return true;
	}

}
