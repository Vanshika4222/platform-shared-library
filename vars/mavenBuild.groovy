def call(script) {
      echo "Hello Vanshika welcome to MavenBuild shared library"
   podTemplate(yaml: """
	apiVersion: v1
        kind: Pod
        metadata:
	    name: maven-pod
          labels:
            some-label: maven-pod
        spec:
          containers:
          - name: maven
            image: maven:alpine
            command:
            - cat
            tty: true
          - name: busybox
            image: busybox
            command:
            - cat
            tty: true
        """.stripIndent())
       node (maven-pod){
	  
           stage("Tools initialization") {
                   sh "mvn --version"
                   sh "java -version"
           }
	       
         stage("Checkout Code") {
                   git branch: 'master',
                       url: script.env.GIT_SOURCE_URL
           }

	   stage("Cleaning workspace") {
                   sh "mvn clean"
           }
	   stage("Compiling") {
                   sh "mvn compile"
           }
	   if(script.env.CODE_QUALITY == 'True'){
		 stage("Code Quality Analysis") {
		     echo 'Code Quality Analysis'
		 }
	   }
	   if(script.env.UNIT_TESTING == 'True'){
           stage("Running Testcase") {
                   sh "mvn test"
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
           stage("Packaging Application") {
                   sh "mvn package -DskipTests"
           }
	   
       }
   }
