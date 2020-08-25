FROM openjdk:16
LABEL maintainer="Adam.Kurowski@gmail.com"
RUN yum install -y python3 wget
COPY app/ /app
WORKDIR /app
RUN wget https://launcher.mojang.com/v1/objects/c5f6fb23c3876461d46ec380421e42b289789530/server.jar
RUN mkdir /minecraft
WORKDIR /minecraft
CMD ["python3","/app/starter.py"]
EXPOSE 25565/tcp
EXPOSE 25575/tcp