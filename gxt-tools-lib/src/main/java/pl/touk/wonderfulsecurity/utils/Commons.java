/*
* Copyright (c) 2008 TouK.pl
* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package pl.touk.wonderfulsecurity.utils;

import java.util.Collection;

/**
 * Common code
 *
 * @author Lukasz Kucharski - lkc@touk.pl
 */
public final class Commons {
// -------------------------- STATIC METHODS --------------------------

    /**
     * Return null if collection is null or empty or otherwise return first element
     */
    public static <E> E nullIfEmptyCollection(Collection<E> coll) {
        if (coll == null)
            return null;

        if (coll.isEmpty())
            return null;

        return coll.iterator().next();
    }

    /**
     * Throws exception when any of arguments is null
     */
    public static void checkArgumentsNotNull(String errorMessage, Object... arguments) {
        for (Object o : arguments) {
            if (o == null) {
                throw new IllegalArgumentException(errorMessage);
            }
        }
    }
}
