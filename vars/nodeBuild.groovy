def call(Map config=[:],script) {
    // Any valid steps can be called from this code, just like in other
    // Scripted Pipeline
    echo "Hello ${config.name}"
    echo "Hello Vanshika welcome to nodeBuild shared library"
	def label = "Nodekubernetes"
	podTemplate(label: label,
  containers: [containerTemplate(name: 'node', image: 'node', ttyEnabled: true, command: 'cat')]
  )
	{
       node(label) {
	       container('node'){
        git url: script.env.GIT_SOURCE_URL
        
        stage("Install") {
            sh "npm install"
        }
        if(script.env.CODE_QUALITY == 'True')
        {
         stage("Code Quality Analysis") {
             echo 'Code Quality Analysis'
         }
        }
        if(script.env.UNIT_TESTING == 'True')
        {
        stage("Test") {
            sh "npm test"
            
        }
        }
         if(script.env.CODE_COVERAGE == 'True')
        {
         stage("Code Coverage") {
             echo 'Code Coverage'
         }
        }
         if(script.env.SECURITY_TESTING == 'True')
        {
         stage("Security Testing") {
             echo 'Security Testing'
         }
        }
        stage("Deploy") {
            if (config.name == "tapas") {
                sh "npm publish"
            }
        }
    }
       }}}
