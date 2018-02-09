/**
 *    Copyright 2006-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.xyz.generator.config;

import com.xyz.generator.internal.util.messages.Messages;

/**
 * Typesafe enum of different model types
 * 
 * @author Jeff Butler
 */
public enum ModelType {
    HIERARCHICAL("hierarchical"), //$NON-NLS-1$ 分层
    FLAT("flat"), //$NON-NLS-1$ 平级
    CONDITIONAL("conditional"); //$NON-NLS-1$  条件

    private final String modelType;

    /**
     * 
     */
    private ModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getModelType() {
        return modelType;
    }

    public static ModelType getModelType(String type) {
        if (HIERARCHICAL.getModelType().equalsIgnoreCase(type)) {
            return HIERARCHICAL;
        } else if (FLAT.getModelType().equalsIgnoreCase(type)) {
            return FLAT;
        } else if (CONDITIONAL.getModelType().equalsIgnoreCase(type)) {
            return CONDITIONAL;
        } else {
            throw new RuntimeException(Messages.getString(
                    "RuntimeError.13", type)); //$NON-NLS-1$
        }
    }
}
