#!/bin/bash

export PATH=$PATH:/shared/common/apache-maven-3.0-beta-1/bin

cat << EOF > $HOME/.m2/settings.xml
<settings>
  <proxies>
   <proxy>
      <active>true</active>
      <protocol>http</protocol>
      <host>proxy.eclipse.org</host>
      <port>9898</port>
      <nonProxyHosts>eclipse.org</nonProxyHosts>
    </proxy>
  </proxies>
</settings>
EOF

mvn clean install
