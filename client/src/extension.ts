import * as path from 'path';
import {workspace, Disposable, ExtensionContext} from 'vscode';
import { LanguageClient, LanguageClientOptions, ServerOptions } from 'vscode-languageclient';


const main:string='StdioLauncher';

export function activate(context:ExtensionContext){
   const JAVA_HOME=process.env.JAVA_HOME
   //if java_home_is_not_set?
   //const SIDDHI_HOME="/home/malintha/Documents/wso2/siddhi-modules/tooling/siddhi-tooling-5.1.0/"
   const SIDDHI_HOME=workspace.getConfiguration().get("siddhi_home")
   let excecutable : string = path.join(String(JAVA_HOME),'bin', 'java');
   let classPath = path.join(__dirname, '..', 'launcher','ls-launcher-0.0.1-SNAPSHOT.jar');
   const SIDDHI_HOME_LIB=path.join(String(SIDDHI_HOME), 'lib','*');
   const SIDDHI_HOME_PLUGINS=path.join(String(SIDDHI_HOME), 'wso2','lib','plugins','*');
   const LANGSERVER_LIB=path.join(String(SIDDHI_HOME), 'langserver_lib','*');
   classPath=SIDDHI_HOME+':'+SIDDHI_HOME_LIB+':'+SIDDHI_HOME_PLUGINS+':'+LANGSERVER_LIB+':'+classPath
   const args: string[] = ['-cp',classPath];

   process.env.LSDEBUG="true";
   //'-Xdebug','-Xnoagent','-Djava.compiler=NONE','-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005,quiet=y',
   if (process.env.LSDEBUG === "true") {
      //console.log('LSDEBUG is set to "true". Services will run on debug mode');
      args.push('-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005,quiet=y');
   }
   let serverOptions:ServerOptions={
      command:excecutable,
      args: [...args, main],
      options: {}
   }
   let clientOptions:LanguageClientOptions={
       documentSelector: [{scheme: 'file', language: 'siddhi'}],
   }
   let disposable = new LanguageClient('SiddhiLanguageServer', 'Siddhi Language Server', serverOptions, clientOptions,true).start();

   context.subscriptions.push(disposable);
}