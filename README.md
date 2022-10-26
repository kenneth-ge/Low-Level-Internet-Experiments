# Low-Level-Internet-Experiments

Just some things I wrote to play around with low-level networking :P
I wrote all of this in Java

## DNS
When I started hosting websites on my server, I realized that if you have multiple different websites with different domain names, they all point to the same IP address. I thought it was kind of annoying that you have to use virtual hosting to reroute everything, and I thought it would be a lot easier if domain records could point you to the port as well as the IP address. So, that's what I did here. 

## Text
Just an example of a server that sends back text depending on what kind of request you give it, kind of like HTTP. The Swing and JavaFX clients are not working. 

## STUN Server
This is something I used to implement UDP hold punching for some P2P type stuff. I also experimented a bit with cryptography. Make sure to switch out the password if you're using this project for yourself!

## Networking Util
Just some utilities I wrote for the other projects here, including easier I/O etc.

## DNS with Bit-Level Compression
This is a more "hardcore" version of DNS and Text, which uses bit-level compression and other techniques to hyper-optimize the code. Of course, given that this is written in Java, a better approach would have been to write this in C or C++ -- but the point of this exercise was fun and theoretical exploration, and I believe it serves the purpose well enough. 
