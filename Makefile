JAVAC = gcj
OPS = -g -C
LINK_OPS =
NANOXMLPATH = redes/nanoxml/java/nanoxml-lite-2.2.3.jar

PARSER_OBJS = ParseXSPF.class Song.class $(NANOXMLPATH)
NODE_OBJS = P2pRequest.class P2pProtocolHandler.class ConsultThread.class ClientRequestThread.class Node.class $(PARSER_OBJS)
CLIENT_OBJS = Client.class P2pProtocolHandler.class P2pRequest.class ServerRequest.class $(PARSER_OBJS)

all:
	echo -e 'Uso: make <client|node|parser>'

client: $(CLIENT_OBJS)
	$(JAVAC) --main=Client -o Client $(CLIENT_OBJS)

node: $(NODE_OBJS)
	$(JAVAC) --main=Node -o Node $(NODE_OBJS)

parser: $(PARSER_OBJS)
	$(JAVAC) --main=ParseXSPF -o parseXSPF $(PARSER_OBJS)

ParseXSPF.class: ParseXSPF.java
	$(JAVAC) $(OPS) -I .:$(NANOXMLPATH) -c ParseXSPF.java

Node.class: Node.java
	$(JAVAC) $(OPS) -c Node.java

Client.class: Client.java
	$(JAVAC) $(OPS) -c Client.java

ClientRequestThread.class: ClientRequestThread.java
	$(JAVAC) $(OPS) -c ClientRequestThread.java

ServerRequest.class: ServerRequest.java
	$(JAVAC) $(OPS) -c ServerRequest.java

P2pProtocolHandler.class: P2pProtocolHandler.java
	$(JAVAC) $(OPS) -c P2pProtocolHandler.java

P2pRequest.class: P2pRequest.java
	$(JAVAC) $(OPS) -c P2pRequest.java

P2pS.class: P2pS.java
	$(JAVAC) $(OPS) -c P2pS.java

Song.class: Song.java
	$(JAVAC) $(OPS) -c Song.java

ConsultThread.class: ConsultThread.java
	$(JAVAC) $(OPS) -c ConsultThread.java

clean:
	rm -rf *.class *.o Node Client parseXSPF
