#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <old-version> <new-version>"
    exit 1;
fi

OLD=$1
NEW=$2

TFILE=dummy

echo "Collecting MANIFST.MF files..."
mf=$(find plugins -name MANIFEST.MF)
for f in $mf; do
    echo "Processing $f"
    sed "s/Bundle-Version: $OLD/Bundle-Version: $NEW/g" "$f" > $TFILE && mv $TFILE "$f"
done

echo "Collecting feature.xml files..."
features=$(find features -name feature.xml)
for f in $features; do
    echo "Processing $f"
    sed "s/$OLD/$NEW/g" "$f" > $TFILE && mv $TFILE "$f"
done

echo "Collecting pom.xml files..."
pom=$(find . -name pom.xml)
for f in $pom
do
    echo "Processing $f"
    sed "s/<version>$OLD/<version>$NEW/g" "$f" > $TFILE && mv $TFILE "$f"
done
