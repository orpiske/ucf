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

/**
 * Version comparator interface
 * @author Otavio R. Piske <angusyoung@gmail.com>
 *
 */
public interface VersionComparator {
	
	/**
	 * Compares two versions to check it the last (v2) is an upgrade to the 
	 * first (v1).
	 * 
	 * @param v1 The net.orpiske.ucf.contrib.version to check against (base net.orpiske.ucf.contrib.version)
	 * @param v2 The net.orpiske.ucf.contrib.version to check
	 * @return ComparisonStrategy.EQUALS if equals, ComparisonStrategy.LESS_THAN
	 * if v2 is less than the base (v2) net.orpiske.ucf.contrib.version or ComparisonStrategy.GREATER_THAN
	 * otherwise.
	 */
	int compare(final String v1, final String v2);
}
