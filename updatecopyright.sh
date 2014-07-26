#!/bin/bash

OLD=Copyright (c) 2010-2012 Henshin developers
NEW=Copyright (c) 2010-2014 Henshin developers

echo "Collecting files..."
mf=$(find . -name '*.java' -o -name '*.java' -o -name '*.properties')
for f in $mf; do
    echo "Processing $f"
    sed "s/$OLD/$NEW/g" "$f" > test.txt
    break
    # && mv $TFILE "$f"
done
