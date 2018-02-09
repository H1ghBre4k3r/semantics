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

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializer
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import de.cau.cs.kieler.simulation.core.NDimensionalArray
import java.lang.reflect.Type
import com.google.gson.JsonElement
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonParseException
import java.util.List

/**
 * (De-)Serializer for NDimensionalArrays in the simulation.
 * 
 * @author aas
 *
 */
class NDimensionalArraySerializer implements JsonSerializer<NDimensionalArray>,
                                             JsonDeserializer<NDimensionalArray> {
    /**
     * {@inheritDoc}
     */
    override serialize(NDimensionalArray src, Type typeOfSrc, JsonSerializationContext context) {
        val object = new JsonObject()
        // Add indices
        val indicesArray = new JsonArray()
        for(i : src.cardinalities) {
            indicesArray.add(i)    
        }
        object.add("indices", indicesArray)
        // Add values
        val valuesArray = new JsonArray()
        for(e : src.elements) {
            val v = e.value
            if(v instanceof Number) {
                valuesArray.add(v)            
            } else if(v instanceof Boolean) {
                valuesArray.add(v)            
            } if(v instanceof String) {
                valuesArray.add(v)            
            }
        }
        object.add("values", valuesArray)
        return object
    }
    
    /**
     * {@inheritDoc}
     */
    override deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            val object = json.asJsonObject
            
            val jsonIndices = object.get("indices").asJsonArray
            val jsonValues = object.get("values").asJsonArray
    
            val indices = newArrayList()
            for(jsonIndex : jsonIndices) {
                val i = context.deserialize(jsonIndex, typeof(Integer))
                indices.add(i)
            }
            
            val List<Object> values = newArrayList()
            values.addAll(jsonValues)
            val array = new NDimensionalArray(values, indices)
            return array
        } catch (Exception e) {
            throw new Exception("Cannot deserialize NDimensionalArray", e)
        }
    }
    
    /**
     * Adds the values of a (possibly multidimensional) json array to the target list.
     */
    private def void addAll(List<Object> targetList, JsonArray jsonArray) {
        for(jsonValue : jsonArray) {
            if(jsonValue instanceof JsonArray) {
                // The values in this array are other arrays.
                targetList.addAll(jsonValue)
            } else {
                // The values in this array seem to be primitives.
                val value = JsonManager.jsonAsObject(jsonValue)
                if(value !== null) {
                    if(value instanceof Double) {
                        val intValue = value.intValue 
                        if(value == intValue) {
                            targetList.add(intValue)
                        } else {
                            targetList.add(value)
                        }
                    } else {
                        targetList.add(value)    
                    }    
                }
            }
        }
    }
}
