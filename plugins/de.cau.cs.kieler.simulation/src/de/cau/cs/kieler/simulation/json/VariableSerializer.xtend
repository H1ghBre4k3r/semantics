/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 * 
 * Copyright 2017 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */
package de.cau.cs.kieler.simulation.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import de.cau.cs.kieler.simulation.core.Variable
import java.lang.reflect.Type
import com.google.gson.JsonPrimitive
import de.cau.cs.kieler.simulation.core.NDimensionalArray
import de.cau.cs.kieler.prom.templates.VariableInterfaceType

/**
 * (De-)Serializer for variables in the data pool
 * 
 * @author aas
 *
 */
class VariableSerializer implements JsonSerializer<Variable> , JsonDeserializer<Variable> {
    /**
     * {@inheritDoc}
     */
    override serialize(Variable src, Type typeOfSrc, JsonSerializationContext context) {
        val object = new JsonObject()
        object.add("value", context.serialize(src.value))
//        object.addProperty("name", src.name)
//        object.add("type", context.serialize(src.type))
//        object.addProperty("interface", VariableInterfaceType.getBitmask(src.interfaceTypes))
        return object
    }
    
    /**
     * {@inheritDoc}
     */
    override deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        val object = json.asJsonObject
        
        val name = object.get("name")?.getAsString()
        val jsonValue = object.get("value")
        val value = context.deserialize(jsonValue, jsonValue.class)
        val interface = object.get("interface")
        
        val variable = new Variable
        if(name != null) {
            variable.name = name
        }
        if(value instanceof JsonPrimitive) {
            if(value != null) {
                val primitive = JsonManager.jsonAsObject(value)
                variable.value = primitive
                // Use Integer instead Double if possible
                if(primitive instanceof Double) {
                    if(primitive == primitive.intValue) {
                        variable.value = primitive.intValue
                    }
                }
            }
        } else if (value instanceof JsonObject){
            val array = context.deserialize(value, typeof(NDimensionalArray))
            variable.value = array
        }
        if(interface != null) {
            val interfaceTypes = VariableInterfaceType.getInterfaceTypes(interface.asInt)
            variable.interfaceTypes = interfaceTypes
        }
        return variable
    }
}