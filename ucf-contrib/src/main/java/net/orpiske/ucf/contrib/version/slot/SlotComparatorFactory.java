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
 * A factory for net.orpiske.ucf.contrib.version.slot comparators
 * 
 * @author Otavio R. Piske <angusyoung@gmail.com>
 */
public final class SlotComparatorFactory {
	/**
	 * This is the default net.orpiske.ucf.contrib.version.slot: it declares a single net.orpiske.ucf.contrib.version.slot for all the versions of a
	 * package
	 */
	public static final String DEFAULT_SLOT = "*";
	
	/**
	 * The major net.orpiske.ucf.contrib.version.slot considers that versions in different major releases are different.
	 * For instance, in this net.orpiske.ucf.contrib.version.slot definition 1.2.0 is different than 2.3.0. On the other
	 * hand, 1.2.0 and 1.3.0 are on the same net.orpiske.ucf.contrib.version.slot.
	 */
	public static final String MAJOR = "n.*";
	
	
	/**
	 * The major/minor net.orpiske.ucf.contrib.version.slot considers that versions in different major and minor releases
	 * are different. 
	 * For instance, in this net.orpiske.ucf.contrib.version.slot definition 1.2.0 is different than 1.3.0 or 2.3.0. On
	 * the other hand, 1.2.0 and 1.2.1 are on the same net.orpiske.ucf.contrib.version.slot.
	 */
	public static final String MAJOR_MINOR = "n.n.*";
	
	/**
	 * Constructs a new net.orpiske.ucf.contrib.version.slot comparator based on an input mask
	 * @param mask the input mask representing the net.orpiske.ucf.contrib.version.slot
	 * @return A net.orpiske.ucf.contrib.version.slot comparator for that mask or the default comparator if not valid
	 */
	public static SlotComparator create(final String mask) {
		if (mask.equals(MAJOR)) {
			return new MajorComparator();
		}
		
		if (mask.equals(MAJOR_MINOR)) {
			return new MajorMinorComparator();
		}
		
		return new DefaultComparator();
	}

}
