<?php

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
class MessageListener extends Thread{
    public function run() {
        echo "thread running";
    }
}
