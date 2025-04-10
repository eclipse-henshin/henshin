pipeline {
	options {
		timeout(time: 60, unit: 'MINUTES')
		buildDiscarder(logRotator(numToKeepStr:'5', artifactNumToKeepStr: 'main'.equals(env.BRANCH_NAME) ? '5' : '2' ))
		disableConcurrentBuilds(abortPrevious: true)
		timestamps()
	}
	agent {
		label 'ubuntu-latest'
	}
	tools {
		maven 'apache-maven-3.9.9'
		jdk 'temurin-jdk17-latest'
	}
	stages {
		stage('Build') {
			steps {
				sh '''
					mvn clean verify -P strict-jdk --fail-at-end \
						--batch-mode --no-transfer-progress --threads 1C
				'''
			}
		}
		stage('Deploy') {
			when {
				branch 'master'
			}
			steps{
				sshagent(['projects-storage.eclipse.org-bot-ssh']) {
					sh '''
						BUILD=nightly
						SRC="p2updatesite/target/repository"
						TRG="/home/data/httpd/download.eclipse.org/modeling/emft/henshin/updates/$BUILD"

						ssh genie.henshin@projects-storage.eclipse.org "rm -rf $TRG && mkdir -p $TRG"

						scp -r $SRC/* genie.henshin@projects-storage.eclipse.org:$TRG
						ssh genie.henshin@projects-storage.eclipse.org ls -al $TRG
					'''
				}
			}
		}
	}
}
