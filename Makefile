JAVAC = gcj
OPS = -g
LINK_OPS =
NODE_OBJS = P2pS.o P2pRequest.o P2pProtocolHandler.o ClientRequestThread.o Node.o
CLIENT_OBJS =

node_server: $(NODE_OBJS)
	$(JAVAC) --main=Node -o Node $(NODE_OBJS)

Node.o: Node.java
	$(JAVAC) $(OPS) -c Node.java

ClientRequestThread.o: ClientRequestThread.java
	$(JAVAC) $(OPS) -c ClientRequestThread.java

P2pProtocolHandler.o: P2pProtocolHandler.java
	$(JAVAC) $(OPS) -c P2pProtocolHandler.java

P2pRequest.o: P2pRequest.java
	$(JAVAC) $(OPS) -c P2pRequest.java

P2pS.o: P2pS.java
	$(JAVAC) $(OPS) -c P2pS.java

clean:
	rm -rf *.o