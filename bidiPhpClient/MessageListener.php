<?php
//namespace MessageService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of MessageListener
 *
 * @author rarora
 */
require_once __DIR__.'/packages/bidiDemo/MessageService.php';

use bidiDemo\MessageServiceProcessor;

class MessageListener extends Thread{
    private $processor;
    private $protocol;
    
    public function __construct($protocol, $messageServiceHandler){
        $this->protocol = $protocol;
        $this->processor = new MessageServiceProcessor($messageServiceHandler);
    }

    public function run() {
        //while (true) {
            try {
                echo "Client Processor ran.<br/>";
                while (processor.process(protocol, protocol) === true) { }
                echo "Client Processor stopped.";
            } catch (TException $tx) {
                print 'TException: '.$tx->getMessage()."\n";
            }
        //}
    }
}
