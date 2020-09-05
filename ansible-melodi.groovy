properties([
  parameters([string(defaultValue: '', description: 'Please enter VM IP ', name: 'nodeIP', trim: true)])])

if (nodeIP.trim()) {
      node {
           withCredentials([sshUserPrivateKey(credentialsId: 'jenkins-master-key', keyFileVariable: 'key', passphraseVariable: '', usernameVariable: 'username')]) {
                stage('Pull Repo'){
                   git branch:'release-1.0', changelog: false, poll: false, url: 'https://github.com/iroda1986/melodi.git' 
                 }
                stage ('install Apache'){
                  sh ' ssh -o  StrictHostKeyChecking=no -i $key root@${nodeIP} yum install httpd -y '
           
                }
                stage('install git') {
                   sh 'scp -r  -o  StrictHostKeyChecking=no -i $key * root@${nodeIP}: /var/www/html'
          
                }
                stage('Change Ownership'){
        
                  sh 'ssh -o  StrictHostKeyChecking=no -i $key root@${nodeIP} chown -R apache:apache /var/www/html '
                }
                stage('Start Apache '){
        
                  sh 'ssh -o  StrictHostKeyChecking=no -i $key root@${nodeIP} " start httpd && enable httpd "
                }
            }
     
        } 
}
else {
   error 'Please enter valid IP address'
}
