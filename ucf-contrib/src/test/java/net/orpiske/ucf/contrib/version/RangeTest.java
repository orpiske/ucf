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

import org.junit.Test;

import static org.junit.Assert.*;

public class RangeTest {

	@Test
	public void testMinimumBoth() {
		String minimum = Range.minimum("(2.2.1,2.2.999)");
		
		assertEquals("Incorrect minimum version", "2.2.1", minimum);
	}
	
	
	@Test
	public void testMinimumOnly() {
		String minimum = Range.minimum("(2.2.1,)");
		
		assertEquals("Incorrect minimum version", "2.2.1", minimum);
	}
	
	
	@Test
	public void testMinimumMaximumOnly() {
		String minimum = Range.minimum("(,2.2.999)");
		
		assertNull("Incorrect minimum version", minimum);
	}
	
	
	@Test
	public void testMaximumBoth() {
		String maximum = Range.maximum("(2.2.1,2.2.999)");
		
		assertEquals("Incorrect maximum version", "2.2.999", maximum);
	}
	
	
	@Test
	public void testMaximumOnly() {
		String maximum = Range.maximum("(,2.2.999)");
		
		assertEquals("Incorrect maximum version", "2.2.999", maximum);
	}
	
	
	@Test
	public void testMaximumminimumOnly() {
		String maximum = Range.maximum("(2.2.1,)");
		
		assertNull("Incorrect maximum version", maximum);
	}
	
	
	@Test
	public void testRangeBoth() {
		Range range = Range.toRange("(2.2.1,2.2.999)"); 
		
		Version minimum = range.getMinimumVersion();
		assertEquals(Version.toVersion("2.2.1"), minimum);
		
		Version maximum = range.getMaximumVersion();
		assertEquals(Version.toVersion("2.2.999"), maximum);
	}
	
	
	@Test
	public void testRangeMinimumOnly() {
		Range range = Range.toRange("(2.2.1,)"); 
		
		Version minimum = range.getMinimumVersion();
		assertEquals(Version.toVersion("2.2.1"), minimum);
		
		Version maximum = range.getMaximumVersion();
		assertNull("Maximum should be null", maximum);
	}
	
	
	@Test
	public void testRangeMaximumOnly() {
		Range range = Range.toRange("(,2.2.999)"); 
		
		Version minimum = range.getMinimumVersion();
		assertNull("Minimum should be null", minimum);
		
		Version maximum = range.getMaximumVersion();
		assertEquals(Version.toVersion("2.2.999"), maximum);
	}
	
	@Test 
	public void testInRange() {
		Version v1 = Version.toVersion("2.2.3");
		Range range = Range.toRange("(2.2.1, 2.2.999)");
		
		assertTrue("Version 2.2.3 should be in range", range.inRange(v1));
	}
	
	
	@Test 
	public void testInRangeGreater() {
		Version v1 = Version.toVersion("2.3.3");
		Range range = Range.toRange("(2.2.1, 2.2.999)");
		
		assertFalse("Version 2.3.3 should not be in range", range.inRange(v1));
	}
	
	@Test 
	public void testInRangeGreater2() {
		Version v1 = Version.toVersion("2.2.1000");
		Range range = Range.toRange("(2.2.1, 2.2.999)");
		
		assertFalse("Version 2.2.1000 should not be in range", range.inRange(v1));
	}
	
	
	@Test 
	public void testInRangeLesser() {
		Version v1 = Version.toVersion("2.1.1");
		Range range = Range.toRange("(2.2.1, 2.2.999)");
		
		assertFalse("Version 2.3.3 should not be in range", range.inRange(v1));
	}
	
	@Test 
	public void testInRangeEqualminimum() {
		Version v1 = Version.toVersion("2.2.1");
		Range range = Range.toRange("(2.2.1, 2.2.999)");
		
		assertTrue("Version 2.2.1 should be in range", range.inRange(v1));
	}
	
	
	@Test 
	public void testInRangeEqualMaximum() {
		Version v1 = Version.toVersion("2.2.999");
		Range range = Range.toRange("(2.2.1, 2.2.999)");
		
		assertTrue("Version 2.2.999 should be in range", range.inRange(v1));
	}
}
