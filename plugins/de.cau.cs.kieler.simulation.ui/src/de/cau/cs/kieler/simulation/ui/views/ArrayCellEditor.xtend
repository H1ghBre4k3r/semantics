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
package de.cau.cs.kieler.simulation.ui.views

import de.cau.cs.kieler.simulation.core.NDimensionalArray
import de.cau.cs.kieler.simulation.core.NDimensionalArrayElement
import de.cau.cs.kieler.simulation.core.Variable
import java.util.Collections
import java.util.List
import org.eclipse.core.runtime.Assert
import org.eclipse.jface.viewers.ArrayContentProvider
import org.eclipse.jface.viewers.CellEditor
import org.eclipse.jface.viewers.ColumnViewerEditor
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter
import org.eclipse.jface.viewers.TableViewer
import org.eclipse.jface.viewers.TableViewerColumn
import org.eclipse.jface.viewers.TableViewerEditor
import org.eclipse.jface.viewers.TableViewerFocusCellManager
import org.eclipse.swt.SWT
import org.eclipse.swt.events.FocusAdapter
import org.eclipse.swt.events.FocusEvent
import org.eclipse.swt.events.KeyAdapter
import org.eclipse.swt.events.KeyEvent
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Table
import de.cau.cs.kieler.prom.ui.UIUtil

/**
 * Cell editor for NDimensionalArrays
 * 
 * @author aas
 *
 */
class ArrayCellEditor extends CellEditor {
    
    /**
     * The table in which the elements of the array are displayed.
     */
    var TableViewer viewer;
    
    /**
     * The index column of the table
     */
    var TableViewerColumn indexColumn
    /**
     * The value column of the table
     */
    var TableViewerColumn valueColumn
    /**
     * The user value column of the table
     */
    var TableViewerColumn userValueColumn
    /**
     * The history column of the table
     */
    var TableViewerColumn historyColumn
    
    /**
     * The array that is beeing edited
     */
    var NDimensionalArray array
    
    /**
     * The parent composite of this cell editor
     */
    var Composite parent
    
    /**
     * The alignment for the table in the parent.
     * This is choosen depending on the position of the variable that holds this array.
     */
    var int verticalAlignment
    
    /**
     * The variable that holds the array to be edited
     */
    var Variable variable
    
    /**
     * Constructor
     *
     * @param parent The parent control
     * @param style The style bits
     */
    new(Composite parent, int style) {
        super(parent, style);
    }
    
    /**
     * Constructor
     *
     * @param parent The parent control
     */
    new(Composite parent) {
        this(parent, SWT.NONE);
    }
    
    /**
     * {@inheritDoc}
     */
    override protected createControl(Composite parent) {
        this.parent = parent.parent
        viewer = createTable(parent)
        viewer.control.addFocusListener(new FocusAdapter() {
            override focusLost(FocusEvent e) {
                ArrayCellEditor.this.focusLost();
            }
        });
        
        return viewer.control
    }
    
    /**
     * {@inheritDoc}
     */
    override getLayoutData() {
        // Try to fit inside the parent control to have everything visible.
        val result = new LayoutData();
        if (control != null) {
            val preferredSize = control.computeSize(SWT.DEFAULT, SWT.DEFAULT, true)
            result.minimumWidth = preferredSize.x;
            val parentHeight = (parent.size.y*0.6f) as int
            result.minimumHeight = Math.min(preferredSize.y, parentHeight);
            
            result.verticalAlignment = verticalAlignment
        }
        return result
    }
    
    /**
     * {@inheritDoc}
     */
    override protected doGetValue() {
        return array
    }
    
    /**
     * {@inheritDoc}
     */
    override protected doSetFocus() {
        viewer.control.setFocus
    }
    
    /**
     * {@inheritDoc}
     */
    override protected focusLost() {
        // Do nothing
    }
    
    /**
     * This cell editor does not depend on external focus listeners, because to edit an array element,
     * the parent table (the data pool view) loses focus.
     * However, this editor should still be active to edit the array element.
     */
    override protected dependsOnExternalFocusListener() {
        return false
    }
    
    /**
     * {@inheritDoc}
     */
    override protected doSetValue(Object value) {
        Assert.isTrue(value != null && (value instanceof NDimensionalArray))
        array = (value as NDimensionalArray).clone
        // Find the variable in the parent, that is changed by this cell editor 
        val parentInput = control.getData("parentInput")
        val parentInputList = parentInput as List<Object>
        if(parentInputList != null) {
            variable = parentInputList.findFirst[it instanceof Variable
                                       && ((it as Variable).value == value
                                           || (it as Variable).userValue == value)] as Variable
        }
        
        // Find good alignment based on position of the variable to be changed in parent input
        // (Variable at beginning of list -> top alignment,
        // at end of list -> bottom alignment,
        // in middle of list -> center alignment)
        verticalAlignment = SWT.TOP
        if(variable != null && !parentInputList.isNullOrEmpty) {
            val pos = parentInputList.indexOf(variable)
            if(pos > 10) 
                verticalAlignment = SWT.BOTTOM
            else if(pos > 5)
                verticalAlignment = SWT.CENTER
        }

        // Set input        
        viewer.input = array.elements
    }
    
