# KIELER - Kiel Integrated Environment for Layout Eclipse RichClient
# http://www.informatik.uni-kiel.de/rtsys/kieler/
#
# Copyright 2013 by
# + Kiel University
#   + Department of Computer Science
#     + Real-Time and Embedded Systems Group
#
# This code is provided under the terms of the Eclipse Public License (EPL).
# See the file epl-v10.html for the license text.

#
# This script checks plugins and produces error if non eclipse-ui plugins use banned eclipse features or have dependencies to eclipse-ui plugins.
# Author: Alexander Schulz-Rosengarten <als@informatik.uni-kiel.de>
#

from __future__ import print_function # python3 print

import os
import sys
import re
import argparse
from os.path import isfile, isdir, join, abspath, relpath, normpath

PROJECT_PREFIX = 'de.cau.cs.kieler'
DEP_FILE = 'dependencies.txt'
UI_PROPERTY = 'Eclipse-UI'
UI_REGEX = '.*\\.ui'
BAN_REGEX = '.*\\.ui'

def main(args):
    print('-- Checking plugin dependencies --')

    failed = []
    plugins = args.path
    if not isdir(plugins):
        stop('%s is not a directory' % plugins)

    isUI = re.compile(UI_REGEX)
    for plugin in sorted(os.listdir(plugins)):
        base = join(plugins, plugin)
        if plugin.startswith(PROJECT_PREFIX) and isdir(base) and isfile(join(base, '.project')):
            print('Checking %s' % plugin)
            # Determine if it is a UI plugin
            ui = None
            # Check if there is a property in the manifest
            try:
                manifest = join(base, 'META-INF', 'MANIFEST.MF')
                if isfile(manifest):
                    with open(manifest, 'r') as f:
                        for line in f.readlines():
                            if line.startswith(UI_PROPERTY):
                                ui = line.split(':')[1].strip().lower() == 'true'
            except:
                pass # ignore

            # If not, match plugin id
            if ui is None:
                ui = isUI.match(plugin)

            # Perform checks
            if ui:
                print('- Eclipse UI plugin. No constraints.')
            else:
                xml = checkPluginXml(base, args)
                deps = checkDependencies(base, args)
                if not (xml and deps):
                    failed.append(plugin)

    # report
    if len(failed):
        print('%s The following non-eclipse-ui plugins do not comply with the defined requirements:' % ('[WARNING]' if args.warn else '[ERROR]'))
        for fail in failed:
            print(' - %s' % fail)

    # indicate error
    if not args.warn and len(failed):
        sys.exit(1)

def checkDependencies(base, args):
    isBanned = re.compile(BAN_REGEX)
    filepath = join(base, DEP_FILE)
    if isfile(filepath):
        with open(filepath, 'r') as file:
            success = True
            for line in file.readlines()[1:]:
                dep = line.split(':')[1]
                if isBanned.match(dep):
                    print('Has (possibly transient) dependency to banned plugin: %s' % dep)
                    success = False
            return success
    else:
        print('Missing dependencies file (%s)' % DEP_FILE)
        return False

def checkPluginXml(base, args):
    if isfile(join(base, 'plugin.xml')):
        print('Has plugin.xml')
        return False
    return True

def stop(msg):
    errPrint('[ERROR] ' + msg)
    sys.exit(2)

def errPrint(*args, **kwargs):
    print(*args, file=sys.stderr, **kwargs)


if __name__ == "__main__":
    argParser = argparse.ArgumentParser(description='This script checks plugins and produces error if non eclipse-ui plugins use banned eclipse features or have dependencies to eclipse-ui plugins.')
    argParser.add_argument('-w' , '--warn', dest='warn', action="store_true", help='produce only warings instead of errors')
    argParser.add_argument('path', help='path to the folder containing the plugins to check')
    try:
        main(argParser.parse_args())
    except KeyboardInterrupt:
        print('\nAbort')
        sys.exit(0)