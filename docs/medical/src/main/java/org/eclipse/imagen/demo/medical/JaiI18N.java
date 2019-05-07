/*
 * Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.eclipse.imagen.demo.medical;


import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/** The class for inetrnationalization. */


class JaiI18N {
    static String packageName = "";
    private static ResourceBundle   resources =
	ResourceBundle.getBundle("MedicalApp", Locale.getDefault());

    /** Return a property defined in the property file. */


    public static String getString(String key) {
        String s = resources.getString(key);

	// If the string is quoted, remove the quotation mark.
	// Sometimes, put the quotation marks are used to reserve more space
	// by adding white charaters.
	if (s.startsWith("\""))
	    s = s.substring(1, s.length() - 1);
	return s;
    }
}
