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
package de.cau.cs.kieler.kivis.ui.animations

import de.cau.cs.kieler.kivis.kivis.Animation
import de.cau.cs.kieler.prom.build.ConfigurableAttribute
import de.cau.cs.kieler.simulation.core.DataPool
import org.apache.batik.dom.svg.SVGOMPathElement
import org.apache.batik.dom.svg.SVGOMPoint
import org.w3c.dom.Element
import org.w3c.dom.svg.SVGLocatable
import org.w3c.dom.svg.SVGPoint

/**
 * @author aas
 *
 */
class WalkPathAnimation extends AnimationHandler {
    public val pathName = new ConfigurableAttribute("path", null, true)
    public val pathStart = new ConfigurableAttribute("start", 0)
    public val pathEnd = new ConfigurableAttribute("end", 0)
    public val pathLength = new ConfigurableAttribute("length", 0)
    
    public val autoOrientation = new ConfigurableAttribute("autoOrientation", false)
    public val angleOffset = new ConfigurableAttribute("angleOffset", 0)
    public val angleValue = new ConfigurableAttribute("angle", 0)
    public val anchorX = new ConfigurableAttribute("anchorX", 0.5)
    public val anchorY = new ConfigurableAttribute("anchorY", 0.5)
    public val appendTransform = new ConfigurableAttribute("appendTransform", false)
    public val position = new ConfigurableAttribute("position")
    
    public val wrapAround = new ConfigurableAttribute("wrapAround", false)
    
    private var String initialTransform
    private var SVGPoint lastPoint
    private var double lastScaledPosition
    private var int travelDirection
    
    new() {
    }
    
    new(String svgElementId, Animation animation) {
        super(svgElementId, animation)
        initialize
    }
    
    override getName() {
        return "walkPath"
    }
    
