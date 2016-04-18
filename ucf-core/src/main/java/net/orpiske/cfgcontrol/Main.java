/**
 * Copyright 2014 Otavio Rodolfo Piske
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.orpiske.cfgcontrol;

public class Main {
    private static void configureOutput(final String level) {
        if (level == null || (level.length() == 0)) {
            LogConfigurator.silent();

            return;
        }

        if (level.equals("verbose")) {
            LogConfigurator.verbose();
        }
        else {
            if (level.equals("debug")) {
                LogConfigurator.debug();
            }
            else {
                if (level.equals("trace")) {
                    LogConfigurator.trace();
                }
                else {
                    LogConfigurator.silent();
                }
            }
        }
    }


    public static void main(String[] args) {
        String logLevel = System.getProperty("net.orpiske.jms.log.level");

        configureOutput(logLevel);

        if (args.length == 0) {
            System.err.println("The test case is missing!");
            System.exit(1);
        }
        else {
            System.out.println("Running " + args[0]);
        }
    }
}
