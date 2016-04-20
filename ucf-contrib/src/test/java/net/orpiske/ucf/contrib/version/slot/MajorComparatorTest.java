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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MajorComparatorTest {
	private SlotComparator comparator;
	
	@Before
	public void setup() {
		comparator = new MajorComparator();
	}

	@Test
	public void testFitsMajor() {
		boolean ret = comparator.fits("1.0.0", "1.1.0");
		assertTrue("Version 1.0.0 and 1.1.0 should be in the same slot", ret);
	}
	
	@Test
	public void testFitsMajorMinor() {
		boolean ret = comparator.fits("1.1.0", "1.1.1");
		assertTrue("Version 1.1.0 and 1.1.1 should be in the same slot", ret);
	}
	
	
	@Test
	public void testNotFits() {
		boolean ret = comparator.fits("1.0.0", "2.1.0");
		assertFalse("Version 1.0.0 and 2.1.0 should not be in the same slot",
				ret);
	}
	
	
	@Test
	public void testNotFitsGreater() {
		boolean ret = comparator.fits("2.0.0", "1.1.0");
		assertFalse("Version 2.0.0 and 1.1.0 should not be in the same slot", 
				ret);
	}

}
