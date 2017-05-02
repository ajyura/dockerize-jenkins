import hudson.model.*;
import jenkins.model.*;


Thread.start {
      sleep 10000
      println "--> setting agent port for jnlp"
      def env = System.getenv()
      int port = env['JENKINS_SLAVE_AGENT_PORT'].toInteger()
      Jenkins.instance.setSlaveAgentPort(port)
      println "--> setting agent port for jnlp... done"
}

System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", "sandbox allow-scripts allow-same-origin; default-src 'none'; img-src 'self'; style-src 'self' 'unsafe-inline'; script-src 'self' 'unsafe-inline';")
