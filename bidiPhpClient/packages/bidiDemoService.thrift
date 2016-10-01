namespace java bidiDemo
namespace php bidiDemo

struct Message {
  1: string clientName,
  2: string message
}

service MessageService {
  oneway void sendMessage(Message msg),
}