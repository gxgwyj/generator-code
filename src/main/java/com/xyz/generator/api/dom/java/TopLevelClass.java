package com.xyz.generator.api.dom.java;

import com.xyz.generator.api.dom.OutputUtilities;
import com.xyz.generator.internal.util.StringUtility;

import java.util.*;

/**
 * 类: TopLevelClass <br>
 * 描述: <br>
 * 作者: gaoxugang <br>
 * 时间: 2018年02月02日 14:10
 */
public class TopLevelClass extends InnerClass implements CompilationUnit {

    private Set<FullyQualifiedJavaType> importedTypes;

    private Set<String> staticImports;

    private List<String> fileCommentLines;


    public TopLevelClass(FullyQualifiedJavaType type) {
        super(type);
        importedTypes = new TreeSet<FullyQualifiedJavaType>();
        fileCommentLines = new ArrayList<String>();
        staticImports = new TreeSet<String>();
    }

    public TopLevelClass(String typeName) {
        this(new FullyQualifiedJavaType(typeName));
    }

    public Set<FullyQualifiedJavaType> getImportedTypes() {
        return Collections.unmodifiableSet(importedTypes);
    }

    public void addImportedType(String importedType) {
        addImportedType(new FullyQualifiedJavaType(importedType));
    }

    public void addImportedType(FullyQualifiedJavaType importedType) {
        if (importedType != null
                && importedType.isExplicitlyImported()
                && !importedType.getPackageName().equals(
                getType().getPackageName())) {
            importedTypes.add(importedType);
        }
    }

    public String getFormattedContent() {
        StringBuilder sb = new StringBuilder();

        for (String fileCommentLine : fileCommentLines) {
            sb.append(fileCommentLine);
            OutputUtilities.newLine(sb);
        }

        if (StringUtility.stringHasValue(getType().getPackageName())) {
            sb.append("package "); //$NON-NLS-1$
            sb.append(getType().getPackageName());
            sb.append(';');
            OutputUtilities.newLine(sb);
            OutputUtilities.newLine(sb);
        }

        for (String staticImport : staticImports) {
            sb.append("import static "); //$NON-NLS-1$
            sb.append(staticImport);
            sb.append(';');
            OutputUtilities.newLine(sb);
        }

        if (staticImports.size() > 0) {
            OutputUtilities.newLine(sb);
        }

        Set<String> importStrings = StringUtility.calculateImports(importedTypes);
        for (String importString : importStrings) {
            sb.append(importString);
            OutputUtilities.newLine(sb);
        }

        if (importStrings.size() > 0) {
            OutputUtilities.newLine(sb);
        }

        sb.append(super.getFormattedContent(0));

        return sb.toString();
    }

    public boolean isJavaInterface() {
        return false;
    }

    public boolean isJavaEnumeration() {
        return false;
    }

    public void addFileCommentLine(String commentLine) {
        fileCommentLines.add(commentLine);
    }

    public List<String> getFileCommentLines() {
        return fileCommentLines;
    }

    public void addImportedTypes(Set<FullyQualifiedJavaType> importedTypes) {
        this.importedTypes.addAll(importedTypes);
    }

    public Set<String> getStaticImports() {
        return staticImports;
    }

    public void addStaticImport(String staticImport) {
        staticImports.add(staticImport);
    }

    /* (non-Javadoc)
     * @see com.xyz.generator.api.dom.java.CompilationUnit#addStaticImports(java.util.Set)
     */
    public void addStaticImports(Set<String> staticImports) {
        this.staticImports.addAll(staticImports);
    }




}
