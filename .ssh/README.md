# SSHing via Virtual Private Server

## Setup using AWS Lightsail
> [!NOTE] 
> Lightsail is not cloud, it is just a virtual machine.

> [!IMPORTANT]
> Make sure to create an instance with more RAM and memory to avoid storage shortage while installing dependencies.

- Create Static IP from the Networking section of the instance.
- Download default key (file_name.pem) and store it in .ssh folder.

## SSH using PowerShell or cmd
1. Open .ssh directory in PowerShell
    ```
    ssh ubuntu@65.1.141.123 -i .\file_name.pem
    ```

> [!NOTE]
> Might get "WARNING: UNPROTECTED PRIVATE KEY FILE!" error, fix it.

2. Change root's password (if instance is based on ubuntu)
    ```
    sudo passwd ubuntu
    ```

## Setup Apache Kafka

### Install Apache Kafka on Ubuntu [Guide to install Kafka](https://vegastack.com/tutorials/how-to-install-apache-kafka-on-ubuntu-22-04/)

 - Add kafka user, ```sudo useradd kafka -m -s /bin/bash```
 - Set password for kafk user, ```sudo passwd kafka```
 - Grant permission to kafka below root, ```sudo visudo```
 - Select kafka user, ```su -l kafka```
 - Create Downloads directory, ```mkdir ~/Downloads && cd ~/Downloads```
 - Download Kafka in Downloads directory, ```wget https://downloads.apache.org/kafka/3.9.0/kafka_2.12-3.9.0.tgz -O ~/Downloads/ka
fka.tgz```
 - Create kafka directory, ```mkdir ~/kafka && cd ~/kafka```
 - Extract tgz file, ```tar -xvzf ~/Downloads/kafka.tgz --strip 1```
 - Open server.properties, ```nano ~/kafka/config/server.properties```
 - Add, ```delete.topic.enable = true```
 - Open, ```sudo nano /etc/systemd/system/kafka.service```
 - Add,
```
    [Unit]
   
    [Service]
    Type=simple
    User=kafka
    ExecStart=/bin/sh -c '/home/kafka/kafka/bin/kafka-server-start.sh /home/kafka/kafka/config/server.properties > /home/kafka/kafka/kafka.log 2>&1'
    ExecStop=/home/kafka/kafka/bin/kafka-server-stop.sh
    Restart=on-abnormal

    [Install]
    WantedBy=multi-user.target
```
 - Start Kafka, ```sudo systemctl start kafka```
 - Check status, ```sudo systemctl status kafka```
 
> [!NOTE]
> Might get 'failed' error

- Check for potential errors in, ```~/kafka/kafka.log```

### Install Java in VPS

- Switch to root user, ```exit```
- Get update, ```sudo apt-get update```
- Upgrade, ```sudo apt upgrade```
- Install Java, ```sudo apt install openjdk-21-jre-headless```
- Switch to kakfa, ```su -l kafka```
- Start Kafka, ```sudo systemctl start kafka```
- Check status, ```sudo systemctl status kafka```
  
> [!NOTE]
> It will run successfully but Zookeeper might give errors, open kafka.log and check.

- Change directory, ```cd ~/kafka```
- Start Zookeeper service, ```bin/zookeeper-server-start.sh config/zookeeper.properties``` or ```bin/zookeeper-server-start.sh config/zookeeper.properties > /dev/null```

Open another PowerShell and start kafka.