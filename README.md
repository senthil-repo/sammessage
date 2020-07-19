Disclaimer:
 - Please install Java, Gradle, AWS SAM CLI (command line tool) & other basic softwares that are needed to run any java based applications.
 
 - I have used Java 8 and followed Test Driven Development to build the application.
 
 - I have used Gradle to build the application.

Please follow the steps mentioned below to build & run the application:

1) Download the source code from the URL below :

https://github.com/senthil-repo/sammessage.git

2) Build the source code : 

Go to the folder /sammessage/ (where you can find the build.gradle, template.yaml) and open the SAM CLI command prompt.
Run the following command.
sam build

You should be getting a message 'Build Succeeded'.

3) Package & deploy :
Please run the following command, that package & deploy the source code and any dependent jars.

sam deploy --guided

Follow the on-screen instruction. When you are prompted, give a stack name and the AWS region that you want the stack to be deployed.

Eg.
        Stack Name [sammessage]: sammessage
        
        AWS Region [eu-west-1]: eu-west-1

Further, you will be prompted for consent(s) to proceed. Please type 'Y' for every prompt and and proceed.

Eg.
        #Shows you resources changes to be deployed and require a 'Y' to initiate deploy
        Confirm changes before deploy [Y/n]: y
        
        #SAM needs permission to be able to create roles to connect to the resources in your template
        Allow SAM CLI IAM role creation [Y/n]: y
        
        Save arguments to samconfig.toml [Y/n]: y
        ....
	Deploy this changeset? [y/N]:

In the end, you should be getting the following message. This is the indication that the stack & application is successfully created in AWS.

Successfully created/updated stack - sammessage in (your region)


4) Further, please login to your AWS account and you should find the following stacks under respective services.
1. SQS Queue  (in a name MySqsQueue)
2. Lambda Function (in a name that starts with 'sammessage-messageprocessor') 
3. Dynamo table (in a name processedmessage)

Testing: 

Send an JSON input message to SQS queue and you should be getting the response message in the dynamodb.


Other details:	

To generate the output (i.e all possible combinations of the values), we could do it in couple of ways. I have choosen a 3rd party library to do it.
Bascially, my idea is not to invent the wheel.


Thanks for your time in assessing this task. In case i accidentally missed anything, please do not hesitate to notify me.
