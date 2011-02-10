#!/bin/bash
if [ $# -ne 2 ]; then
  echo "usage: $0 <revision> <tag_name>"
  exit
fi

for i in plugins features releng
do
  svn copy https://dev.eclipse.org/svnroot/modeling/org.eclipse.emft.henshin/$i/branches/0_8_0@$1 https://dev.eclipse.org/svnroot/modeling/org.eclipse.emft.henshin/$i/tags/$2 -m "tagging $2" --username ckrause
done

