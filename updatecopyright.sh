#!/bin/bash

OLD="2010-2012 Henshin developers"
NEW="2010-2014 Henshin developers"

TFILE=dummy

echo "Collecting files..."
mf=$(find . -name '*.java' -o -name '*.java' -o -name '*.properties')
for f in $mf; do
    echo "Processing $f"
    sed "s/$OLD/$NEW/g" "$f" > $TFILE && mv $TFILE "$f"
done