    /**
     * Creates the table in which the array elements are displayed and can be modified.
     * 
     * @param parent The parent composite
     * @return the table for the array elements
     */
    private def TableViewer createTable(Composite parent) {
        val style = getStyle().bitwiseOr(SWT.BORDER.bitwiseOr(SWT.FULL_SELECTION))
        val table = new Table(parent, style)
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        table.setSize(500,500)
        
        // Create viewer
        viewer = new TableViewer(table)
        // Support objects that are "equal" yet two different objects in memory.
        viewer.comparer = new IdentityComparer()
        
        // Create columns
        indexColumn = UIUtil.createTableColumn(viewer, "Index", 50, true)
        indexColumn.labelProvider = new DataPoolColumnLabelProvider() {
            override String getText(Object element) {
                if(element instanceof NDimensionalArrayElement) {
                    // Combine all indices into one string separated with comma
                    val indexCSV = element.index.map[it.toString].reduce[p1, p2| p1 +","+ p2] 
                    if(element.isDirty) {
                        return "*" + indexCSV    
                    } else {
                        return indexCSV
                    }
                }
            }
        };
        valueColumn = UIUtil.createTableColumn(viewer, "Current Value", 100, true)
        valueColumn.labelProvider = new DataPoolColumnLabelProvider() {
            override String getText(Object element) {
                if(element instanceof NDimensionalArrayElement) {
                    return element.value?.toString
                }
                return ""
            }
        };
        userValueColumn = UIUtil.createTableColumn(viewer, "User Value", 100, true)
        userValueColumn.labelProvider = new DataPoolColumnLabelProvider() {
            override String getText(Object element) {
                if(element instanceof NDimensionalArrayElement) {
                    if(element.isDirty) {
                        return element.userValue.toString
                    }
                }
                return ""
            }
        };
        historyColumn = UIUtil.createTableColumn(viewer, "History", 200, true)
        historyColumn.labelProvider = new HistoryColumnLabelProvider() {
            override String getText(Object element) {
                if(element instanceof NDimensionalArrayElement) {
                    val history = element.getArrayElementHistory
                    return createHistoryText(history)
                }
                return ""
            }
            
            override Image getToolTipImage(Object element) {
                if(img != null) {
                    img.dispose()
                    img = null
                }
                if(element instanceof NDimensionalArrayElement) {
                    val history = element.getArrayElementHistory
                    img = createHistoryGraph(history)
                }
                return img
            }
            
            private def List<Object> getArrayElementHistory(NDimensionalArrayElement element) {
                if(variable != null) {
                    val history = variable.history
                    if(!history.isNullOrEmpty) {
                        val index = element.index
                        val historyOfThisElement = history.map[(it.value as NDimensionalArray).get(index)]
                        return historyOfThisElement
                    }
                }
                return Collections.EMPTY_LIST
            }
        };
        
        // Create content
        viewer.setContentProvider(ArrayContentProvider.instance);
        viewer.input = newArrayList()
        
        // Make cells editable
        userValueColumn.editingSupport = new ValueColumnEditingSupport(viewer)
        
        // Delegate key listener
        val arrayCellEditor = this
        viewer.control.addKeyListener(new KeyAdapter() {
            
            override keyReleased(KeyEvent e) {
                // Cancel with Escape key, apply values with Enter key
                arrayCellEditor.delegateKeyRelease(e)
            }
        })
        
        // Use TAB to go to next row neighbor and activate cell editor
        val focusCellManager = new TableViewerFocusCellManager(viewer, new FocusCellOwnerDrawHighlighter(viewer));
        val activationSupport = new ColumnViewerEditorActivationStrategy(viewer)
        activationSupport.enableEditorActivationWithKeyboard = true
        TableViewerEditor.create(viewer, focusCellManager, activationSupport, ColumnViewerEditor.TABBING_HORIZONTAL.bitwiseOr(
            ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR).bitwiseOr(
            ColumnViewerEditor.TABBING_VERTICAL).bitwiseOr(
            ColumnViewerEditor.KEYBOARD_ACTIVATION))
        
        return viewer
    }
    
    /**
     * Delegate the key release event to the super implementation.
     */
    public def void delegateKeyRelease(KeyEvent e) {
        keyReleaseOccured(e)
    }
}