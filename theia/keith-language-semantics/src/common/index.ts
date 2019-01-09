/*
 * KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
 *
 * http://rtsys.informatik.uni-kiel.de/kieler
 *
 * Copyright 2018 by
 * + Kiel University
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 *
 * This code is provided under the terms of the Eclipse Public License (EPL).
 */

// Language register, holds all languages that are supported by KEITH
export const languageDescriptions: LanguageDescription[] = [
    {id: "sctx", name: "SCCharts"},
    {id: "scl", name: "SCL"},
    {id: "strl", name: "Estrel"},
    {id: "lus", name: "Lustre"},
    {id: "kext", name: "KExt"},
    {id: "anno", name: "Annotations"},
    {id: "sctx", name: "SCCharts"},
]

export class LanguageDescription {
    id: string
    name: string
    keywords?: string[]
}