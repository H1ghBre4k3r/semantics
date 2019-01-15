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

import { ContainerModule, interfaces } from 'inversify'
import { MonacoEditorProvider } from '@theia/monaco/lib/browser/monaco-editor-provider'
import { KeithMonacoEditorProvider } from "./keith-monaco-editor-provider"
import { KeithLanguageClientContribution } from './keith-language-client-contribution';
import { LanguageClientContribution } from '@theia/languages/lib/browser';
import { RegistrationContribution, monarchLanguage, KeithMonarchLanguage, configuration } from './registration-contribution';
import { languageDescriptions, LanguageDescription } from "../common"
import { CommandContribution } from '@theia/core';

export default new ContainerModule((bind: interfaces.Bind, unbind: interfaces.Unbind, isBound: interfaces.IsBound, rebind: interfaces.Rebind) => {
    // register languages
    languageDescriptions.forEach((language: LanguageDescription) => {
        monaco.languages.register({
            id: language.id,
            aliases: [language.name, language.id],
            extensions: ['.' + language.id],
            mimetypes: ['text/' + language.id]
        })
    })
    // get keywords for highlighting
    bind(CommandContribution).toDynamicValue(ctx => {
        const returnValue = ctx.container.get(RegistrationContribution)
        const client = ctx.container.get(KeithLanguageClientContribution)
        client.languageClient.then(lClient => {
            lClient.sendRequest("keith/registration/get-languages").then((languages: LanguageDescription[]) => {
                languages.forEach((language: LanguageDescription) => {
                    if (monaco.languages.getEncodedLanguageId(language.id)) {
                        let mLanguage = monarchLanguage as KeithMonarchLanguage
                        mLanguage.keywords = language.keywords ? language.keywords : []
                        mLanguage.tokenPostfix = "." + language.id
                        monaco.languages.setLanguageConfiguration(language.id, configuration)
                        monaco.languages.setMonarchTokensProvider(language.id, mLanguage)
                    } else {
                        console.warn("Got unregistered language " + language.id +
                        ". A language has to be registered in frontend-extension and language-client-contribution.")
                    }
                })
            })
        }).catch(() => {
            throw new Error("Failed to get keywords for language registration")
        })
        return returnValue
    })
    bind(LanguageClientContribution).toDynamicValue(ctx => ctx.container.get(KeithLanguageClientContribution))

    bind(KeithLanguageClientContribution).toSelf().inSingletonScope()
    bind(RegistrationContribution).toSelf().inSingletonScope()
    bind(KeithMonacoEditorProvider).toSelf().inSingletonScope()
    rebind(MonacoEditorProvider).to(KeithMonacoEditorProvider).inSingletonScope()
})