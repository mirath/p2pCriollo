JAVAC = gcj
OPS = -g -C
LINK_OPS =
NODE_OBJS = P2pRequest.class P2pProtocolHandler.class ClientRequestThread.class Node.class
CLIENT_OBJS = Client.class P2pProtocolHandler.class P2pRequest.class

all:
	echo -e 'Uso: make <client|node>'

client: $(CLIENT_OBJS)
	$(JAVAC) --main=Client -o Client $(CLIENT_OBJS)

node: $(NODE_OBJS)
	$(JAVAC) --main=Node -o Node $(NODE_OBJS)

Node.class: Node.java
	$(JAVAC) $(OPS) -c Node.java

Client.class: Client.java
	$(JAVAC) $(OPS) -c Client.java

ClientRequestThread.class: ClientRequestThread.java
	$(JAVAC) $(OPS) -c ClientRequestThread.java

P2pProtocolHandler.class: P2pProtocolHandler.java
	$(JAVAC) $(OPS) -c P2pProtocolHandler.java

P2pRequest.class: P2pRequest.java
	$(JAVAC) $(OPS) -c P2pRequest.java

P2pS.class: P2pS.java
	$(JAVAC) $(OPS) -c P2pS.java

clean:
	rm -rf *.class *.o Node Client
