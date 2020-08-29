node ('master'){
    stage ('init') {
         withCredentials([sshUserPrivateKey(credentialsId: 'jenkins-master-key', keyFileVariable: 'SSHKEY', passphraseVariable: '', usernameVariable: 'SSHUSERNAME')]) {
            sh 'ssh -o StrictHostKeyChecking=no -i $SSHKEY  $SSHUSERNAME@157.245.214.206 yum install epel-release -y '
        }
    }
}

