FROM asneg/opcuastack:release-4

WORKDIR /home/OpcUaWebServer

# Add source files to docker container
COPY / ./

# Prepare Python dependencies for tests
RUN apt-get update && apt-get install -y python3-pip && apt-get install -y vim
RUN pip3 install -r ./ftest/requirements.txt

# Prepare workdir

# Build
RUN sh build.sh -t local -i / -j 2 -B Release

# Clean build files
RUN sh build.sh -t clean

# Start OpcUaWebServer
EXPOSE 8890 4840 8080 8081 8082 22
CMD ["OpcUaServer4", "/etc/OpcUaStack/OpcUaWebServer/OpcUaServer.xml"]
