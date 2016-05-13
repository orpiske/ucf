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
package net.orpiske.ucf.contrib.scm.exceptions;

/**
 * SCM update exceptions are thrown during a SCM update operation 
 * @author Otavio R. Piske <angusyoung@gmail.com>
 *
 */
@SuppressWarnings("serial")
public class ScmUpdateException extends Exception {

	/**
	 * Constructor
	 * @param message Exception message
	 * @param t Exception cause
	 */
	public ScmUpdateException(String message, Throwable t) {
		super(message, t);
	}

	/**
	 * Constructor
	 * @param message Exception message
	 */
	public ScmUpdateException(String message) {
		super(message);
	}

}
