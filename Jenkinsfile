pipeline {
	options {
		timeout(time: 60, unit: 'MINUTES')
		buildDiscarder(logRotator(numToKeepStr:'20', artifactNumToKeepStr: 'main'.equals(env.BRANCH_NAME) ? '5' : '2' ))
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
					mvn clean verify -P strict-jdk,javadoc --fail-at-end -Dmaven.test.failure.ignore=true \
						--batch-mode --no-transfer-progress --threads 1C
				'''
			}
			post {
				always {
					archiveArtifacts artifacts: '.*log,**/target/**/.*log', allowEmptyArchive: true
					junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
					discoverGitReferenceBuild referenceJob: 'eclipse.henshin/master'
					recordIssues enabledForFailure: true, publishAllIssues: true, ignoreQualityGate: true, tools: [
							eclipse(name: 'Compiler', pattern: '**/target/compilelogs/*.xml'),
							javaDoc()
						], qualityGates: [[threshold: 1, type: 'NEW', unstable: true]]
					recordIssues enabledForFailure: true, publishAllIssues: true, ignoreQualityGate: true, tools: [
							mavenConsole(),
						], qualityGates: [[threshold: 1, type: 'NEW_HIGH', unstable: true]]
				}
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
