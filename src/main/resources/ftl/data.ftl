<#list dataModel as field>
    /**
     * ${field.desc}
     */
     private ${field.type} ${field.name};

</#list>