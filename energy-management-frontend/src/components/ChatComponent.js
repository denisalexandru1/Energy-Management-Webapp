import React, { useState, useEffect } from 'react';
import { Box, TextField, Button } from '@mui/material';
import { Client, Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const ChatComponent = ({ senderId, receiverId }) => {
  
  if (receiverId === undefined) {
    receiverId = window.location.href.split('/')[window.location.href.split('/').length - 1];
  }

  const [receiverUsername, setReceiverUsername] = useState('');

  fetch(`http://localhost:8082/user/${receiverId}`, {
    method: 'GET',
    headers: {
      'Authorization': localStorage.getItem('jwtToken')
    }
  })
  .then(res => res.json())
  .then(data => {
    setReceiverUsername(data.username);
  })
  .then(() => console.log("receiver username: " + receiverUsername))
  .catch(err => console.log(err));

  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');
  const [isTyping, setIsTyping] = useState(false);
  const [isConnected, setIsConnected] = useState(false);

  const socket = new SockJS(`http://localhost:8091/ws`, null, {
    transports: ['websocket', 'xhr-polling', 'iframe-eventsource'],
    origins: 'http://localhost:3001',
  });


  const chatStompClient = Stomp.over(socket);

  const handleSendMessage = () => {
    if (newMessage.trim() !== '' && isConnected) {
      const payload = {
        senderId: senderId,
        receiverId: receiverId,
        message: newMessage,
      };
  
      chatStompClient.publish({
        destination: '/destination/publish',
        body: JSON.stringify(payload),
      });
    
      setMessages([...messages, payload]);
      setNewMessage('');
    }
  };

  // const handleTyping = () => {
  //   if (isConnected) {
  //     const payload = {
  //       senderId: senderId,
  //       receiverId: receiverId,
  //       isTyping: true,
  //     };
  //
  //     chatStompClient.publish({
  //       destination: '/destination/typing',
  //       body: JSON.stringify(payload),
  //     });
  //   }
  // };

  useEffect(() => {
    const connectToWebSocket = async () => {
      try {
        await chatStompClient.connect({}, (frame) => {
          console.log('Connected:', frame);
          setIsConnected(true);
          chatStompClient.subscribe('/topic/chat', (message) => {
            processMessage(message);
          });

          // chatStompClient.subscribe('/topic/typing', (typingMessage) => {
          //   processTyping(typingMessage);
          // });

        });
      } catch (error) {
        console.error('Error connecting to WebSocket:', error);
      }
    };
  
    const disconnectFromWebSocket = () => {
      if (chatStompClient.connected) {
        chatStompClient.disconnect(() => {
          console.log('Disconnected from WebSocket');
        });
      }
    };
  
    const processMessage = (message) => {
      const jsonMessage = JSON.parse(message.body);
  
      if (jsonMessage.receiverId === senderId) {
        setMessages([...messages, jsonMessage]);
      }
    };

    // const processTyping = (typingMessage) => {
    //   const jsonTypingMessage = JSON.parse(typingMessage.body);
    //
    //   if (jsonTypingMessage.senderId === receiverId) {
    //     setIsTyping(jsonTypingMessage.typing);
    //   }
    // };
  
    connectToWebSocket();
  
    return () => disconnectFromWebSocket();
  }, [chatStompClient, senderId, messages]);

  return (
    <Box sx={{ width: '100%', marginTop: 2 }}>
    <h3>Sender id: {senderId}, Receiver id: {receiverId}</h3>
    <h2>Chat with {receiverUsername}</h2>
      <Box
        sx={{
          border: '1px solid #ccc',
          borderRadius: 4,
          padding: 2,
          minHeight: 200,
          overflowY: 'auto',
        }}
      >
        {messages.map((message, index) => (
        <div key={index} style={{ textAlign: message.senderId === senderId ? 'right' : 'left' }}>
          <strong>
            {message.senderId === senderId ? 'You' : receiverUsername}
          </strong> {message.message}
        </div>
        ))}

        {/*{isTyping && (*/}
        {/*    <div style={{ textAlign: 'left' }}>*/}
        {/*      <strong>*/}
        {/*        {receiverUsername} is typing...*/}
        {/*      </strong>*/}
        {/*    </div>*/}
        {/*)}*/}

      </Box>
      <Box sx={{ marginTop: 2 }}>
        <TextField
          fullWidth
          label="Type your message"
          variant="outlined"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          //onInput={handleTyping}
        />
      </Box>
      <Box sx={{ marginTop: 2 }}>
        <Button variant="contained" onClick={handleSendMessage}>
          Send
        </Button>
      </Box>
    </Box>
  );
};

export default ChatComponent;
