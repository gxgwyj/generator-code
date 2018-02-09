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
package com.xyz.generator.api;

/**
 * 生成文件的抽象类
 */
public abstract class GeneratedFile {
    
    /** The target project. */
    protected String targetProject;

    /**
     * Instantiates a new generated file.
     *
     * @param targetProject
     *            the target project
     */
    public GeneratedFile(String targetProject) {
        super();
        this.targetProject = targetProject;
    }

    public abstract String getFormattedContent();


    public abstract String getFileName();


    public String getTargetProject() {
        return targetProject;
    }


    public abstract String getTargetPackage();


    @Override
    public String toString() {
        return getFormattedContent();
    }

    public abstract boolean isMergeable();
}
