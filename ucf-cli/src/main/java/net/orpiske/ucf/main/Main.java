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
package net.orpiske.ucf.main;

import net.orpiske.ucf.utils.Constants;

import static java.util.Arrays.copyOfRange;

public class Main {
    private static void help(int code) {
        System.out.println("Usage: ucf <action>\n");

        System.out.println("Actions:");
        System.out.println("   configure");
        System.out.println("----------");
        System.out.println("   help");
        System.out.println("   --version");

        System.exit(code);
    }

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
        String logLevel = System.getProperty("net.orpiske.ucf.log.level");

        configureOutput(logLevel);

        if (args.length == 0) {
            System.err.println("The test case is missing!");
            System.exit(1);
        }
        else {
            System.out.println("Running " + args[0]);
        }


        int ret = 0;

        if (args.length == 0) {
            help(1);
        }

        String first = args[0];
        String[] newArgs = copyOfRange(args, 1, args.length);

        if (first.equals("help")) {
            help(1);
        }

        if (first.equals("configure")) {
            // Installer installer = new Installer(newArgs);

            // ret = installer.run();
            System.exit(ret);
        }

        if (first.equals("--version")) {
            System.out.println("Universal Configuration Framework: ucf "
                    + Constants.VERSION);

            System.exit(ret);
        }

        help(1);
    }
}
