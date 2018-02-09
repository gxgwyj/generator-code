package com.xyz.generator.config;

/**
 * 类: TypedPropertyHolder <br>
 * 描述: ${DESCRIPTION}<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年02月05日 11:21
 */
public class TypedPropertyHolder extends PropertyHolder{

    private String configurationType;

    public TypedPropertyHolder() {
        super();
    }

    public String getConfigurationType() {
        return configurationType;
    }

    public void setConfigurationType(String configurationType) {
        if (!"DEFAULT".equalsIgnoreCase(configurationType)) { //$NON-NLS-1$
            this.configurationType = configurationType;
        }
    }
}
