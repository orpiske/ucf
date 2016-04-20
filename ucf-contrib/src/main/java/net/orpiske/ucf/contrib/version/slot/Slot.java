/**
   Copyright 2012 Otavio Rodolfo Piske

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

import net.orpiske.ucf.contrib.version.Version;

/**
 * Abstracts the net.orpiske.ucf.contrib.version.slot information
 * @author Otavio R. Piske <angusyoung@gmail.com>
 *
 */
public class Slot {
	private SlotComparator slotComparator;
	
	/**
	 * Constructor
	 */
	public Slot() {
		slotComparator = SlotComparatorFactory.create(SlotComparatorFactory.DEFAULT_SLOT);
	}
	
	
	/**
	 * Constructor
	 * @param slotComparator The net.orpiske.ucf.contrib.version.slot comparator
	 */
	public Slot(SlotComparator slotComparator) {
		this.slotComparator = slotComparator;
	}
	
	/**
	 * Constructor
	 * @param slotMask The net.orpiske.ucf.contrib.version.slot mask string
	 */
	public Slot(final String slotMask) {
		slotComparator = SlotComparatorFactory.create(slotMask);
	}
	
	
	/**
	 * Whether one net.orpiske.ucf.contrib.version share the same net.orpiske.ucf.contrib.version.slot as the other
	 * @param v1 The net.orpiske.ucf.contrib.version to check against
	 * @param v2 The net.orpiske.ucf.contrib.version to check
	 * @return True if v2 fits/shares the same net.orpiske.ucf.contrib.version.slot as v1.
	 */
	public boolean fits(Version v1, Version v2) {
		return slotComparator.fits(v1.toString(), v2.toString());
	}

}
