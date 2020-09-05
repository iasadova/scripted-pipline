properties([
  parameters([string(defaultValue: '', description: 'Please enter VM IP ', name: 'nodeIP', trim: true)])])

if (nodeIP.trim()) {
    node {
        withCredentials([sshUserPrivateKey(credentialsId: 'jenkins-master-key', keyFileVariable: 'key', passphraseVariable: '', usernameVariable: 'username')]) {
            stage('Pull Repo'){
                   git branch:'release-1.0', changelog: false, poll: false, url: 'https://github.com/ikambarov/melodi.git' 
            }
            stage("Install Apache") {
                sh 'ssh -o StrictHostKeyChecking=no -i $key $username@${nodeIP} yum install httpd -y'
            }
            stage("Start Apache"){
             sh 'ssh -o StrictHostKeyChecking=no -i $key $username@${nodeIP} systemctl start httpd'
            }
            stage("Enable Apache"){
             sh 'ssh -o StrictHostKeyChecking=no -i $key $username@${nodeIP} systemctl enable httpd'
            }
            stage("Copy Files"){
             sh 'scp -r -o StrictHostKeyChecking=no -i $key * $username@${nodeIP}:/var/www/html/'
            }
            stage("Change Ownership"){
             sh 'ssh -o StrictHostKeyChecking=no -i $key $username@${nodeIP} chown -R apache:apache /var/www/html'
            }
        }
     
    } 
}
else {
   error 'Please enter valid IP address'
}
