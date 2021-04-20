def call(script) {
      echo "Hello Vanshika welcome to MavenBuild shared library"
   
       node {
           stage("Tools initialization") {
                   sh "mvn --version"
                   sh "java -version"
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
