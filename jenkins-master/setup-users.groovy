import jenkins.*
import hudson.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import hudson.plugins.sshslaves.*;
import hudson.model.*
import jenkins.model.*
import hudson.security.*

global_domain = Domain.global()
credentials_store =
  Jenkins.instance.getExtensionList(
    'com.cloudbees.plugins.credentials.SystemCredentialsProvider'
  )[0].getStore()

credentials = new BasicSSHUserPrivateKey(CredentialsScope.GLOBAL,null,"root",new BasicSSHUserPrivateKey.UsersPrivateKeySource(),"","")

credentials_store.addCredentials(global_domain, credentials)

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
def adminUsername = System.getenv('JENKINS_ADMIN_USERNAME') ?: 'admin'
def adminPassword = System.getenv('JENKINS_ADMIN_PASSWORD') ?: 'password'
hudsonRealm.createAccount(adminUsername, adminPassword)
//hudsonRealm.createAccount("admin", "admin")

def instance = Jenkins.getInstance()
instance.setSecurityRealm(hudsonRealm)
instance.save()


def strategy = new GlobalMatrixAuthorizationStrategy()

//  Slave Permissions
strategy.add(hudson.model.Computer.BUILD,'admin')
strategy.add(hudson.model.Computer.CONFIGURE,'admin')
strategy.add(hudson.model.Computer.CONNECT,'admin')
strategy.add(hudson.model.Computer.CREATE,'admin')
strategy.add(hudson.model.Computer.DELETE,'admin')
strategy.add(hudson.model.Computer.DISCONNECT,'admin')

//  Credential Permissions
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.CREATE,'admin')
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.DELETE,'admin')
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.MANAGE_DOMAINS,'admin')
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.UPDATE,'admin')
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.VIEW,'admin')

//  Overall Permissions
strategy.add(hudson.model.Hudson.ADMINISTER,'admin')
strategy.add(hudson.PluginManager.CONFIGURE_UPDATECENTER,'admin')
strategy.add(hudson.model.Hudson.READ,'admin')
strategy.add(hudson.model.Hudson.RUN_SCRIPTS,'admin')
strategy.add(hudson.PluginManager.UPLOAD_PLUGINS,'admin')

//  Job Permissions
strategy.add(hudson.model.Item.BUILD,'admin')
strategy.add(hudson.model.Item.CANCEL,'admin')
strategy.add(hudson.model.Item.CONFIGURE,'admin')
strategy.add(hudson.model.Item.CREATE,'admin')
strategy.add(hudson.model.Item.DELETE,'admin')
strategy.add(hudson.model.Item.DISCOVER,'admin')
strategy.add(hudson.model.Item.READ,'admin')
strategy.add(hudson.model.Item.WORKSPACE,'admin')

//  Run Permissions
strategy.add(hudson.model.Run.DELETE,'admin')
strategy.add(hudson.model.Run.UPDATE,'admin')

//  View Permissions
strategy.add(hudson.model.View.CONFIGURE,'admin')
strategy.add(hudson.model.View.CREATE,'admin')
strategy.add(hudson.model.View.DELETE,'admin')
strategy.add(hudson.model.View.READ,'admin')

//  Setting Anonymous Permissions
strategy.add(hudson.model.Hudson.READ,'anonymous')
strategy.add(hudson.model.Item.BUILD,'anonymous')
strategy.add(hudson.model.Item.CANCEL,'anonymous')
strategy.add(hudson.model.Item.DISCOVER,'anonymous')
strategy.add(hudson.model.Item.READ,'anonymous')

// Setting Admin Permissions
strategy.add(Jenkins.ADMINISTER, "admin")

// Setting easy settings for local builds
def local = System.getenv("BUILD").toString()
if(local == "local") {
  //  Overall Permissions
  strategy.add(hudson.model.Hudson.ADMINISTER,'anonymous')
  strategy.add(hudson.PluginManager.CONFIGURE_UPDATECENTER,'anonymous')
  strategy.add(hudson.model.Hudson.READ,'anonymous')
  strategy.add(hudson.model.Hudson.RUN_SCRIPTS,'anonymous')
  strategy.add(hudson.PluginManager.UPLOAD_PLUGINS,'anonymous')
}

instance.setAuthorizationStrategy(strategy)
instance.save()
