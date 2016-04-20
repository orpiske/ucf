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
package net.orpiske.ucf.contrib.version;

import net.orpiske.ucf.contrib.version.slot.SlotComparator;

/**
 * Implements a net.orpiske.ucf.contrib.version comparison strategy which allows us to control how to
 * determine if one net.orpiske.ucf.contrib.version should be considered an upgrade to another or not.
 * 
 * @author Otavio R. Piske <angusyoung@gmail.com>
 */
public class ComparisonStrategy {
	/**
	 * Checked net.orpiske.ucf.contrib.version is less than the base net.orpiske.ucf.contrib.version
	 */
	public static final int LESS_THAN = -1;
	
	/**
	 * The versions are the same
	 */
	public static final int EQUALS = 0;
	
	/**
	 * The checked net.orpiske.ucf.contrib.version is greater than the base net.orpiske.ucf.contrib.version
	 */
	public static final int GREATER_THAN = 1;
	
	private SlotComparator slotComparator;
	private VersionComparator versionComparator;
	
	/**
	 * Constructor
	 * @param slotComparator The net.orpiske.ucf.contrib.version.slot comparator
	 * @param versionComparator The net.orpiske.ucf.contrib.version comparator
	 */
	public ComparisonStrategy(final SlotComparator slotComparator, 
			final VersionComparator versionComparator) 
	{
		this.slotComparator = slotComparator;
		this.versionComparator = versionComparator;
	}
 	
	
	/**
	 * Compares two versions to check it the last (v2) is an upgrade to the 
	 * first (v1). With respect to the appropriate net.orpiske.ucf.contrib.version net.orpiske.ucf.contrib.version.slot.
	 * 
	 * @param v1 The net.orpiske.ucf.contrib.version to check against (base net.orpiske.ucf.contrib.version)
	 * @param v2 The net.orpiske.ucf.contrib.version to check
	 * @return ComparisonStrategy.EQUALS if equals, ComparisonStrategy.LESS_THAN
	 * if v2 is less than the base (v2) net.orpiske.ucf.contrib.version or ComparisonStrategy.GREATER_THAN
	 * otherwise.
	 */
	@Deprecated
	public int compare(final String v1, final String v2) {
		if (slotComparator.fits(v1, v2)) {
			return versionComparator.compare(v1, v2);
		}
		
		return EQUALS;
	}
	
}