    override doApply(DataPool pool, Element elem) {
        // Define pathEnd using the path length
        if(pathLength.floatValue > 0 && pathEnd.floatValue == 0) {
            pathEnd.value = pathStart.floatValue + pathLength.floatValue
        }
        val length = (pathEnd.floatValue - pathStart.floatValue)
        if(length <= 0) {
            throw new IllegalArgumentException("The length of the path in the "+name+" animation must be greater than 0.\n"
                                             + "(start is:"+pathStart.floatValue+", end is:"+pathEnd.floatValue+")"
            )
        }
        
        // Calculate animation
        var SVGOMPathElement path
        val pathElement = findElement(pathName.stringValue)
        if(pathElement == null) {
            throw new Exception("Path with id '" + pathName + "' was not found in the svg.")
        } else {
            try {
                path = pathElement as SVGOMPathElement
            } catch (Exception e) {
                throw new Exception("Element with id '" + pathName + "' is not a valid path in the svg.")
            }
        }
        // Compute position on path
        val totalPathLength = path.getTotalLength
        val value = if(position.value == null)
                        variableValue.doubleValue
                    else
                        position.floatValue.doubleValue
                        
        var wrappedValue = value
        if(wrapAround.value != null && wrapAround.boolValue) {
            // If value is below the path, bring it back to the range
            // It holds length >= 0, because we check it above and throw an exception if not.
            // Thus the loops will terminate.
            while(wrappedValue < pathStart.floatValue) {
                wrappedValue += length
            }
            // If the value is above the path, bring it back to the range
            while(wrappedValue > pathEnd.floatValue) {
                wrappedValue -= length
            }    
        } else if(value < pathStart.floatValue || value > pathEnd.floatValue) {
            // The value is below or above the path range, so it will not be positioned on the path. 
            return;
        }
        val scaledValue = scale(wrappedValue, pathStart.floatValue, pathEnd.floatValue, 0, totalPathLength)
        var pointOnPath = path.getPointAtLength(scaledValue.floatValue)
        val pathPos = path.getAbsoluteTranslation
        val currentPoint = new SVGOMPoint(pointOnPath.x + pathPos.x, pointOnPath.y + pathPos.y)
        if(currentPoint == null) {
            throw new IllegalArgumentException(name+" animation could not determine position on path "+path)
        }
        // Determine if travel direction is in with or against path direction
        if(scaledValue > lastScaledPosition) {
            travelDirection = 1
        } else if(scaledValue < lastScaledPosition) {
            travelDirection = -1
        }
        // Compute angle
        if (autoOrientation.boolValue) {
            if(lastPoint != null && (lastPoint.x != currentPoint.x || lastPoint.y != currentPoint.y)){
                // Calculate rotation based on last position
                angleValue.value = computeAngle(lastPoint, currentPoint)
                if(angleOffset.floatValue != 0) {
                    angleValue.value = angleValue.floatValue angleOffset.floatValue
                }
            } else {
                // Calculate slope ("Steigung" bzw. "Ableitung") on path on current position
                val delta = 1
                var SVGPoint point1
                var SVGPoint point2
                if(scaledValue <= (totalPathLength - delta)) {
                    // Positive delta
                    point1 = pointOnPath
                    point2 = path.getPointAtLength(scaledValue.floatValue + delta)
                } else if(scaledValue >= delta) {
                    // Negative delta
                    point1 = path.getPointAtLength(scaledValue.floatValue - delta)
                    point2 = pointOnPath
                }
                if(point1 != null && point2 != null
                    && (point1.x != point2.x || point1.y != point2.y)) {
                    // Compute angle
                    angleValue.value = computeAngle(point1, point2)
                    // Add offset
                    if(angleOffset.floatValue != 0) {
                        angleValue.value = angleValue.floatValue + angleOffset.floatValue
                    }
                    // Turn around when moving "backwards"
                    if(travelDirection == -1) {
                        angleValue.value = angleValue.floatValue + 180 * travelDirection
                    }
                }
            }
        }
        // Remember positions of this iteration
        lastPoint = currentPoint
        lastScaledPosition = scaledValue
        
        if(elem instanceof SVGLocatable) {
            if(appendTransform.boolValue) {
                if(initialTransform == null) {
                    initialTransform = elem.getAttribute("transform")
                }
            } else {
                initialTransform = ""    
            }
            // Position and size of the element
            val locatable = elem as SVGLocatable
            val box = locatable.BBox
            val parentTranslation = elem.parentNode.getAbsoluteTranslation
            val pos = new SVGOMPoint(box.x + parentTranslation.x, box.y + parentTranslation.y)
            val width = box.width
            val height = box.height
            // Anchor position in pixels
            var double pivotX = anchorX.floatValue * width
            var double pivotY = anchorY.floatValue * height
            
            val xValue = currentPoint.x - pos.x - pivotX
            val yValue = currentPoint.y - pos.y - pivotY
            var translate = "translate(" + xValue + "," + yValue + ")"
            if (autoOrientation.boolValue) {
                translate += "rotate(" + angleValue.floatValue + "," + (pos.x + pivotX) + "," + (pos.y + pivotY) + ")";
            }
            val newTransform = initialTransform + translate
            elem.setAttribute("transform", newTransform)
        } else {
            throw new Exception("The element '"+svgElementId+"' is not an SVGLocatable.")
        }
    }
    
    private def double computeAngle(SVGPoint p1, SVGPoint p2) {
        val double RADTODEG = 180.0 / Math.PI;
        var double deltaX
        var double deltaY
        var double alpha
        deltaX = p2.getX() - p1.getX();// Ankathete
        deltaY = p2.getY() - p1.getY();// Gegenkathete
        // Edge cases
        if(deltaX == 0 && deltaY > 0) {
            // 90 degrees up
            return 90;
        } else if(deltaX == 0 && deltaY < 0) {
            // 90 degrees down
            return 270;
        } if(deltaX == 0 && deltaY == 0) {
             throw new IllegalArgumentException("Delta of points cannot be 0. Can't compute angle in "+name+" animation.")
        }
        alpha = Math.atan(deltaY / deltaX) * RADTODEG;

        if (deltaX > 0 && deltaY >= 0) {
            // values from 0..90 nothing to do change here
        } else if (deltaX < 0 && deltaY >= 0) {
            // mapping from -90..0 to 90..180
            alpha += 180
        } else if (deltaX < 0 && deltaY <= 0) {
            // mapping from 0..90 to 180..270
            alpha += 180
        } else if (deltaX > 0 && deltaY <= 0) {
            // mapping from -90..0 to 270..360
            alpha += 360
        }
        return alpha;
    }
    
    private def void print(Element elem, String attr) {
        println(attr+"="+elem.getAttribute(attr))
        if(elem.parentNode != null && elem.parentNode instanceof Element) {
            print(elem.parentNode as Element, attr)
        }
    }
}