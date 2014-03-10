package com.intelligrape.direcPay.common

import com.toml.dp.util.AES128Bit
import org.codehaus.groovy.grails.commons.ApplicationHolder

class DirecPayUtility {

    public static String getApplicationName() {
        getGrailsApplication().metadata.'app.name'
    }

    public static def getGrailsApplication() {
        return ApplicationHolder.application
    }

    public static def getConfig(String configProperty) {
        def cpMap = grailsApplication.config.clone()
        configProperty = "grails.plugins.${configProperty}"
        List<String> configPropertyKeys = configProperty.split(/\./)
        def configValue = null
        for (String key in configPropertyKeys) {
            if (((cpMap instanceof ConfigObject) || (cpMap instanceof Map)) && cpMap.containsKey(key)) {
                configValue = cpMap[key]
                cpMap = cpMap[key]
            } else {
                return null
            }
        }
        return configValue
    }

    static String SECRET_KEY = getConfig("direcPay.encryption.secretKey")

    public static String encrypt(String valueToEnc) {
        String encrypt = AES128Bit.encrypt(valueToEnc, SECRET_KEY);
        return encrypt
    }

    public static String[] splitString(String responseString, String delimiter = '|') {
        String[] array = null
        if (responseString && delimiter) {
            array = responseString.tokenize(delimiter);
        }
        return array
    }

}
