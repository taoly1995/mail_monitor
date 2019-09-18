FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD /target/javamail-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

#定义时区参数
ENV TZ=Asia/Shanghai

#设置时区
# Ubuntu
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
# Ubuntu
RUN echo 'Asia/Shanghai' >/etc/timezone