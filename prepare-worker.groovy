properties([
  parameters([string(defaultValue: '', description: 'Please enter VM IP ', name: 'nodeIP', trim: true)])])

if (nodeIP.trim()) {
       node {
   withCredentials([sshUserPrivateKey(credentialsId: 'jenkins-master-key', keyFileVariable: 'key', passphraseVariable: '', usernameVariable: 'username')]) {
     stage('init'){
           sh 'ssh -o  StrictHostKeyChecking=no -i $key root@${nodeIP} yum install epel-release -y '
        }
     stage('install git'){
           sh 'ssh -o  StrictHostKeyChecking=no -i $key root@${nodeIP} yum install git -y '
           
        }
     stage('install java'){
           sh 'ssh -o  StrictHostKeyChecking=no -i $key root@${nodeIP} yum install java-1.8.0-openjdk-devel -y'
          
        }
      }
     
  } 
}
else {
   error 'Please enter valid IP address'
}
