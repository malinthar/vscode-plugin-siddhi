import * as path from 'path';
import {workspace, Disposable, ExtensionContext} from 'vscode';
import { LanguageClient, LanguageClientOptions, ServerOptions } from 'vscode-languageclient';


const main:string='StdioLauncher';

export function activate(context:ExtensionContext){
   const JAVA_HOME=process.env.JAVA_HOME
   let excecutable : string = path.join(String(JAVA_HOME),'bin', 'java');
   let classPath = path.join(__dirname, '..', 'launcher','ls-launcher.jar');
   const SIDDHI_HOME=path.join(__dirname, '..', 'lib','*');
   classPath=SIDDHI_HOME+':'+classPath
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
   let disposable = new LanguageClient('SiddhiLanguageServer', 'Siddhi Language Server', serverOptions, clientOptions).start();
   context.subscriptions.push(disposable);
}