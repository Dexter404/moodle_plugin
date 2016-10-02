<?php
//namespace MessageService;

error_reporting(E_ALL);

//require_once __DIR__.'/packages/bidiDemo/MessageService.php';
require_once __DIR__.'/packages/bidiDemo/MessageServiceTypes.php';
require_once __DIR__.'/lib/php/lib/Thrift/ClassLoader/ThriftClassLoader.php';
require_once 'MessageListener.php';

use Thrift\ClassLoader\ThriftClassLoader;

//$GEN_DIR = realpath(dirname(__FILE__).'/.').'/gen-php/LMSReporting';

$loader = new ThriftClassLoader();
$loader->registerNamespace('Thrift', __DIR__ . '/lib/php/lib');
$loader->registerDefinition('bidiDemo', __DIR__ . '/packages');
//$loader->registerDefinition('shared', $GEN_DIR);
//$loader->registerDefinition('tutorial', $GEN_DIR);
$loader->register();

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

use Thrift\Protocol\TBinaryProtocol;
use Thrift\Transport\TSocket;
use Thrift\Transport\TBufferedTransport;
use Thrift\Exception\TException;
use bidiDemo\MessageServiceClient;
use bidiDemo\MessageServiceIf;
use bidiDemo\Message;

class MessageServiceHandler implements MessageServiceIf{
    public function sendMessage(Message $msg){
        echo "---------------------<br/>";
        echo "Got message from " . $msg->clientName . "<br/>";
        echo $msg->message . "<br/>";
        echo "---------------------<br/>";
    }    
}

try {
    $socket = new TSocket('localhost', 9095);
    //$socket->setSendTimeout(2000);
    $transport = new TBufferedTransport($socket, 1024, 1024);
    $protocol = new TBinaryProtocol($transport);
    $client = new MessageServiceClient($protocol);
    $transport->open();
    
    $msg = new Message(array('clientName'=>"D",'message'=>"Hello I am php client."));
    echo "Client Name: ".$msg->clientName."<br>Message: ".$msg->message;
    $client->sendMessage($msg);
    echo "<br>Message sent!";

    $msgListener = new MessageListener($protocol, new MessageServiceHandler());
    $msgListener->start();
    
    //$transport->close();  
} catch (TException $tx) {
    print 'TException: '.$tx->getMessage()."\n";
}

