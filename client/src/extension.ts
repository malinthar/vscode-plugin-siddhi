import * as path from 'path';
import {workspace, Disposable, ExtensionContext} from 'vscode';
import { LanguageClient, LanguageClientOptions, ServerOptions } from 'vscode-languageclient';


const main:string='StdioLauncher';

export function activate(context:ExtensionContext){
   //local java path
   const JAVA_HOME=process.env.JAVA_HOME
   //pltform or OS,to be used for custom class paths
   const platform=process.platform;
   //console.log(JAVA_HOME)
   //executable path
   let excecutable : string = path.join(String(JAVA_HOME),'bin', 'java');
   //console.log(excecutable)
   //server calss path
   let classPath = path.join(__dirname, '..', 'launcher', 'ls-launcher.jar');
   console.log(classPath);
   //args to server options
   const args: string[] = ['-cp', classPath];

   //process.env.LSDEBUG="true";
   
   //'-Xdebug','-Xnoagent','-Djava.compiler=NONE','-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005,quiet=y',
   if (process.env.LSDEBUG === "true") {
      //console.log('LSDEBUG is set to "true". Services will run on debug mode');
      args.push('-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005,quiet=y');
   }
   //server options
   let serverOptions:ServerOptions={
      command: excecutable,
      args: [...args, main],
      options: {}
   }

   //Client options
   let clientOptions:LanguageClientOptions={
       // Register the server for siddhi documents
       documentSelector: [{scheme: 'file', language: 'siddhi'}],
   
   }

   // Create the language client and start the client(ups the stream).
   let disposable = new LanguageClient('SiddhiLanguageServer', 'Siddhi Language Server', serverOptions, clientOptions).start();
	
	// Push the disposable to the context's subscriptions so that the 
	// client can be deactivated on extension deactivation
   context.subscriptions.push(disposable);
}